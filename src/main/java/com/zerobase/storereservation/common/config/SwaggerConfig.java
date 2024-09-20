package com.zerobase.storereservation.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // 협업을 위한 Swagger 설정. 각 컨트롤러에 summary, description 작성 완료
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    public Info apiInfo() {
        return new Info()
                .title("매장 예약 서비스")
                .description("매장 검색, 예약, 리뷰를 작성할수 있는 API 입니다.")
                .version("1.0");
    }
}
