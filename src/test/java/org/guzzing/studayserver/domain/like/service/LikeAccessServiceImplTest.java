package org.guzzing.studayserver.domain.like.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LikeAccessServiceImplTest {

    @Autowired
    private LikeAccessService likeAccessService;

    @Autowired
    private LikeRepository likeRepository;

    @Test
    @DisplayName("멤버가 해당 학원에 대해서 좋아요를 했는지 여부를 반환한다.")
    @WithMockCustomOAuth2LoginUser
    void isLiked_MemberAndAcademy_ReturnTrue() {
        // Given
        final Long memberId = 1L;
        final Long academyId = 1L;
        final Like like = Like.of(memberId, academyId);
        likeRepository.save(like);

        // When
        final boolean result = likeAccessService.isLiked(memberId, academyId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("멤버가 해당 학원에 대해서 좋아요를 했는지 여부를 반환한다.")
    @WithMockCustomOAuth2LoginUser
    void isLiked_MemberAndAcademy_ReturnFalse() {
        // Given
        final Long memberId = 1L;
        final Long academyId = 1L;
        final Like like = Like.of(memberId, academyId);
        likeRepository.save(like);

        // When
        final boolean result = likeAccessService.isLiked(memberId, 2L);

        // Then
        assertThat(result).isFalse();
    }

}