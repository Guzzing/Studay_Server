package org.guzzing.studayserver.domain.child.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = {Base62Provider.class})
class Base62ProviderTest {

    @Autowired
    private Base62Provider base62Provider;

    @Test
    @DisplayName("아이 아이디를 8자리 파일명으로 인코딩한다.")
    void encode_childId_FileName() {
        // Given
        final long childId = 1L;

        // When
        String encode = base62Provider.encode(childId);

        // Then
        assertThat(encode).hasSize(8);
        assertThat(encode).isEqualTo("BAAAAAAA");
    }

    @Test
    @DisplayName("파일명을 아이 아이디로 디코딩한다.")
    void decode_FileName_ChildId() {
        // Given
        final String encode = "BDDASBC9";

        // When
        long decode = base62Provider.decode(encode);

        // Then
        assertThat(decode).isPositive();
    }

}