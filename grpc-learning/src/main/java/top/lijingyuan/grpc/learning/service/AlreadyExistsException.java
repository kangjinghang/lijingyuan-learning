package top.lijingyuan.grpc.learning.service;

/**
 * AlreadyExistsException
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }

}
