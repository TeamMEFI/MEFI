package com.mefi.backend.api.controller;

import com.mefi.backend.common.model.BaseResponseBody;
import io.openvidu.java.client.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/openvidu/api")
@CrossOrigin(origins = "*")
public class OpenViduController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;    // OpenVidu Deployment URL

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET; // OpenVidu Secret Key

    private OpenVidu openVidu;      // OpenVidu Deployment와 상호작용 하기 위한 객체

    @PostConstruct  // WAS에서 BEAN 초기화 이후 한 번만 호출
    public void init() {
        this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping("/sessions")
    @Operation(summary = "OpenVidu Session 생성", description = "OpenVidu Deployment에 Session 생성을 요청합니다")
    @ApiResponse(responseCode = "200", description = "Session 생성 성공, 세션 ID를 반환합니다. ")
    public ResponseEntity<? extends BaseResponseBody> createSession() {
        try {
            // Session 설정 및 생성
            SessionProperties properties = new SessionProperties.Builder().build();
            Session session = openVidu.createSession(properties);
            log.info("SessionProperties : {}", properties);
            log.info("Session : {}", session.getSessionId());

            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, session.getSessionId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/sessions/{sessionId}/connections")
    @Operation(summary = "OpenVidu Connection 생성", description = "OpenVidu 서버에 특정 세션에 대한 Connection 생성을 요청합니다")
    @ApiResponse(responseCode = "200", description = "Connection이 정상적으로 생성되어 토큰이 반환됩니다")
    public ResponseEntity<? extends BaseResponseBody> createConnection(
            @PathVariable("sessionId") @Parameter(name = "sessionId", description = "사용자가 접속하려는 세션ID") String sessionId
    ){
        try{
            // ID에 해당되는 Session 객체 조회
            Session session = openVidu.getActiveSession(sessionId);
            if(session==null) { // 활성화 된 Session이 없는 경우 NOT_FOUND 응답
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Connection 프로퍼티 설정
            ConnectionProperties properties = new ConnectionProperties.Builder()
                    .type(ConnectionType.WEBRTC)
                    .role(OpenViduRole.PUBLISHER)
                    .data("user_data")
                    .build();

            // Session에 접속하기 위한 Connection 생성
            Connection connection = session.createConnection(properties);
            log.info("Connection Properties : {}", properties);
            log.info("Connection : {}", session.getSessionId());

            return ResponseEntity.status(200).body(BaseResponseBody.of(0, connection));
        } catch (OpenViduJavaClientException e) {
            throw new RuntimeException(e);
        } catch (OpenViduHttpException e) {
            throw new RuntimeException(e);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/sessions/{sessionId}/")
    @Operation(summary = "", description = "")
    @ApiResponse(responseCode = "200", description = "Connection이 정상적으로 생성되어 토큰이 반환됩니다")
    public ResponseEntity<? extends BaseResponseBody> checkCount(@PathVariable("sessionId") String sessionId,
                                             @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);
        try {
            // 해당 세션에 대한 정보를 가져온다
            session.fetch();
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "Proceeding"));
        } catch (OpenViduHttpException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseBody.of(1, "Done"));
        }
    }
}
