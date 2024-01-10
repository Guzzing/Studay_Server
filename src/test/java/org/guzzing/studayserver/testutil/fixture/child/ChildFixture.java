package org.guzzing.studayserver.testutil.fixture.child;

import org.guzzing.studayserver.domain.child.model.Child;

public class ChildFixture {

    public static Child child() {
        return new Child(
                "김별",
                "초등학교 1학년",
                "aakdjlaf.com"
        );
    }
}
