package top.lijingyuan.grpc.learning.service;

import io.grpc.Context;
import top.lijingyuan.grpc.learning.pb.Filter;
import top.lijingyuan.grpc.learning.pb.Laptop;

/**
 * LaptopStore
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
public interface LaptopStore {

    void save(Laptop laptop) throws Exception;

    Laptop find(String id);

    void search(Context context, Filter filter, LaptopStream stream);

}
