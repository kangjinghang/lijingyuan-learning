package top.lijingyuan.junit5.learning.fundamental;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Env
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
public interface Env {

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @Tag("dev")
    @interface Dev {

    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @Tag("sit")
    @interface Sit {

    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @Tag("uat")
    @interface Uat {

    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @Dev
    @Sit
    @Uat
    @interface All {

    }


}
