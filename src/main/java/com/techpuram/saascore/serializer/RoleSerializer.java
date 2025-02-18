package com.techpuram.saascore.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.techpuram.saascore.entity.Roles;

public class RoleSerializer extends StdSerializer<Roles> {

    public RoleSerializer() {
        this(null);
    }

    public RoleSerializer(Class<Roles> t) {
        super(t);
    }

    @Override
    public void serialize(Roles role, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (role != null) {
            gen.writeStartObject();
            gen.writeStringField("id", String.valueOf(role.getRoleId()));
            gen.writeStringField("name", role.getRoleName());
            gen.writeEndObject();
        } else {
            gen.writeNull();
        }
    }
}
