package top.lijingyuan.netty.chapter4.common.order;

import lombok.Data;
import top.lijingyuan.netty.chapter4.common.OperationResult;

@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;

}
