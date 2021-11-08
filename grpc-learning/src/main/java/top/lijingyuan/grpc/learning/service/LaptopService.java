package top.lijingyuan.grpc.learning.service;

import com.google.protobuf.ByteString;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.grpc.learning.pb.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * LaptopService
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
@Slf4j
public class LaptopService extends LaptopServiceGrpc.LaptopServiceImplBase {

    private LaptopStore laptopStore;

    private ImageStore imageStore;

    private RatingStore ratingStore;

    public LaptopService(LaptopStore laptopStore) {
        this.laptopStore = laptopStore;
    }

    public LaptopService(LaptopStore laptopStore, ImageStore imageStore) {
        this.laptopStore = laptopStore;
        this.imageStore = imageStore;
    }

    public LaptopService(LaptopStore laptopStore, ImageStore imageStore, RatingStore ratingStore) {
        this.laptopStore = laptopStore;
        this.imageStore = imageStore;
        this.ratingStore = ratingStore;
    }

    /**
     * 一元（ UNARY）：客户端发送单个请求消息，服务端返回单个响应消息
     */
    @Override
    public void createLaptop(CreateLaptopRequest request, StreamObserver<CreateLaptopResponse> responseObserver) {
        Laptop laptop = request.getLaptop();

        String id = laptop.getId();
        log.info("got a create laptop request with ID: {}", id);
        UUID uuid;
        if (id.isEmpty()) {
            uuid = UUID.randomUUID();
        } else {
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                // 按错误码返回
                responseObserver.onError(
                        Status.INVALID_ARGUMENT.withDescription(e.getMessage())
                                .asRuntimeException()
                );
                return;
            }
        }

//        try {
//            // 休眠6秒，配合客户端的 deadline 为5秒的配置
//            TimeUnit.SECONDS.sleep(6);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 为了避免客户端因为deadline已经失败，但是服务端仍然继续往下运行，正常创建 laptop 的问题，需要加上以下代码
        // 经过大量处理后，我们需要检查当前上下文状态
        if (Context.current().isCancelled()) {
            log.info("request is cancelled");
            // 返回错误后直接返回，这样 laptop 就不会保存到 laptopStore 了
            responseObserver.onError(
                    Status.CANCELLED.withDescription("request is cancelled")
                            .asRuntimeException()
            );
            return;
        }

        Laptop other = laptop.toBuilder().setId(uuid.toString()).build();

        try {
            laptopStore.save(other);
        } catch (AlreadyExistsException e) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }

        CreateLaptopResponse response = CreateLaptopResponse.newBuilder()
                .setId(other.getId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 服务端（SERVER STREAMING）：客户端发送单个请求消息，服务端回复多个消息流
     */
    @Override
    public void searchLaptop(SearchLaptopRequest request, StreamObserver<SearchLaptopResponse> responseObserver) {
        Filter filter = request.getFilter();
        log.info("got a search laptop request with filter: {}", filter);

        laptopStore.search(Context.current(), filter, laptop -> {
            log.info("found laptop with ID: {}", laptop.getId());
            SearchLaptopResponse response = SearchLaptopResponse.newBuilder().setLaptop(laptop).build();
            // 循环发送
            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
        log.info("search laptop completed");
    }

    /**
     * 客户端流（CLIENT STREAMING）：客户端发送多个消息流，服务端返回单个响应
     */
    @Override
    public StreamObserver<UploadImgRequest> uploadImage(StreamObserver<UploadImgResponse> responseObserver) {
        return new StreamObserver<UploadImgRequest>() {

            private String laptopId;

            private String imageType;

            private ByteArrayOutputStream imageData;

            private static final int maxImageSize = 1 << 10;

            @Override
            public void onNext(UploadImgRequest request) {
                // 首先进行oneOf判断类型
                if (request.getDataCase() == UploadImgRequest.DataCase.INFO) {
                    ImageInfo info = request.getInfo();
                    log.info("received image info:\n{}", info);
                    laptopId = info.getLaptopId();
                    imageType = info.getImgType();
                    imageData = new ByteArrayOutputStream();

                    // check laptop exists
                    Laptop found = laptopStore.find(info.getLaptopId());
                    if (found == null) {
                        responseObserver.onError(
                                Status.NOT_FOUND
                                        .withDescription("laptop ID doesn't exists")
                                        .asRuntimeException()
                        );
                        return;
                    }
                    return;
                }

                // should be new chunk data
                ByteString chunkData = request.getChunkData();
                log.info("received image chunk with size: {}", chunkData.size());

                if (chunkData == null) {
                    log.info("image data wasn't sent before");
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("image data wasn't sent before")
                                    .asRuntimeException()
                    );
                    return;
                }

                // 增加对传入图片大小的校验
                int size = imageData.size() + chunkData.size();
//                if (size > maxImageSize) {
//                    log.info("image is to large: {}", size);
//                    responseObserver.onError(
//                            Status.INVALID_ARGUMENT
//                                    .withDescription("image is to large: " + size)
//                                    .asRuntimeException()
//                    );
//                    return;
//                }

                try {
                    chunkData.writeTo(imageData);
                } catch (IOException e) {
                    responseObserver.onError(
                            Status.INTERNAL
                                    .withDescription("cannot write chunk data: " + e.getMessage())
                                    .asRuntimeException()
                    );
                    return;
                }
            }

            @Override
            public void onError(Throwable t) {
                log.warn(t.getMessage());
            }

            @Override
            public void onCompleted() {
                String imageId;
                int imageSize = imageData.size();

                try {
                    imageId = imageStore.save(laptopId, imageType, imageData);
                } catch (IOException e) {
                    responseObserver.onError(
                            Status.INTERNAL
                                    .withDescription("cannot save image to store: " + e.getMessage())
                                    .asRuntimeException()
                    );
                    return;
                }

                UploadImgResponse response = UploadImgResponse.newBuilder().setId(imageId).setSize(imageSize).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 双向流式传输（BIDIRECTIONAL STREAMING）：客户端和服务端并行持续发送和接受多条消息，可以以任何顺序，非常灵活且无阻塞。
     */
    @Override
    public StreamObserver<RateLaptopRequest> rateLaptop(StreamObserver<RateLaptopResponse> responseObserver) {
        return new StreamObserver<RateLaptopRequest>() {
            @Override
            public void onNext(RateLaptopRequest request) {
                String laptopId = request.getLaptopId();
                double score = request.getScore();
                log.info("received rate-laptop request: id = {}, score = {}", laptopId, +score);

                Laptop found = laptopStore.find(laptopId);
                if (found == null) {
                    responseObserver.onError(
                            Status.NOT_FOUND
                                    .withDescription("laptop ID doesn't exist")
                                    .asRuntimeException()
                    );
                    return;
                }

                Rating rating = ratingStore.add(laptopId, score);
                RateLaptopResponse response = RateLaptopResponse.newBuilder().setLaptopId(laptopId).setRatedCount(rating.getCount())
                        .setAverageScore(rating.getSum() / rating.getCount())
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                log.error(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
