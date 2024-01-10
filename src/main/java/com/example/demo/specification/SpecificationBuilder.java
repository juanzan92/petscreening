package com.example.demo.specification;

import com.example.demo.entities.enums.Breed;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpecificationBuilder<T> {
    public static SimpleDateFormat YYYY_MM_DD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private Specification<T> specification;

    public SpecificationBuilder() {
        this.specification = Specification.where(null);
    }

    public Specification<T> build() {
        return this.specification;
    }


    public SpecificationBuilder<T> addEqualAttribute(Optional<?> value, String attribute) {
        this.specification = this.specification.and((root, query, builder) -> value.map(val ->
                builder.equal(root.get(attribute), val)
        ).orElse(null));
        return this;
    }
    public SpecificationBuilder<T> addEqualAttribute(Breed value, String attribute) {
        this.specification = this.specification.and((root, query, builder) ->
                builder.equal(root.get(attribute), value)
        );
        return this;
    }

    public SpecificationBuilder<T> addTrue(Optional<?> value, String attribute) {
        this.specification = this.specification.and((root, query, builder) ->
                value.map(val -> builder.isTrue(root.get(attribute))).orElse(null)
        );
        return this;
    }


    public SpecificationBuilder<T> addLikeAttribute(Optional<?> value, String attribute) {
        this.specification = this.specification.and((root, query, builder) -> value.map(val ->
                        builder.like(builder.lower(root.get(attribute)),
                                "%" + val + "%"))
                .orElse(null));
        return this;
    }

    public SpecificationBuilder<T> addInCollection(Optional<List<UUID>> values, String attributeAccessor) {
        this.specification = this.specification.and((root, query, builder) -> values.map(uuids -> builder.in(root.get(attributeAccessor)).value(uuids)).orElse(null));
        return this;
    }

    public static ZonedDateTime parseDate(String dateS) {
        try {
            Date date = YYYY_MM_DD_DATE_FORMAT.parse(dateS.trim());
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("GMT"));
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date", e);
        }
    }
}
