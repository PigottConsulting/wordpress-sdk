package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class MediaTypeDeserializer extends JsonDeserializer<MediaType> {

    @Override
    public MediaType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return MediaType.fromString(jsonParser.getValueAsString());
    }
}
