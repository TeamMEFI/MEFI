package com.mefi.backend.api.controller;

import com.mefi.backend.api.request.ConferenceCreateReqDto;
import com.mefi.backend.api.response.ConferenceResDto;
import com.mefi.backend.api.service.ConferenceService;
import com.mefi.backend.common.auth.CustomUserDetails;
import com.mefi.backend.common.model.BaseResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting")
@Tag(name="3.CONFERENCE", description="CONFERENCE API")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @Operation(summary = "회의 생성", description = "api/meeting/\n\n 리더는 회의를 생성 가능하다.")
    @PostMapping("")
    @ApiResponse(responseCode = "201", description = "성공 \n\n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> createMeeting(Authentication authentication,
                                                                    @RequestBody ConferenceCreateReqDto conferenceCreateReqDto) {
        // 로그인된 유저 정보 조회
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("\n회의 생성 URL 맵핑: {}", conferenceCreateReqDto.getTitle());
        log.info("\n회의 생성자 : {}", userDetails.getUserId());

        // 회의 생성
        conferenceService.createMeeting(userDetails.getUserId(), conferenceCreateReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, "Success"));
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "팀 회의 이력 조회 API", description = "주어진 기간동안의 팀 회의 이력을 반환한다")
    @ApiResponse(responseCode = "200", description = "성공 시 상태 코드 200와 회의 이력 리스트 반환")
    public ResponseEntity<? extends BaseResponseBody> getConferenceHistory(
            Authentication authentication,
            @PathVariable("teamId") Long teamId,
            @RequestParam("start") String start,
            @RequestParam("end") String end
    ){

        // 로그인된 유저 정보 조회
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        // 팀 회의 이력 조회
        List<ConferenceResDto> histories = conferenceService.getConferenceHistory(user.getUserId(), teamId, start, end);
        log.info("조회된 회의 이력 개수 : {}", histories.size());
        
        // 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, histories));
    }
}
