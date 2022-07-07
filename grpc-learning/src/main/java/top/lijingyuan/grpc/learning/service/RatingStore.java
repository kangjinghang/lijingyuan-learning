package top.lijingyuan.grpc.learning.service;

/**
 * RatingStore
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
public interface RatingStore {

    Rating add(String laptopId, double score);

}
