package com.example.demo.config;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateScalar implements Coercing<Date, String> {
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof Timestamp) {
            return sdf.format(Date.from(((Timestamp) dataFetcherResult).toInstant()));
        } else {
            throw new CoercingSerializeException("Not a valid DateTime");
        }
    }

    @Override
    public Date parseValue(Object input) throws CoercingParseValueException {
        return Date.from(Instant.from(LocalDateTime.parse(input.toString(), DateTimeFormatter.ISO_DATE_TIME)));
    }

    @Override
    public Date parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            return new Date(input.toString());
        }

        throw new CoercingParseLiteralException("Value is not a valid ISO date time");
    }
}
