package top.lijingyuan.guava.learning;

import com.google.common.collect.ComparisonChain;

/**
 * ObjectsTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class ObjectsTest {

    static class Guava implements Comparable<Guava> {
        private final String manufacture;
        private final String version;
        private final String releaseDate;

        public Guava(String manufacture, String version, String releaseDate) {
            this.manufacture = manufacture;
            this.version = version;
            this.releaseDate = releaseDate;
        }

        @Override
        public int compareTo(Guava o) {
            return ComparisonChain.start().compare(this.manufacture, o.manufacture)
                    .compare(this.version, o.version).compare(this.releaseDate, o.releaseDate).result();
        }
    }

}
