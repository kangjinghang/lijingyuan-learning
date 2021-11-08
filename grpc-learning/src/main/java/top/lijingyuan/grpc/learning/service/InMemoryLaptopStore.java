package top.lijingyuan.grpc.learning.service;

import io.grpc.Context;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.grpc.learning.pb.Filter;
import top.lijingyuan.grpc.learning.pb.Laptop;
import top.lijingyuan.grpc.learning.pb.Memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * InMemoryLaptopStore
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
@Slf4j
public class InMemoryLaptopStore implements LaptopStore {

    private ConcurrentHashMap<String, Laptop> data;

    public InMemoryLaptopStore() {
        this.data = new ConcurrentHashMap<>(0);
    }

    @Override
    public void save(Laptop laptop) throws Exception {
        if (data.containsKey(laptop.getId())) {
            throw new AlreadyExistsException("laptop ID already exists");
        }
        // deep copy
        Laptop other = laptop.toBuilder().build();
        data.put(other.getId(), other);
        log.info("saved laptop with ID: {}", other.getId());
    }

    @Override
    public Laptop find(String id) {
        if (!data.containsKey(id)) {
            return null;
        }
        // deep copy
        return data.get(id).toBuilder().build();
    }

    @Override
    public void search(Context context, Filter filter, LaptopStream stream) {
        for (Map.Entry<String, Laptop> entry : data.entrySet()) {
            if (context.isCancelled()) {
                log.info("context is cancelled");
                return;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Laptop laptop = entry.getValue();
            if (isQualified(filter, laptop)) {
                stream.send(laptop);
            }
        }
    }

    private boolean isQualified(Filter filter, Laptop laptop) {
        if (laptop.getPriceUsed() > filter.getMinPriceUsed()) {
            return false;
        }

        if (laptop.getCpu().getNumberCores() < filter.getMinCpuCores()) {
            return false;
        }

        if (laptop.getCpu().getMinGhz() < filter.getMinCpuGhz()) {
            return false;
        }

        return toBit(laptop.getRam()) >= toBit(filter.getMinRam());
    }

    private long toBit(Memory memory) {
        long value = memory.getValue();
        switch (memory.getUnit()) {
            case BIT:
                return value;
            case BYTE:
                // 1 BYTE = 8 BIT = 2^3 BIT
                return value << 3;
            case KILOBYTE:
                // 1 KILOBYTE = 1024 BYTE = 2^10 BYTE = 2^13 BIT
                return value << 13;
            case MEGABYTE:
                return value << 23;
            case GIGABYTE:
                return value << 33;
            case TERABYTE:
                return value << 43;
            default:
                return 0;
        }
    }

}
