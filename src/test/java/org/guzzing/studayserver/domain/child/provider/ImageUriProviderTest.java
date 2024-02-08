package org.guzzing.studayserver.domain.child.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.Base64;
import java.util.List;
import org.guzzing.studayserver.global.common.profile.provider.ProfileImageUriProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest(webEnvironment = NONE)
class ImageUriProviderTest {

    @Autowired
    private ProfileImageUriProvider profileImageUriProvider;

    @Test
    @DisplayName("형제 간 기본 이미지가 중복되지 않도록 사전 정의된 기본 URI를 반환한다.")
    void provideDefaultProfileImageURI_NotDuplicated() {
        // Given
        final List<String> existsImageUris = List.of(
                "icon_0_0.png", "icon_0_1.png", "icon_0_2.png",
                "icon_1_0.png", "icon_1_1.png", "icon_1_2.png",
                "icon_2_0.png", "icon_2_1.png");

        // When
        String defaultProfileImageURI = profileImageUriProvider.provideDefaultProfileImageURI(existsImageUris);

        // Then
        boolean result = existsImageUris.contains(defaultProfileImageURI);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("이미지 원본 파일명을 Base64로 인코딩하고 멤버 아이디 경로를 포함하도록 만들어 반환한다.")
    void provideCustomProfileImageURI_MemberId_Base64Encoding() {
        // Given
        Long memberId = 24L;
        byte[] originalFileName = "test".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "test",
                "test.png",
                "image/png",
                originalFileName);

        // When
        String profileImageURI = profileImageUriProvider.provideCustomProfileImageURI(memberId, file);

        // Then
        String[] split = profileImageURI.split("/");
        String encodedFileName = split[split.length - 1];
        byte[] decodedFileName = Base64.getDecoder().decode(encodedFileName);

        assertThat(profileImageURI).contains(memberId.toString());
        assertThat(decodedFileName).isEqualTo(originalFileName);
    }

}