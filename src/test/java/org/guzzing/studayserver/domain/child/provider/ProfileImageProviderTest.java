package org.guzzing.studayserver.domain.child.provider;

import static org.assertj.core.api.Assertions.assertThat;

import io.findify.s3mock.S3Mock;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProfileImageProviderTest {

    @Autowired
    private ProfileImageProvider profileImageProvider;

    @MockBean
    private S3Mock s3Mock;

    @AfterEach
    void tearDown() {
        s3Mock.stop();
    }

    @Test
    @DisplayName("중복되지 않는 기본 이미지 URI를 반환한다.")
    void provideDefaultProfileImageURI_NotDuplicated() {
        // Given
        final List<String> existsImageUris = List.of(
                "icon_0_0.png", "icon_0_1.png", "icon_0_2.png",
                "icon_1_0.png", "icon_1_1.png", "icon_1_2.png",
                "icon_2_0.png", "icon_2_1.png");

        // When
        String defaultProfileImageURI = profileImageProvider.provideDefaultProfileImageURI(existsImageUris);

        // Then
        boolean result = existsImageUris.contains(defaultProfileImageURI);

        assertThat(result).isFalse();
    }

//    @Test
//    @DisplayName("이미지를 Base62로 인코딩하여 S3에 업로드한다.")
//    void uploadProfileImage_EncodeFileName_UploadToS3() throws IOException {
//        // Given
//        final Long childId = 24L;
//        String path = "test.png";
//        String contentType = "image/png";
//        String dirName = "test";
//
//        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());
//
//        // When
//        final String encodeFileName = profileImageProvider.uploadProfileImage(childId, file);
//
//        // Then
//        assertThat(encodeFileName)
//                .hasSize(8)
//                .isEqualTo("YAAAAAAA");
//    }

}