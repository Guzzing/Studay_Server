package org.guzzing.studayserver.domain.child.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.provider.ProfileImageProvider;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.child.service.param.ChildDeleteParam;
import org.guzzing.studayserver.domain.child.service.param.ChildModifyParam;
import org.guzzing.studayserver.domain.child.service.result.ChildProfileImagePatchResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@Service
public class ChildService {

    private final MemberRepository memberRepository;
    private final ChildRepository childRepository;
    private final ProfileImageProvider profileImageProvider;

    public ChildService(MemberRepository memberRepository, ChildRepository childRepository,
            ProfileImageProvider profileImageProvider) {
        this.memberRepository = memberRepository;
        this.childRepository = childRepository;
        this.profileImageProvider = profileImageProvider;
    }

    @Transactional
    public Long create(ChildCreateParam param, Long memberId) {
        Member member = getMember(memberId);

        String defaultProfileImage = getDefaultProfileImageToChild(member);
        Child child = new Child(param.nickname(), param.grade(), defaultProfileImage);

        child.assignToNewMemberOnly(member);

        Child savedChild = childRepository.save(child);
        return savedChild.getId();
    }

    public ChildrenFindResult findByMemberId(Long memberId) {
        Member member = getMember(memberId);
        List<Child> children = member.getChildren();

        return new ChildrenFindResult(children.stream()
                .map(child -> new ChildFindResult(
                        child.getId(),
                        child.getProfileImageURLPath(),
                        child.getNickName(),
                        child.getGrade(),
                        "휴식 중!"))
                .toList());
    }

    @Transactional
    public void delete(ChildDeleteParam param) {
        Child child = getChildByIdAndMemberId(param.childId(), param.memberId());

        child.getMember().removeChild(child.getId());
    }

    @Transactional
    public void removeChild(long memberId) {
        childRepository.deleteByMemberId(memberId);
    }

    @Transactional
    public Long modify(ChildModifyParam param) {
        Child child = getChildByIdAndMemberId(param.childId(), param.memberId());

        child.update(param.nickname(), param.grade());

        return child.getId();
    }

    @Transactional
    public ChildProfileImagePatchResult modifyProfileImage(final Long childId, final MultipartFile file) {
        final Child child = childRepository.findById(childId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이입니다."));

        final String profileImageUri = profileImageProvider.uploadProfileImage(file);

        child.updateProfileImageUri(profileImageUri);

        return new ChildProfileImagePatchResult(childId, child.getProfileImageURLPath());
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 멤버 아이디입니다: " + memberId));
    }

    private Child getChildByIdAndMemberId(Long childId, Long memberId) {
        return childRepository.findByIdAndMemberId(childId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이입니다."));
    }

    private String getDefaultProfileImageToChild(final Member member) {
        final List<String> uris = member.getChildren()
                .stream()
                .map(Child::getProfileImageURIPath)
                .toList();

        return profileImageProvider.provideDefaultProfileImageURI(uris);
    }

}
