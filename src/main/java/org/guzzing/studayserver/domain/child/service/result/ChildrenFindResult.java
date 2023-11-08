package org.guzzing.studayserver.domain.child.service.result;

import java.util.List;

public record ChildrenFindResult(
        List<ChildFindResult> children
) {

    public ChildrenFindResult(List<ChildFindResult> children) {
        this.children = List.copyOf(children);
    }

    public record ChildFindResult(
            Long childId,
            String nickname,
            String grade,
            String schedule
    ) {

    }

}
