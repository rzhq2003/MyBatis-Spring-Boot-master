package tk.mybatis.springboot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				/*
				 * .tags 第一个参数必须是Tag，后面的是 Tag 类型的可选参数 new Tag(String,String)
				 * 第一个参数是key，第二个参数是Value。注解@Api#tags传入的是tag的key
				 */
				.tags(new Tag("auth-module-resource", "用户验证"), getTags())
				.select()
				.apis(RequestHandlerSelectors.basePackage("tk.mybatis.springboot.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private Tag[] getTags() {
		Tag[] tags = { 
				new Tag("user-module-resource", "用户管理")
				,new Tag("usrgrp-module-resource", "用户组管理")
				,new Tag("template-module-resource", "模板管理")

				};
		return tags;
	}

    private ApiInfo apiInfo() {
         return new ApiInfoBuilder()
                 .title("平台项目 RESTful APIs")
                 .description("平台项目后台api接口文档")
                 .termsOfServiceUrl("http://10.4.0.57:8080/swagger-ui.html")
                 .contact(new Contact("rzhq2003","",""))
                 .version("1.0")
                 .build();
     }

}