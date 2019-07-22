package com.qrqs.springbucks.database.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

public class MoneyReadConverter implements Converter<Long, Money> {
    @Override
    public Money convert(Long price) {
        return Money.of(CurrencyUnit.of("CNY"), price);
    }
}
