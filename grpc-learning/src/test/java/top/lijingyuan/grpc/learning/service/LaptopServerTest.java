package top.lijingyuan.grpc.learning.service;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import top.lijingyuan.grpc.learning.pb.*;
import top.lijingyuan.grpc.learning.sample.Generator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * LaptopServerTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
public class LaptopServerTest {

    @Rule
    // automatic graceful shutdown channel at the end of test
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private LaptopStore laptopStore;

    private ImageStore imageStore;

    private RatingStore ratingStore;

    private LaptopServer server;

    private ManagedChannel channel;

    @Before
    public void setUp() throws Exception {
        String servername = InProcessServerBuilder.generateName();
        InProcessServerBuilder serverBuilder = InProcessServerBuilder.forName(servername).directExecutor();

        laptopStore = new InMemoryLaptopStore();
        imageStore = new DiskImageStore("img");
        ratingStore = new InMemoryRatingStore();

        server = new LaptopServer(serverBuilder, 0, laptopStore, imageStore, ratingStore);
        server.start();

        channel = InProcessChannelBuilder.forName(servername).directExecutor().build();
    }

    @After
    public void teardown() throws Exception {
        server.stop();
    }

    @Test
    public void createLaptopWithAValidID() {
        Generator generator = new Generator();
        Laptop laptop = generator.newLaptop();
        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

        LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
        CreateLaptopResponse response = stub.createLaptop(request);
        assertNotNull(response);
        assertEquals(laptop.getId(), response.getId());

        Laptop found = laptopStore.find(response.getId());
        assertNotNull(found);
    }

    @Test
    public void createLaptopWithEmptyID() {
        Generator generator = new Generator();
        Laptop laptop = generator.newLaptop().toBuilder().setId("").build();
        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

        LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
        CreateLaptopResponse response = stub.createLaptop(request);
        assertNotNull(response);
        assertFalse(response.getId().isEmpty());

        Laptop found = laptopStore.find(response.getId());
        assertNotNull(found);
    }

    @Test(expected = StatusRuntimeException.class)
    public void createLaptopWithInvalidID() {
        Generator generator = new Generator();
        Laptop laptop = generator.newLaptop().toBuilder().setId("invalid").build();
        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

        LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
        stub.createLaptop(request);
    }

    @Test(expected = StatusRuntimeException.class)
    public void createLaptopWithAnAlreadyExistsID() throws Exception {
        Generator generator = new Generator();
        Laptop laptop = generator.newLaptop();
        laptopStore.save(laptop);
        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

        LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
        stub.createLaptop(request);
    }

    @Test
    public void rateLaptop() throws Exception {
        Generator generator = new Generator();
        Laptop laptop = generator.newLaptop();
        laptopStore.save(laptop);

        LaptopServiceGrpc.LaptopServiceStub stub = LaptopServiceGrpc.newStub(channel);
        RateLaptopResponseStreamObserver responseObserver = new RateLaptopResponseStreamObserver();
        StreamObserver<RateLaptopRequest> requestObserver = stub.rateLaptop(responseObserver);

        double[] scores = {8, 7.5, 10};
        double[] averages = {8, 7.75, 8.5};
        int n = scores.length;

        for (int i = 0; i < n; i++) {
            RateLaptopRequest request = RateLaptopRequest.newBuilder()
                    .setLaptopId(laptop.getId())
                    .setScore(scores[i])
                    .build();
            requestObserver.onNext(request);
        }

        requestObserver.onCompleted();

        assertNull(responseObserver.err);
        assertTrue(responseObserver.completed);
        assertEquals(n, responseObserver.responses.size());

        int idx = 0;
        for (RateLaptopResponse response : responseObserver.responses) {
            assertEquals(laptop.getId(), response.getLaptopId());
            assertEquals(idx + 1, response.getRatedCount());
            assertEquals(averages[idx], response.getAverageScore(), 1e-9);
            idx++;
        }
    }

    private class RateLaptopResponseStreamObserver implements StreamObserver<RateLaptopResponse> {
        public List<RateLaptopResponse> responses;
        public Throwable err;
        public boolean completed;

        public RateLaptopResponseStreamObserver() {
            responses = new LinkedList<>();
        }

        @Override
        public void onNext(RateLaptopResponse response) {
            responses.add(response);
        }

        @Override
        public void onError(Throwable t) {
            err = t;
        }

        @Override
        public void onCompleted() {
            completed = true;
        }
    }

}