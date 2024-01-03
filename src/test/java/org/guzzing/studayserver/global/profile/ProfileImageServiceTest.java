package org.guzzing.studayserver.global.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import io.findify.s3mock.S3Mock;
import java.io.IOException;
import org.guzzing.studayserver.config.S3MockConfig;
import org.guzzing.studayserver.global.config.S3Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;

@Import(value = S3MockConfig.class)
@SpringBootTest(webEnvironment = NONE)
class ProfileImageServiceTest {

    @Autowired
    private ProfileImageService service;

    @Autowired
    private AmazonS3 s3client;
    @Autowired
    private S3Mock s3Mock;
    @Autowired
    private S3Config s3Config;

    @BeforeEach
    void setUp() {
        s3Mock.start();
        s3client.createBucket(s3Config.getBucketName());
    }

    @AfterEach
    void tearDown() {
        s3client.shutdown();
        s3Mock.stop();
    }

    @Test
    @DisplayName("프로필 이미지를 업로드한다.")
    void uploadProfileImage_Success() throws IOException {
        // Given
        String fileName = "test";
        String originFilename = "test.png";
        String contentType = "image/png";
        byte[] content = "test".getBytes();
        String profileImageUri = "test/test_profile_image_uri";

        MockMultipartFile file = new MockMultipartFile(fileName, originFilename, contentType, content);

        // When
        service.uploadProfileImage(file, profileImageUri);

        // Then
        S3Object object = s3client.getObject(s3Config.getBucketName(), profileImageUri);

        assertThat(object.getObjectMetadata().getContentType()).isEqualTo(contentType);
        assertThat(FileCopyUtils.copyToByteArray(object.getObjectContent())).isEqualTo(content);
    }

}