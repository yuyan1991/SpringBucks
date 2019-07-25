package com.qrqs.springbucks.database.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MoneyReadConverter implements Converter<Long, Money> {
    @Override
    public Money convert(Long price) {
        return Money.ofMinor(CurrencyUnit.of("CNY"), price);
    }
}
