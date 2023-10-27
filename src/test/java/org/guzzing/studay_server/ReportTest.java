package org.guzzing.studay_server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    @DisplayName("필수로 들어가야 하는 게시글 id가 들어가지 않은 경우 예외를 던진다.")
    public void createReport_nullPostId_throwIllegalArgumentException() {
        //given
        Long postId = null;
        Long reporterId = 2L;
        String contents = "This is a report.";
        ReportItem item = ReportItem.FAKED_SALE;

        //when_then
        assertThrows(IllegalArgumentException.class,()->new Report(postId, reporterId, contents, item));
    }

    @Test
    @DisplayName("필수로 들어가야 하는 신고자 id가 들어가지 않는 경우 예외를 던진다.")
    public void createReport_nullReporterId_throwIllegalArgumentException() {
        //given
        Long postId = 1L;
        Long reporterId = null;
        String contents = "This is a report.";
        ReportItem item = ReportItem.FAKED_SALE;

        //when_then
        assertThrows(IllegalArgumentException.class,()->new Report(postId, reporterId, contents, item));
    }

    @Test
    @DisplayName("필수로 들어가야 하는 신고 항목이 들어가지 않은 경우 예외를 던진다.")
    public void createReport_nullItem_throwIllegalArgumentException() {
        //given
        Long postId = 1L;
        Long reporterId = 2L;
        String contents = "This is a report.";
        ReportItem item = null;

        //when_then
        assertThrows(IllegalArgumentException.class,()->new Report(postId, reporterId, contents, item));
    }
}


