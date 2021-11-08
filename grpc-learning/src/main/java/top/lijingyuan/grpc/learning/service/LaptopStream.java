package top.lijingyuan.grpc.learning.service;

import top.lijingyuan.grpc.learning.pb.Laptop;

/**
 * LaptopStream
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
public interface LaptopStream {

    void send(Laptop laptop);

}
