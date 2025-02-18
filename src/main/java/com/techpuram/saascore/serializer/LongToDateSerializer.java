package com.techpuram.saascore.serializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LongToDateSerializer extends JsonSerializer<Long> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(format(value));
        } else {
            gen.writeNull();
        }
    }

    // ✅ Static method to reuse everywhere
    public static String format(Long timestamp) {
        if (timestamp == null) return null;
        return FORMATTER.format(Instant.ofEpochMilli(timestamp));
    }
}

