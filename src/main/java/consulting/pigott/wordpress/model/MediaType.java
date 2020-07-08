package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = EnumSerializer.class)
@JsonDeserialize(using = MediaTypeDeserializer.class)
public enum MediaType {
    IMAGE,
    FILE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static MediaType fromString(String displayVal) {
        return MediaType.valueOf(displayVal.toUpperCase());
    }
}
