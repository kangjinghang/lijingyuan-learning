package top.lijingyuan.guava.learning.eventbus.internal;

/**
 * MyEventExceptionHandler
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-25
 * @since 1.0.0
 */
public interface MyEventExceptionHandler {

    void handle(Throwable cause,MyEventContext context);

}
