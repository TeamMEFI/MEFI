package com.mefi.backend.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

public interface MailService {

    // 인증 코드 생성
    public String createAuthCode();

    // 메일 내용 생성
    public MimeMessage createMessage(String receiver, int authCode) throws MessagingException, UnsupportedEncodingException;

    // 메일 전송
    public int sendMessage(String email) throws MessagingException, UnsupportedEncodingException;
}
