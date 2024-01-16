package org.guzzing.studayserver.domain.member.event;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.Getter;

@Getter
public class WithdrawEvent {

    private final HttpServletRequest request;
    private final List<String> childProfileImageUris;

    public WithdrawEvent(HttpServletRequest request, List<String> childProfileImageUris) {
        this.request = request;
        this.childProfileImageUris = childProfileImageUris;
    }

}
