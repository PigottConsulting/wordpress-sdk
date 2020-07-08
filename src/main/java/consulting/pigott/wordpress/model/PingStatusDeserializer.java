package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PingStatusDeserializer extends JsonDeserializer<PingStatus> {

    @Override
    public PingStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return PingStatus.fromString(jsonParser.getValueAsString());
    }
}
