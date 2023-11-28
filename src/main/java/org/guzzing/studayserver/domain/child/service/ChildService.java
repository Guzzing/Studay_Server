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
import org.springframework.scheduling.annotation.Scheduled;
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
    @Scheduled(cron = "0 0 0 1 1 *")
    public void increaseGrade() {
        childRepository.findAll()
                .forEach(Child::increaseGrade);
    }

    @Transactional
    public Long create(ChildCreateParam param, Long memberId) {
        Member member = getMember(memberId);

        Child child = new Child(param.nickname(), param.grade());
        child.assignToNewMemberOnly(member);
        setDefaultProfileImageToChild(child);

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
        Member member = getMember(param.memberId());

        member.removeChild(param.childId());
    }

    @Transactional
    public Long modify(ChildModifyParam param) {
        Member member = getMember(param.memberId());

        Child child = member.findChild(param.childId())
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 아이입니다: " + param.childId()));
        child.update(param.nickname(), param.grade());

        return child.getId();
    }

    @Transactional
    public ChildProfileImagePatchResult modifyProfileImage(final Long childId, final MultipartFile file) {
        final Child child = childRepository.findById(childId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이입니다."));

        final String profileImageUri = profileImageProvider.uploadProfileImage(childId, file);

        child.updateProfileImageUri(profileImageUri);

        return new ChildProfileImagePatchResult(childId, profileImageUri);
    }

    private void setDefaultProfileImageToChild(final Child child) {
        Member member = child.getMember();
        final List<String> uris = member.getChildren()
                .stream()
                .filter(c -> !c.equals(child))
                .map(Child::getProfileImageURIPath)
                .toList();

        final String defaultProfileImageURI = profileImageProvider.provideDefaultProfileImageURI(uris);

        child.updateProfileImageUri(defaultProfileImageURI);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 멤버 아이디입니다: " + memberId));
    }

}
