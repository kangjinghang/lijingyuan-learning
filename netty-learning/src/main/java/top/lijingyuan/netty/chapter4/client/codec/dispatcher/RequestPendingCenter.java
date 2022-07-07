package top.lijingyuan.netty.chapter4.client.codec.dispatcher;

import top.lijingyuan.netty.chapter4.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RequestPendingCenter
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class RequestPendingCenter {

    private Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(Long streamId, OperationResultFuture future) {
        this.map.put(streamId, future);
    }

    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture resultFuture = map.get(streamId);
        if (resultFuture != null) {
            resultFuture.setSuccess(operationResult);
        }
        this.map.remove(streamId);
    }

}
