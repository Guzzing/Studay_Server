package org.guzzing.studayserver.global.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class EtagConfig {
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter()  {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterFilterRegistrationBean =
                new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());

        filterFilterRegistrationBean.addUrlPatterns("/calendar/mark");
        filterFilterRegistrationBean.addUrlPatterns("/children");
        filterFilterRegistrationBean.addUrlPatterns("/academies/*");
        filterFilterRegistrationBean.setName("etagFilter");

        return filterFilterRegistrationBean;
    }

}
