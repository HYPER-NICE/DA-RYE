package hyper.darye.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "basicAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("다례 (Da-Rye) API 문서")
                        .version("1.0.0")
                        .description("""
                                ## 다례(茶禮) 프로젝트
                                다례는 한국 전통의 차 문화와 예절을 의미하며, 차를 우려내고 대접하는 과정에서 예의를 갖춘 의식적인 행위를 가리킵니다.
                                
                                이 프로젝트는 다례 문화를 기반으로 재화의 유통을 목표로 하며, 백엔드와 프론트엔드로 구성됩니다.
                                
                                ### Da-Gwan
                                **다관(茶罐)** 은 백엔드 프로젝트의 이름입니다. 
                                다관은 차를 우려내는 도구로, 데이터를 처리하고 필요한 정보를 제공합니다.
                                """)
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")));
    }
}
