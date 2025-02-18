package com.techpuram.saascore.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.techpuram.saascore.entity.Users;

public class UserDetailSerializer extends JsonSerializer<Users> {

    @Override
    public void serialize(Users user, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (user != null) {
            gen.writeStartObject();
            gen.writeNumberField("userId", user.getUserId());
            gen.writeStringField("username", user.getFirstName()); // Assuming `User` has a `firstName` field
            gen.writeEndObject();
        } else {
            gen.writeNull();
        }
    }
}