package top.lijingyuan.guava.learning;

/**
 * TokenBucketExample
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class TokenBucketExample {

    public static void main(String[] args) {
        final TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i < 110; i++) {
            new Thread(tokenBucket::buy).start();
        }
    }

}
