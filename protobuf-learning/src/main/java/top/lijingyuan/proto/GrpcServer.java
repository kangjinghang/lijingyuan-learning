package top.lijingyuan.proto;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * GrpcServer
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class GrpcServer {

    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new StudentServiceImpl())
                .build()
                .start();

        System.out.println("server started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            GrpcServer.this.stop();
            System.err.println("*** server shut down");
        }));

        System.out.println("invoked here");
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
//        server.stop();
        server.awaitTermination();
    }

}
