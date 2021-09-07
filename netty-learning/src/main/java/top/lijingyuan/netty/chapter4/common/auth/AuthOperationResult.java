package top.lijingyuan.netty.chapter4.common.auth;

import lombok.Data;
import top.lijingyuan.netty.chapter4.common.OperationResult;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
