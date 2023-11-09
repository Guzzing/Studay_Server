package org.guzzing.studayserver.domain.child.controller.response;

import java.util.List;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;

public record ChildrenFindResponse(
        List<ChildFindResponse> children
) {

    public ChildrenFindResponse(List<ChildFindResponse> children) {
        this.children = List.copyOf(children);
    }

    public static ChildrenFindResponse from(ChildrenFindResult result) {
        List<ChildFindResponse> children = result.children().stream()
                .map(child ->
                        new ChildFindResponse(child.childId(), child.nickname(), child.grade(), child.schedule()))
                .toList();

        return new ChildrenFindResponse(children);
    }

    public record ChildFindResponse(
            Long childId,
            String nickname,
            String grade,
            String schedule
    ) {

    }

}
