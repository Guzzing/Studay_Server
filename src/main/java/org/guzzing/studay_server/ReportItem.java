package org.guzzing.studay_server;

import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Arrays;

public enum ReportItem {
    FAKED_SALE("허위 매물", 5),
    SPAMMING("도배글", 1),
    SWEAR_WORD("욕설", 3),
    SENSATIONAL("선정성", 5),
    PERSONAL_INFORMATION_EXPOSURE("개인정보노출", 2),
    COMMENTS("기타사항",1);

    private final String description;
    private final int score;

    ReportItem(String description, int score) {
        this.description = description;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public static ReportItem find(String reportItem) throws ChangeSetPersister.NotFoundException {
        return Arrays.stream(ReportItem.values()).filter(r->r.equals(reportItem)).
                findFirst().orElseThrow(()-> new ChangeSetPersister.NotFoundException());
    }

}

