package top.lijingyuan.grpc.learning.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.ClientAuth;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * LaptopServer
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
@Slf4j
public class LaptopServer {

    private final int port;

    private final Server server;

    public LaptopServer(int port, LaptopStore laptopStore) {
        this(ServerBuilder.forPort(port), port, laptopStore);
    }

    public LaptopServer(int port, LaptopStore laptopStore, ImageStore imageStore) {
        this(ServerBuilder.forPort(port), port, laptopStore, imageStore);
    }

    public LaptopServer(int port, LaptopStore laptopStore, ImageStore imageStore, RatingStore ratingStore,
                        SslContext sslContext) {
        this(NettyServerBuilder.forPort(port).sslContext(sslContext), port, laptopStore, imageStore, ratingStore);
    }

    public LaptopServer(int port, LaptopStore laptopStore, ImageStore imageStore, RatingStore ratingStore) {
        this(ServerBuilder.forPort(port), port, laptopStore, imageStore, ratingStore);
    }

    public LaptopServer(ServerBuilder<?> serverBuilder, int port, LaptopStore laptopStore) {
        this.port = port;
        LaptopService laptopService = new LaptopService(laptopStore);
        server = serverBuilder.addService(laptopService)
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    public LaptopServer(ServerBuilder<?> serverBuilder, int port, LaptopStore laptopStore, ImageStore imageStore) {
        this.port = port;
        LaptopService laptopService = new LaptopService(laptopStore, imageStore);
        server = serverBuilder.addService(laptopService)
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    public LaptopServer(ServerBuilder<?> serverBuilder, int port,
                        LaptopStore laptopStore, ImageStore imageStore, RatingStore ratingStore) {
        this.port = port;
        LaptopService laptopService = new LaptopService(laptopStore, imageStore, ratingStore);
        server = serverBuilder.addService(laptopService)
                // 支持 grpc reflection
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    public void start() throws IOException {
        server.start();
        log.info("server started on port {}", port);

        // 注册jvm hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("shut down gRPC because JVM shuts down");
            try {
                this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("server shut down");
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUtilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static SslContext loadTLSCredentials() throws SSLException {
        File serverCertFile = new File("cert/server-cert.pem");
        File serverKeyFile = new File("cert/server-key.pem");
        File clientCACertFile = new File("cert/ca-cert.pem");

        SslContextBuilder ctxBuilder = SslContextBuilder.forServer(serverCertFile, serverKeyFile)
                .clientAuth(ClientAuth.REQUIRE)
                .trustManager(clientCACertFile);
        // 不需要客户端证书
//        SslContextBuilder ctxBuilder = SslContextBuilder.forServer(serverCertFile, serverKeyFile)
//                .clientAuth(ClientAuth.NONE);

        return GrpcSslContexts.configure(ctxBuilder).build();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        InMemoryLaptopStore laptopStore = new InMemoryLaptopStore();
        DiskImageStore imageStore = new DiskImageStore("img");
        RatingStore ratingStore = new InMemoryRatingStore();
        LaptopServer server = new LaptopServer(8080, laptopStore, imageStore, ratingStore);
        server.start();
        server.blockUtilShutdown();
    }

}
