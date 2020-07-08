package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PostTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void renderedObjectTest() throws IOException {

        String json = "{"+
                "\"title\": {\"rendered\": \"title\"},"+
        "\"content\": {\"rendered\": \"content\"},"+
        "\"guid\": {\"rendered\": \"guid\"},"+
        "\"excerpt\": {\"rendered\": \"excerpt\"}"+
                "}";
        Post post = mapper.readerWithView(JsonViews.Read.class).forType(Post.class).readValue(json);

        String serialized = mapper.writerWithView(JsonViews.Edit.class).writeValueAsString(post);

        Assertions.assertNotNull(serialized);
        Assertions.assertTrue(serialized.contains("\"title\":\"title\""));
        Assertions.assertTrue(serialized.contains("\"guid\":\"guid\""));
        Assertions.assertTrue(serialized.contains("\"content\":\"content\""));
        Assertions.assertTrue(serialized.contains("\"excerpt\":\"excerpt\""));

    }
}
