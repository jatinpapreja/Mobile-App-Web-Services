package com.app.ws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {
	
	Contact contact = new Contact("Jatin Papreja", 
			null, 
			"jatinpapreja@gmail.com");
	
	List<VendorExtension> vendorExtensions = new ArrayList<>();
			
	ApiInfo apiInfo = new ApiInfo("Photo App RESTful Web Service Documentation",
			"This page documents Photo App RESTful Web Service endpoints.",
			"1.0", 
			null, 
			contact, 
			"Apache 2.0", 
			"http://www.apache.org/licenses/LICENSE-2.0", 
			vendorExtensions
			);
	
	@Bean
	public Docket apiDocket() {
		
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.app.ws"))
				.paths(PathSelectors.any())
				.build();
		
		return docket;
	}
	

}
