package org.guzzing.studayserver.domain.child.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProfileImageProviderTest {

    @Autowired
    private ProfileImageProvider profileImageProvider;

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

}