package com.fabiano.databaseconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.fabiano")
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Bean
    public InternalResourceViewResolver resolver (){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".xhtml");
        resolver.setPrefix("/");
        return resolver;
    }

}

