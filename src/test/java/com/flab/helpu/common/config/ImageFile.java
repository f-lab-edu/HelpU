package com.flab.helpu.common.config;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {

  public static String IMAGE_NAME1 = "test_image1.jpeg";
  public static MockMultipartFile MOCK_JPEG_FILE
      = createMockFile(IMAGE_NAME1, ImageFormat.JPEG);

  public static String IMAGE_NAME2 = "test_image2.png";
  public static MultipartFile MOCK_PNG_FILE
      = createMockFile(IMAGE_NAME2, ImageFormat.PNG);

  public enum ImageFormat {
    JPEG("jpeg"),
    PNG("png");

    private final String format;

    ImageFormat(String format) {
      this.format = format;
    }
  }

  public String getFormat(ImageFormat imageFormat) {
    return imageFormat.format;
  }

  public static MockMultipartFile createMockFile(String imageName, ImageFormat imageFormat) {
    return switch (imageFormat) {
      case JPEG -> new MockMultipartFile(
          "image_jpeg",
          imageName,
          MediaType.IMAGE_JPEG_VALUE,
          new byte[10]
      );
      case PNG -> new MockMultipartFile(
          "image_png",
          imageName,
          MediaType.IMAGE_PNG_VALUE,
          new byte[10]
      );
      default -> new MockMultipartFile(
          "image",
          "image.png",
          MediaType.IMAGE_PNG_VALUE,
          new byte[10]
      );
    };
  }
}
