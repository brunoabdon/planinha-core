package com.github.brunoabdon.planinha.rest.config;

import static lombok.AccessLevel.PACKAGE;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.brunoabdon.commons.modelo.conv.PeriodoConverter;
import com.github.brunoabdon.commons.modelo.conv.YearMonthConveter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Setter(PACKAGE)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {

        log.info("Registrando conveteres.");

        registry.addConverter(PeriodoConverter.FromString.INSTANCE);
        registry.addConverter(PeriodoConverter.ToString.INSTANCE);
        registry.addConverter(YearMonthConveter.FromString.INSTANCE);
        registry.addConverter(YearMonthConveter.ToString.INSTANCE);

   }

}
