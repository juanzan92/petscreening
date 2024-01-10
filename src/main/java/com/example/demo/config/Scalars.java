package com.example.demo.config;

import graphql.schema.GraphQLScalarType;

public class Scalars {


    public static GraphQLScalarType localDateTimeType() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Date type")
                .coercing(new DateScalar())
                .build();
    }
}
