package top.lijingyuan.netty.chapter4.common.keepalive;


import lombok.Data;
import lombok.extern.java.Log;
import top.lijingyuan.netty.chapter4.common.Operation;

@Data
@Log
public class KeepaliveOperation extends Operation {

    private long time ;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public KeepaliveOperationResult execute() {
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}
