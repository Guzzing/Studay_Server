package org.guzzing.studay_server;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long reporterId;

    @Column
    private String contents;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportItem item;

    public Report(Long postId, Long reporterId, String contents, ReportItem item) {
        Assert.notNull(postId,"게시글 Id는 null이 들어올 수 없습니다.");
        Assert.notNull(reporterId,"신고자의 Id는 null이 들어올 수 없습니다");
        Assert.notNull(item,"신고항목은 null이 들어올 수 없습니다.");

        this.postId = postId;
        this.reporterId = reporterId;
        this.contents = contents;
        this.item = item;
    }

}
