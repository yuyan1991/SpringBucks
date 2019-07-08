package com.qrqs.springbucks.database.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.nio.charset.StandardCharsets;

@ReadingConverter
public class BytesToMoneyConverter implements Converter<byte[], Money> {
    @Override
    public Money convert(byte[] bytes) {
        String value = new String(bytes, StandardCharsets.UTF_8);
        return Money.of(CurrencyUnit.of("CNY"), Double.parseDouble(value));
    }
}
