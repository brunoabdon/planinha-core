package com.github.brunoabdon.planinha.rest.config;

import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.commons.modelo.conv.YearMonthConveter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static lombok.AccessLevel.PACKAGE;

@Slf4j
@Configuration
@Setter(PACKAGE)
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Converter<Periodo, String> periodoStringConverter;

    @Autowired
    private Converter<String, Periodo> stringPeriodoConverter;

    @Override
    public void addFormatters(final FormatterRegistry registry) {

        log.info("Registrando conveteres.");

        registry.addConverter(stringPeriodoConverter);
        registry.addConverter(periodoStringConverter);
        registry.addConverter(YearMonthConveter.FromString.INSTANCE);
        registry.addConverter(YearMonthConveter.ToString.INSTANCE);
   }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        log.debug("Configurando CORS promiscuo.");
        registry.addMapping("/**");
    }
}
