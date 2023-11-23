package org.guzzing.studayserver.domain.member.service;

import java.util.List;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.provider.ProfileImageProvider;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam.MemberAdditionalChildParam;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult.MemberChildInformationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProfileImageProvider profileImageProvider;

    public MemberService(MemberRepository memberRepository, ProfileImageProvider profileImageProvider) {
        this.memberRepository = memberRepository;
        this.profileImageProvider = profileImageProvider;
    }

    @Transactional
    public Long register(MemberRegisterParam param) {
        Member member = getMember(param.memberId());

        member.update(param.nickname(), param.email());

        for (MemberAdditionalChildParam childParam : param.children()) {
            Child child = new Child(childParam.nickname(), childParam.grade());
            setDefaultProfileImageToChild(child, member);
            child.assignToNewMemberOnly(member);
        }

        return member.getId();
    }

    public MemberInformationResult getById(Long memberId) {
        Member member = getMember(memberId);

        List<MemberChildInformationResult> childInformationResults = member.getChildren().stream()
                .map(child -> new MemberChildInformationResult(child.getId(),
                        child.getNickName(),
                        child.getProfileImageURLPath(),
                        "휴식중!!"))
                .toList();

        return new MemberInformationResult(member.getNickName(), member.getEmail(), childInformationResults);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));
    }

    private void setDefaultProfileImageToChild(final Child child, final Member member) {
        final List<String> uris = member.getChildren()
                .stream()
                .map(Child::getProfileImageURIPath)
                .toList();

        final String defaultProfileImageURI = profileImageProvider.provideDefaultProfileImageURI(uris);

        child.updateProfileImageUri(defaultProfileImageURI);
    }
}
