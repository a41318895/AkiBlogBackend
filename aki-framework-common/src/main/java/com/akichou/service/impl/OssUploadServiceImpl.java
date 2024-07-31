package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.exception.SystemException;
import com.akichou.service.OssUploadService;
import com.akichou.util.PathUtils;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Oss(Object Storage Service) Upload Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
public class OssUploadServiceImpl implements OssUploadService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Override
    public ResponseResult<Object> uploadImg(MultipartFile img) {

        String originalFilename = img.getOriginalFilename();
        long fileSize = img.getSize();

        // Handle the exception : file type error
        assert originalFilename != null;
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // Handle the exception : file size too large error
        if (fileSize > 2 * 1024 * 1024) {
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }

        String filePath = PathUtils.generateFilePath(originalFilename);

        String url = uploadToMinio(img, filePath);

        return ResponseResult.okResult(url);
    }

    private String uploadToMinio(MultipartFile imgFile, String filePath) {

        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            InputStream inputStream = imgFile.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .stream(inputStream, imgFile.getSize(), -1)
                            .contentType(imgFile.getContentType())
                            .build()
            );

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(filePath)
                            .build()
            );
        } catch (Exception e) {

            throw new RuntimeException("Upload to Minio Process Error Occurred: " + e.getMessage());
        }
    }
}
