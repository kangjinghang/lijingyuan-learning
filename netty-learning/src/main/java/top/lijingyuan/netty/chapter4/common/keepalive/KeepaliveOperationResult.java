package top.lijingyuan.netty.chapter4.common.keepalive;

import lombok.Data;
import top.lijingyuan.netty.chapter4.common.OperationResult;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
