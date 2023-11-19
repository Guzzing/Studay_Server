package org.guzzing.studayserver.domain.child.provider;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProfileImageProvider {

    private static final List<String> DEFAULT_IMAGE_URIS = List.of(
            "icon_0_0.png", "icon_0_1.png", "icon_0_2.png",
            "icon_1_0.png", "icon_1_1.png", "icon_1_2.png",
            "icon_2_0.png", "icon_2_1.png", "icon_2_2.png");

    public String provideDefaultProfileImageURI(final List<String> existsImageUris) {
        return DEFAULT_IMAGE_URIS.stream()
                .filter(imageUri -> !existsImageUris.contains(imageUri))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("사용 가능한 이미지 URI가 업습니다."));
    }

}
