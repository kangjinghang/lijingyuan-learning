package top.lijingyuan.grpc.learning.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ImageMetadata
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class ImageMetadata {

    private final String laptopId;

    private final String type;

    private final String path;

}
