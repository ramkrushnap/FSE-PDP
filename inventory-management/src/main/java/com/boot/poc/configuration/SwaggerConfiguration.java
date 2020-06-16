package com.boot.poc.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	   @Bean
	    public Docket swaggerProductApi() { 
	        return new Docket(DocumentationType.SWAGGER_2)
	       //   .groupName("product-api-1.0")
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())              
	          .paths(PathSelectors.any())                          
	          .build();
	        //  .apiInfo(new ApiInfoBuilder().version("1.0").title("Product Api").description("Documentation for Person API v1.0").build());                                           
	    }
//	   
//	   @Bean
//	    public Docket swaggerNewProductApi() { 
//	        return new Docket(DocumentationType.SWAGGER_2)
//	          .groupName("product-api-1.1")
//	          .select()                                  
//	          .apis(RequestHandlerSelectors.any())              
//	          .paths(regex("/inventory/api/products/v1.1.*"))                          
//	          .build()
//	          .apiInfo(new ApiInfoBuilder().version("1.1").title("Product Api").description("Documentation for Person API v1.1").build());                                           
//	    }
}
