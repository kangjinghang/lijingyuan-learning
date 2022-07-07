package top.lijingyuan.grpc.learning.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ImageStore
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
public interface ImageStore {

    String save(String laptopId, String imgType, ByteArrayOutputStream imageData) throws IOException;

}
