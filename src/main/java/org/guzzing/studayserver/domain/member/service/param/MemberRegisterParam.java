package org.guzzing.studayserver.domain.member.service.param;

import java.util.List;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;

public record MemberRegisterParam(
        String nickname,
        String email,
        List<ChildCreateParam> children
) {

}
