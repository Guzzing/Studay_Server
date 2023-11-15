package org.guzzing.studayserver.domain.like.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    @MockBean
    private AcademyAccessService academyAccessService;
    @MockBean
    private MemberAccessService memberAccessService;

    private final Long memberId = 1L;
    private final Long academyId = 1L;
    private LikePostParam param;

    @BeforeEach
    void setUp() {
        LikePostRequest request = new LikePostRequest(academyId);
        param = LikePostRequest.to(request, memberId);
    }

    @Test
    @DisplayName("학원에 대해서 좋아요를 등록한다.")
    @WithMockCustomOAuth2LoginUser
    void createLikeOfAcademy_WithMemberId() {
        // Given & When
        LikePostResult result = likeService.createLikeOfAcademy(param);

        // Then
        assertThat(result.memberId()).isEqualTo(memberId);
        assertThat(result.academyId()).isEqualTo(academyId);
    }

    @Test
    @DisplayName("학원에 대해 등록한 좋아요를 제거한다.")
    @WithMockCustomOAuth2LoginUser
    void removeLikeOfAcademy_LikeId_Remove() {
        // Given
        LikePostResult savedLike = likeService.createLikeOfAcademy(param);

        // When
        likeService.removeLikeOfAcademy(savedLike.likeId(), memberId);

        // Then
        boolean result = likeRepository.existsById(savedLike.likeId());

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("내가 좋아요한 모든 학원 비용 정보를 조회한다.")
    @WithMockCustomOAuth2LoginUser
    void findAllLikesOfMember_MemberId_AcademyInfo() {
        // Given
        given(academyAccessService.findAcademyFeeInfo(any())).willReturn(new AcademyFeeInfo("학원명", 100L));

        LikePostResult savedLike = likeService.createLikeOfAcademy(param);

        // When
        LikeGetResult result = likeService.findAllLikesOfMember(savedLike.memberId());

        // Then
        assertThat(result.likeAcademyInfos()).isNotEmpty();
        assertThat(result.totalFee()).isEqualTo(100);
    }

}
