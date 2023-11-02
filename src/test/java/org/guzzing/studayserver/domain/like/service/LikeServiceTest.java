package org.guzzing.studayserver.domain.like.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.guzzing.studayserver.domain.like.controller.dto.LikeRequest;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.LikeParam;
import org.guzzing.studayserver.domain.like.service.dto.LikeResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles(profiles = {"dev", "oauth"})
@SpringBootTest
@Transactional
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    private final Long memberId = 1L;
    private final Long academyId = 1L;
    private LikeParam param;

    @BeforeEach
    void setUp() {
        LikeRequest request = new LikeRequest(academyId);
        param = LikeRequest.to(request, memberId);
    }

    @Test
    @DisplayName("학원에 대해서 좋아요를 등록한다.")
    void createLikeOfAcademy_WithMemberId() {
        // Given & When
        LikeResult result = likeService.createLikeOfAcademy(param);

        // Then
        assertThat(result.memberId()).isEqualTo(memberId);
        assertThat(result.academyId()).isEqualTo(academyId);
    }

    @Test
    @DisplayName("학원에 대해 등록한 좋아요를 제거한다.")
    void removeLikeOfAcademy_LikeId_Remove() {
        // Given
        LikeResult savedLike = likeService.createLikeOfAcademy(param);

        // When
        likeService.removeLikeOfAcademy(savedLike.likeId());

        // Then
        boolean result = likeRepository.existsById(savedLike.likeId());

        assertThat(result).isFalse();
    }

}