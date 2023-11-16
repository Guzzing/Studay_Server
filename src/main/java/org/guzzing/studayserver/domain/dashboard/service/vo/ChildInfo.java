package org.guzzing.studayserver.domain.dashboard.service.vo;

import org.guzzing.studayserver.domain.child.model.Child;

public record ChildInfo(
        long childId,
        String childNickName
) {

    public static ChildInfo from(final Child entity) {
        return new ChildInfo(entity.getId(), entity.getNickName());
    }

}
