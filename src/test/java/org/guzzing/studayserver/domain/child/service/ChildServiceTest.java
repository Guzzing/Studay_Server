package org.guzzing.studayserver.domain.child.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

import jakarta.persistence.EntityNotFoundException;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.global.profile.ProfileImageUriProvider;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.child.service.param.ChildDeleteParam;
import org.guzzing.studayserver.domain.child.service.param.ChildModifyParam;
import org.guzzing.studayserver.domain.child.service.result.ChildProfileImagePatchResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ChildServiceTest {

    @Autowired
    private ChildService childService;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private ProfileImageUriProvider profileImageUriProvider;

    @DisplayName("아이를 등록한다.")
    @Test
    void create_success() {
        // Given
        String childNickname = "아이 닉네임";
        String childGrade = "초등학교 1학년";

        Member member = createMember("123");

        ChildCreateParam param = new ChildCreateParam(childNickname, childGrade);

        given(profileImageUriProvider.provideDefaultProfileImageURI(anyList()))
                .willReturn("image.png");

        // When
        Long savedChildId = childService.create(param, member.getId());

        // Then
        assertThat(member.getChildren()).isNotEmpty();
    }

    @DisplayName("멤버가 존재하지 않는다면 아이가 생성되지 않는다.")
    @Test
    void create_throwInvalidMemberIdException() {
        // Given
        Long nonExistenceMemberId = 1L;

        String childNickname = "아이 닉네임";
        String childGrade = "초등학교 1학년";

        ChildCreateParam param = new ChildCreateParam(childNickname, childGrade);

        // When & Then
        assertThatThrownBy(() -> childService.create(param, nonExistenceMemberId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 멤버 아이디입니다: " + nonExistenceMemberId);
    }

    @DisplayName("멤버에게 할당된 아이의 수가 최대치를 넘을 경우 예외를 발생시킨다")
    @Test
    void create_throwChildLimitExceededException() {
        // Given
        Member member = createMember("123");

        createChild("아이 닉네임", member);
        createChild("아이 닉네임", member);
        createChild("아이 닉네임", member);
        createChild("아이 닉네임", member);
        createChild("아이 닉네임", member);

        ChildCreateParam param = new ChildCreateParam("아이 닉네임", "초등학교 1학년");
        given(profileImageUriProvider.provideDefaultProfileImageURI(anyList()))
                .willReturn("image.png");

        // When & Then
        assertThatThrownBy(() -> childService.create(param, member.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("멤버당 아이는 최대 %d까지 등록할 수 있습니다.", Member.CHILDREN_MAX_SIZE));
    }

    @DisplayName("멤버의 아이들의 정보를 반환한다.")
    @Test
    void findByMemberId_success() {
        // Given
        Member member = createMember("123");

        createChild("아이 닉네임", member);
        createChild("아이 닉네임", member);

        // When
        ChildrenFindResult result = childService.findByMemberId(member.getId());

        // Then
        assertThat(result.children()).hasSize(2);
    }

    @DisplayName("존재하지 않는 멤버일 경우, 아이를 찾을 수 없다.")
    @Test
    void findByMemberId_throwException() {
        // Given
        Long nonExistenceMemberId = 1L;

        // When & Then
        assertThatThrownBy(() -> childService.findByMemberId(nonExistenceMemberId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 멤버 아이디입니다: " + nonExistenceMemberId);
    }

    @DisplayName("할당된 아이를 삭제한다.")
    @Test
    void deleteChild() {
        // Given
        Member member = createMember("123");

        Child child = createChild("아이 닉네임", member);

        ChildDeleteParam param = new ChildDeleteParam(member.getId(), child.getId());

        // When
        childService.delete(param);
        ChildrenFindResult result = childService.findByMemberId(member.getId());

        // Then
        assertThat(result.children()).isEmpty();
    }

    @DisplayName("멤버에 할당되지 않은 아이일 경우 삭제하지 않는다.")
    @Test
    void deleteChild_throwException() {
        Member member1 = createMember("123");
        Member member2 = createMember("124");

        Child child = createChild("아이 닉네임", member1);

        ChildDeleteParam param = new ChildDeleteParam(member2.getId(), child.getId());

        // When & Then
        assertThatThrownBy(() -> childService.delete(param))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("존재하지 않는 아이입니다.");
    }

    @DisplayName("원하는 아이의 정보를 수정한다.")
    @Test
    void modify_success() {
        Member member = createMember("123");

        Child child = createChild("원래 아이 닉네임", member);

        // Given
        ChildModifyParam param = new ChildModifyParam("수정할 아이 닉네임", "초등학교 2학년", member.getId(), child.getId());

        // When
        Long updatedChildId = childService.modify(param);

        // Then
        assertThat(updatedChildId).isEqualTo(param.childId());
    }

    @DisplayName("멤버에 존재하지 않는 아이라면 예외를 발생시킨다.")
    @Test
    void modify_throwException() {
        // Given
        Member member = createMember("123");

        ChildModifyParam param = new ChildModifyParam("수정할 아이 닉네임", "초등학교 2학년", member.getId(), 1L);

        // When & Then
        assertThatThrownBy(() -> childService.modify(param))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("존재하지 않는 아이입니다.");
    }

    @Test
    @DisplayName("아이의 프로필 이미지를 변경한다.")
    void modifyProfileImage_ChildIdAndMultipartFile_ChangeProfileImageUri() {
        // Given
        Member member = createMember("123");

        Child child = createChild("아이 닉네임", member);

        final String fileName = "file.png";
        final byte[] content = "file-content".getBytes();
        final MockMultipartFile multipartFile = new MockMultipartFile(fileName, content);

        given(profileImageUriProvider.provideCustomProfileImageURI(any(), any())).willReturn("YAAAAAAA");

        // When
        ChildProfileImagePatchResult result = childService.modifyProfileImage(child.getId(), multipartFile);

        // Then
        assertThat(result.childId()).isEqualTo(child.getId());
    }

    private Member createMember(String socialId) {
        Member member = Member.of(new NickName("멤버 닉네임"), socialId, MemberProvider.KAKAO, RoleType.USER);
        return memberRepository.save(member);
    }

    private Child createChild(String nickname, Member member) {
        given(profileImageUriProvider.provideDefaultProfileImageURI(anyList()))
                .willReturn("image.png");
        Long childId = childService.create(new ChildCreateParam(nickname, "초등학교 1학년"), member.getId());

        return childRepository.findById(childId)
                .orElseThrow(() -> new IllegalStateException("아이를 생성하지 못했습니다."));
    }
}
