package com.ikdaman.domain.notice.service;

import com.ikdaman.domain.notice.model.NoticeListRes;
import com.ikdaman.domain.notice.model.NoticeReq;
import com.ikdaman.domain.notice.model.NoticeRes;

public interface NoticeService {
    NoticeRes addNotice(NoticeReq noticeReq);
    NoticeListRes getNotices(Integer page, Integer limit);
    NoticeRes getNotice(Long noticeId);
}
