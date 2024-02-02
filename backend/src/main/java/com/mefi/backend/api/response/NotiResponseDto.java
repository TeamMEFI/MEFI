package com.mefi.backend.api.response;

import com.mefi.backend.db.entity.Noti;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NotiResponseDto {

    // 알림 메세지
    private String message;

    // 읽음 여부
    private boolean status;

    // 생성일
    private LocalDateTime createdTime;

    public NotiResponseDto(Noti noti){
        this.message = noti.getMessage();
        this.status = noti.getStatus();
        this.createdTime = noti.getCreatedTime();
    }
}
