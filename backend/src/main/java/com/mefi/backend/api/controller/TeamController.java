package com.mefi.backend.api.controller;

import com.mefi.backend.api.request.TeamReqDto;
import com.mefi.backend.api.service.TeamService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("team")
@RequiredArgsConstructor
@Slf4j
@Tag(name="2.TEAM", description = "TEAM API")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("")
    @Operation(summary = "팀생성", description = "팀 정보를 받아 팀 생성한다.")
    @ApiResponse(responseCode = "200", description = "성공 \n\n team 식별 아이디 반환")
    public ResponseEntity<? extends BaseResponseBody> createTeam(Authentication authentication, @RequestBody TeamReqDto teamReqDto) throws Exception{

        // 현재 사용자 식별 ID 불러옴
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        log.info("UserID :{}", user.getUserId());
        log.info("teamName :{}", teamReqDto.getTeamName());
        log.info("teamDescription :{}", teamReqDto.getTeamDescription());
        log.info("member1 :{}", teamReqDto.getMembers().get(0));

        // 팀 생성 로직 호출
        teamService.createTeam(user.getUserId(), teamReqDto);

        // 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0,"Success"));
    }

}
