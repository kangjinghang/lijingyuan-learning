package top.lijingyuan.proto;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * GrpcClient
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class GrpcClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051)
                .usePlaintext().build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());

        System.out.println(myResponse.getRealname());

        System.out.println("------------------");

        Iterator<StudentResponse> iter = blockingStub.getStudentByAge(StudentRequest.newBuilder().setAge(18).build());

        while (iter.hasNext()) {
            StudentResponse studentResponse = iter.next();
            System.out.println(studentResponse.getName() + " " + studentResponse.getAge() + " " + studentResponse.getCity());
        }

        System.out.println("------------------");

        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(s -> {
                    System.out.println(s.getName());
                    System.out.println(s.getAge());
                    System.out.println(s.getCity());
                    System.out.println("*****");
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };

        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

//        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentByAges(studentResponseListStreamObserver);
//
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());
//
//        studentRequestStreamObserver.onCompleted();

//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        StreamObserver<StreamRequest> streamRequestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        });

        for (int i = 0; i < 10; i++) {
            streamRequestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        managedChannel.awaitTermination(30, TimeUnit.SECONDS);

    }

}
