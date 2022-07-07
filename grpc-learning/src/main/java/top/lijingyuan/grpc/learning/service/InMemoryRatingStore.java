package top.lijingyuan.grpc.learning.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * InMemoryRatingStore
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
public class InMemoryRatingStore implements RatingStore {

    private ConcurrentHashMap<String, Rating> data;

    public InMemoryRatingStore() {
        this.data = new ConcurrentHashMap<>(0);
    }

    @Override
    public Rating add(String laptopId, double score) {
        return data.merge(laptopId, new Rating(1, score), Rating::add);
    }

}
