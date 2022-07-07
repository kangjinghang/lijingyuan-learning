package top.lijingyuan.grpc.learning.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DiskImageStore
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
public class DiskImageStore implements ImageStore {

    private String imageFolder;

    private ConcurrentHashMap<String, ImageMetadata> data;

    public DiskImageStore(String imageFolder) {
        this.imageFolder = imageFolder;
        this.data = new ConcurrentHashMap<>(0);
    }

    @Override
    public String save(String laptopId, String imgType, ByteArrayOutputStream imageData) throws IOException {
        String imageId = UUID.randomUUID().toString();
        String imagePath = String.format("%s/%s%s", imageFolder, imageId, imgType);

        try (FileOutputStream outputStream = new FileOutputStream(imagePath)) {
            imageData.writeTo(outputStream);
        }

        ImageMetadata metadata = new ImageMetadata(laptopId, imgType, imagePath);
        data.put(laptopId, metadata);

        return imageId;
    }

}
