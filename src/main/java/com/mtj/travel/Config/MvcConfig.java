package com.mtj.travel.Config;

import com.mtj.travel.entity.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");

        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();

        converter.setObjectMapper(new JacksonObjectMapper());

        converters.add(0, converter);
    }
}