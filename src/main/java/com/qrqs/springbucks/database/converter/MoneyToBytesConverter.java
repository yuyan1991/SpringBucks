package com.qrqs.springbucks.database.converter;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.nio.charset.StandardCharsets;

@WritingConverter
public class MoneyToBytesConverter implements Converter<Money, byte[]> {
    @Override
    public byte[] convert(Money money) {
        String res = money.getAmount().toString();
        return res.getBytes(StandardCharsets.UTF_8);
    }
}
