package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = EnumSerializer.class)
@JsonDeserialize(using = FormatDeserializer.class)
public enum Format {
    STANDARD,
    ASIDE,
    CHAT,
    GALLERY,
    LINK,
    IMAGE,
    QUOTE,
    STATUS,
    VIDEO,
    AUDIO
    ;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static Format fromString(String displayVal) {
        return Format.valueOf(displayVal.toUpperCase());
    }
}
