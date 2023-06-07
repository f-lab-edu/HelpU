package com.flab.helpu.domain.product.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.flab.helpu.global.config.FileNameUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Repository
@Slf4j
public class NcpObjectStorage implements ImageStorage {

  @Value("${ncp.storage.bucket}")
  private String bucket;

  private final AmazonS3 NcpClient;

  @Override
  public String store(MultipartFile imageFile) {
    ObjectMetadata objectMetadata = createObjectMetadata(imageFile);
    String fileName = FileNameUtil.createStoredFileName(imageFile.getOriginalFilename());
    try {
      putObjectToNcp(imageFile, fileName, objectMetadata);
    } catch (IOException e) {
      log.error("Could not store a image file.", e);
      return "";
    }

    return fileName;
  }

  @Override
  public boolean doesImageFileExist(String imageFileName) {
    return NcpClient.doesObjectExist(bucket, imageFileName);
  }

  private ObjectMetadata createObjectMetadata(MultipartFile imageFile) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(imageFile.getSize());
    objectMetadata.setContentType(imageFile.getContentType());
    return objectMetadata;
  }

  private void putObjectToNcp(MultipartFile imageFile, String imageFileName, ObjectMetadata objectMetadata) throws
      IOException {
    NcpClient.putObject(
        bucket,
        imageFileName,
        imageFile.getInputStream(),
        objectMetadata
    );
  }
}

