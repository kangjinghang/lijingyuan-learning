package top.lijingyuan.grpc.learning.service;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * InMemoryRatingStoreTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
public class InMemoryRatingStoreTest {

    /**
     * merge 并发测试
     */
    @Test
    public void add() throws InterruptedException {
        InMemoryRatingStore ratingStore = new InMemoryRatingStore();

        List<Callable<Rating>> tasks = new LinkedList<>();
        String laptopID = UUID.randomUUID().toString();
        double score = 5;

        int n = 10;
        for (int i = 0; i < n; i++) {
            tasks.add(() -> ratingStore.add(laptopID, score));
        }

        Set<Integer> ratedCount = new HashSet<>();
        Executors.newWorkStealingPool()
                .invokeAll(tasks)
                .stream()
                .forEach(future -> {
                    try {
                        Rating rating = future.get();
                        assertEquals(rating.getSum(), rating.getCount() * score, 1e-9);
                        ratedCount.add(rating.getCount());
                    } catch (Exception e) {
                        throw new IllegalStateException();
                    }
                });

        assertEquals(n, ratedCount.size());
        // 预期：总共计数10次，从1到10
        for (int cnt = 1; cnt <= n; cnt++) {
            assertTrue(ratedCount.contains(cnt));
        }
    }

}