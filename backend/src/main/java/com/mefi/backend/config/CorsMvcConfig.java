package com.mefi.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

	@Value("${FRONTEND_URL}")
	private String FRONTEND_URL;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        List<String> origins = List.of(FRONTEND_URL);

        // CORS 설정을 모든 경로에 대해서 적용
        corsRegistry.addMapping("/**")
                // 오리진(도메인)에서 온 요청을 허용
                .allowedOrigins(String.join(",", origins))
                .allowedMethods("*");
    }
}
