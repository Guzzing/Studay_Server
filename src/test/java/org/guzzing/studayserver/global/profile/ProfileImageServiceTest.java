package org.guzzing.studayserver.global.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import io.findify.s3mock.S3Mock;
import java.io.IOException;
import org.guzzing.studayserver.config.S3MockConfig;
import org.guzzing.studayserver.global.common.profile.ProfileImageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;

@Import(value = S3MockConfig.class)
@SpringBootTest(webEnvironment = NONE)
class ProfileImageServiceTest {

    private static final String BUCKET_NAME = "studay-resources-bucket";

    @Autowired
    private ProfileImageService service;

    @Autowired
    private AmazonS3 s3client;

    @BeforeAll
    static void beforeAll(@Autowired AmazonS3 s3client, @Autowired S3Mock s3Mock) {
        s3Mock.start();
        s3client.createBucket(BUCKET_NAME);
    }

    @AfterAll
    static void afterAll(@Autowired AmazonS3 s3client, @Autowired S3Mock s3Mock) {
        s3client.shutdown();
        s3Mock.stop();
    }

    @Test
    @DisplayName("프로필 이미지를 업로드한다.")
    void uploadProfileImage_Success() throws IOException {
        // Given
        MultipartFileData multipartFileData = new MultipartFileData();

        // When
        service.uploadProfileImage(multipartFileData.file, multipartFileData.profileImageUri);

        // Then
        S3Object object = s3client.getObject(BUCKET_NAME, multipartFileData.profileImageUri);

        assertThat(object.getObjectMetadata().getContentType()).isEqualTo(multipartFileData.contentType);
        assertThat(FileCopyUtils.copyToByteArray(object.getObjectContent())).isEqualTo(multipartFileData.content);
    }

    @Test
    @DisplayName("업로드한 프로필 이미지를 제거한다.")
    void deleteProfileImage() {
        // Given
        MultipartFileData multipartFileData = new MultipartFileData();
        service.uploadProfileImage(multipartFileData.file, multipartFileData.profileImageUri);

        // When
        service.deleteProfileImage(multipartFileData.profileImageUri);

        // Then
        boolean exist = s3client.doesObjectExist(BUCKET_NAME, multipartFileData.profileImageUri);

        assertThat(exist).isFalse();
    }

    private static class MultipartFileData {

        String fileName = "test";
        String originFilename = "test.png";
        String contentType = MediaType.IMAGE_PNG_VALUE;
        byte[] content = "test".getBytes();
        String profileImageUri = "test/test_profile_image_uri";

        MockMultipartFile file = new MockMultipartFile(fileName, originFilename, contentType, content);
    }

}
