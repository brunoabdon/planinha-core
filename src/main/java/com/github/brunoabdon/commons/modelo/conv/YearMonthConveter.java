package com.github.brunoabdon.commons.modelo.conv;

import static lombok.AccessLevel.PRIVATE;

import java.time.YearMonth;

import org.springframework.core.convert.converter.Converter;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class YearMonthConveter {

    public enum FromString implements Converter<String, YearMonth>{

        INSTANCE {

            @Override
            public YearMonth convert(final String str) {
                return YearMonth.parse(str);
            }

        }
    }
    public enum ToString implements Converter<YearMonth, String> {

        INSTANCE {

            @Override
            public String convert(final YearMonth yearMonth) {
                return yearMonth.toString();
            }
        }
    }
}
