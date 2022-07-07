package top.lijingyuan.grpc.learning.service;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.grpc.learning.pb.*;
import top.lijingyuan.grpc.learning.sample.Generator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * LaptopClient
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
@Slf4j
public class LaptopClient {

    private final ManagedChannel channel;

    private final LaptopServiceGrpc.LaptopServiceBlockingStub blockingStub;

    private final LaptopServiceGrpc.LaptopServiceStub asyncStub;

    public LaptopClient(String host, int port) {
        // 创建连接服务端的channel
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
        this.asyncStub = LaptopServiceGrpc.newStub(channel);
    }

//    public LaptopClient(String host, int port, SslContext sslContext) {
//        channel = NettyChannelBuilder.forAddress(host, port)
//                .sslContext(sslContext)
//                .build();

//        blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
//        asyncStub = LaptopServiceGrpc.newStub(channel);
//    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void createLaptop(Laptop laptop) {
        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();
        CreateLaptopResponse response;
        try {
            response = blockingStub
                    // deadline 为5秒，配合服务端接口的休眠6秒的配置
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .createLaptop(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.ALREADY_EXISTS) {
                log.info("laptop ID already exits, ID: {}", laptop.getId());
                return;
            }
            log.error("request failed", e);
            return;
        } catch (Exception e) {
            log.error("request failed", e);
            return;
        }

        log.info("laptop created with ID: {}", response.getId());
    }


    public void searchLaptop(Filter filter) {
        log.info("search started");
        SearchLaptopRequest request = SearchLaptopRequest.newBuilder().setFilter(filter).build();
        try {
            Iterator<SearchLaptopResponse> responseIterator = blockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .searchLaptop(request);

            while (responseIterator.hasNext()) {
                SearchLaptopResponse response = responseIterator.next();
                Laptop laptop = response.getLaptop();
                log.info("found: {}", laptop);
            }
        } catch (Exception e) {
            log.error("request failed", e);
            return;
        }
        log.info("search completed");
    }

    public void uploadImage(String laptopId, String imagePath) throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<UploadImgRequest> requestObserver = asyncStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                .uploadImage(new StreamObserver<UploadImgResponse>() {
                    @Override
                    public void onNext(UploadImgResponse response) {
                        log.info("received response:\n{}", response);
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("upload failed", t);
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        log.info("image uploaded");
                        finishLatch.countDown();
                    }
                });

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(imagePath);
        } catch (IOException e) {
            log.error("con not read image file", e);
            return;
        }

        String imageType = imagePath.substring(imagePath.lastIndexOf("."));
        ImageInfo info = ImageInfo.newBuilder()
                .setLaptopId(laptopId).setImgType(imageType).build();
        UploadImgRequest request = UploadImgRequest.newBuilder().setInfo(info).build();

        try {
            // 先发 imageInfo
            requestObserver.onNext(request);
            log.info("sent image info:\n{}", info);

            byte[] buffer = new byte[1024];
            // 循环发 chunkData
            while (true) {
                int n = fileInputStream.read(buffer);
                if (n <= 0) {
                    break;
                }
                if (finishLatch.getCount() == 0) {
                    return;
                }
                request = UploadImgRequest.newBuilder()
                        .setChunkData(ByteString.copyFrom(buffer, 0, n)).build();
                requestObserver.onNext(request);
                log.info("sent image chunk with size: {}", n);
            }

        } catch (Exception e) {
            log.error("unexpected error: ", e);
            requestObserver.onError(e);
            return;
        }
        // 完成发送
        requestObserver.onCompleted();

        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            log.warn("request cannot finish within 1 minute");
        }
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rateLaptop(String[] laptopIds, double[] scores) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<RateLaptopRequest> requestObserver = asyncStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                .rateLaptop(new StreamObserver<RateLaptopResponse>() {
                    @Override
                    public void onNext(RateLaptopResponse response) {
                        log.info("laptop rated: id = {}, count = {}, average = {}", response.getLaptopId(),
                                response.getRatedCount(), response.getAverageScore());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("rate laptop failed: " + t.getMessage());
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        log.info("rate laptop completed");
                        finishLatch.countDown();
                    }
                });

        int n = laptopIds.length;
        try {
            for (int i = 0; i < n; i++) {
                RateLaptopRequest request = RateLaptopRequest.newBuilder()
                        .setLaptopId(laptopIds[i])
                        .setScore(scores[i])
                        .build();
                requestObserver.onNext(request);
                log.info("sent rate-laptop request: id = {}, score = {}", request.getLaptopId(), request.getScore());
            }
        } catch (Exception e) {
            log.error("unexpected error: " + e.getMessage());
            requestObserver.onError(e);
            return;
        }

        requestObserver.onCompleted();
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            log.warn("request cannot finish within 1 minute");
        }
    }

//    public static SslContext loadTLSCredentials() throws SSLException {
//        File serverCACertFile = new File("cert/ca-cert.pem");
//        File clientCertFile = new File("cert/client-cert.pem");
//        File clientKeyFile = new File("cert/client-key.pem");
//
//        return GrpcSslContexts.forClient()
//                .keyManager(clientCertFile, clientKeyFile)
//                .trustManager(serverCACertFile)
//                .build();
//    }

    public static void main(String[] args) throws InterruptedException {
        LaptopClient client = new LaptopClient("0.0.0.0", 8080);
        Generator generator = new Generator();

        // ---------------------- 测试1：一元（ UNARY）：客户端发送单个请求消息，服务端返回单个响应消息 ---------------------------------
//        Laptop laptop = generator.newLaptop();
        // 情景1：客户端设置ID为空字符串，服务端设置为uuid，两端正常报错
//        laptop = laptop.toBuilder().setId("").build();
        // 情景2：客户端设置已经存在的ID，客户端收到异常，io.grpc.StatusRuntimeException: ALREADY_EXISTS: laptop ID already exists
        // 之后改进代码：catch (StatusRuntimeException e) ，判断
        // e.getStatus().getCode() == Status.Code.ALREADY_EXISTS 后打印 info 级别日志
//        laptop = laptop.toBuilder().setId("561e63e0-7693-4ee0-ab36-b961e4757f77").build();
        // 情景3：无效的id，客户端收到 io.grpc.StatusRuntimeException: INVALID_ARGUMENT: Invalid UUID string: invalid
//        laptop = laptop.toBuilder().setId("invalid").build();
//        try {
//            client.createLaptop(laptop);
//        } catch (Exception e) {
//            client.shutdown();
//        }

        // ---------------------- 测试1：结束 ---------------------------------


        // ---------------------- 测试2：服务端（SERVER STREAMING）：客户端发送单个请求消息，服务端回复多个消息流 ---------------------------------

//        try {
//            for (int i = 0; i < 10; i++) {
//                Laptop laptop = generator.newLaptop();
//                client.createLaptop(laptop);
//            }
//
//            Memory minRam = Memory.newBuilder().setValue(8).setUnit(Memory.Unit.GIGABYTE).build();
//
//            Filter filter = Filter.newBuilder().setMinPriceUsed(3000).setMinCpuCores(4).setMinCpuGhz(2.5)
//                    .setMinRam(minRam).build();
//
//            client.searchLaptop(filter);
//        } catch (Exception e) {
//            client.shutdown();
//        }

        // ---------------------- 测试2：结束 ---------------------------------


        // ---------------------- 测试3：客户端流（CLIENT STREAMING）：客户端发送多个消息流，服务端返回单个响应 ---------------------------------

//        try {
//            Laptop laptop = generator.newLaptop();
//            client.createLaptop(laptop);
//            client.uploadImage(laptop.getId(), "tmp/laptop.jpg");
//        } catch (Exception e) {
//            client.shutdown();
//        }

        // ---------------------- 测试3：结束 ---------------------------------


        // ---------------------- 测试4：双向流式传输（BIDIRECTIONAL STREAMING）：客户端和服务端并行持续发送和接受多条消息，可以以任何顺序，非常灵活且无阻塞 ---------------------------------

        int n = 3;
        String[] laptopIds = new String[n];

        for (int i = 0; i < n; i++) {
            Laptop laptop = generator.newLaptop();
            laptopIds[i] = laptop.getId();
            client.createLaptop(laptop);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log.info("rate laptop (y/n)? ");
            String answer = scanner.nextLine();
            if (answer.toLowerCase().trim().equals("n")) {
                break;
            }

            double[] scores = new double[n];
            for (int i = 0; i < n; i++) {
                scores[i] = generator.newLaptopScore();
            }

            client.rateLaptop(laptopIds, scores);
        }

        // ---------------------- 测试4：结束 ---------------------------------
    }


}
