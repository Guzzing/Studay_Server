package org.guzzing.studayserver.domain.like.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.guzzing.studayserver.testutil.fixture.member.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LikeFacadeTest {

    @Autowired
    private LikeFacade likeFacade;

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AcademyRepository academyRepository;

    private Member savedMember;
    private Academy savedAcademy;
    private Like like;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(MemberFixture.makeMemberEntity());
        savedAcademy = academyRepository.save(AcademyFixture.academySungnam());
        like = Like.of(savedMember, savedAcademy);
    }

    @Test
    @DisplayName("학원에 대해서 좋아요를 등록한다.")
    void createLikeOfAcademy_WithMemberId() {
        // Given & When
        LikePostRequest request = new LikePostRequest(savedAcademy.getId());
        LikePostParam param = LikePostRequest.to(request, savedMember.getId());
        LikePostResult result = likeFacade.createLikeOfAcademy(param);

        // Then
        assertThat(result.memberId()).isEqualTo(savedMember.getId());
        assertThat(result.academyId()).isEqualTo(savedAcademy.getId());
    }

    @Test
    @DisplayName("학원에 대해 등록한 좋아요를 제거한다.")
    void removeLikeOfAcademy_LikeId_Remove() {
        // Given
        Like savedLike = likeRepository.save(like);

        // When
        likeFacade.removeLike(savedLike.getId(), savedLike.getMemberId());

        // Then
        boolean result = likeRepository.existsById(savedLike.getId());

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("학원 아이디로 등록한 좋아요를 제거한다.")
    void deleteLikeOfAcademy_AcademyId_Delete() {
        // Given
        Like savedLike = likeRepository.save(like);

        // When
        likeFacade.deleteLikeOfAcademy(savedLike.getMemberId(), savedLike.getAcademyId());

        // Then
        Member member = memberRepository.findById(savedLike.getMemberId())
                .orElseThrow(EntityNotFoundException::new);

        List<Long> result = likeRepository.findByMember(member)
                .stream()
                .map(Like::getAcademyId)
                .filter(id -> Objects.equals(id, savedLike.getAcademyId()))
                .toList();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("내가 좋아요한 모든 학원 비용 정보를 조회한다.")
    void findAllLikesOfMember_MemberId_AcademyInfo() {
        // Given
        Like savedLike = likeRepository.save(like);

        // When
        LikeGetResult result = likeFacade.getAllLikesOfMember(savedLike.getMemberId());

        // Then
        assertThat(result.likeAcademyInfos()).isNotEmpty();
        assertThat(result.totalFee()).isZero();
    }

    @Test
    @DisplayName("멤버가 해당 학원에 대해서 좋아요를 했으면 True 를 반환한다.")
    void isLiked_MemberAndAcademy_ReturnTrue() {
        // Given
        Like savedLike = likeRepository.save(like);

        // When
        final boolean result = likeFacade.isLiked(savedLike.getMemberId(), savedLike.getAcademyId());

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("멤버가 해당 학원에 대해 좋아요를 하지 않았으면 False 를 반환한다.")
    void isLiked_MemberAndAcademy_ReturnFalse() {
        // Given
        Academy otherAcademy = academyRepository.save(AcademyFixture.twoCategoriesAcademy());

        // When
        final boolean result = likeFacade.isLiked(savedMember.getId(), otherAcademy.getId());

        // Then
        assertThat(result).isFalse();
    }

}
