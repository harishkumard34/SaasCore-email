package com.techpuram.saascore.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.techpuram.saascore.entity.Profiles;

public class ProfileSerializer extends StdSerializer<Profiles> {

    public ProfileSerializer() {
        this(null);
    }

    public ProfileSerializer(Class<Profiles> t) {
        super(t);
    }

    @Override
    public void serialize(Profiles profile, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (profile != null) {
            gen.writeStartObject();
            gen.writeStringField("id", String.valueOf(profile.getProfileId()));
            gen.writeStringField("name", profile.getProfileName());
            gen.writeEndObject();
        } else {
            gen.writeNull();
        }
    }
}
