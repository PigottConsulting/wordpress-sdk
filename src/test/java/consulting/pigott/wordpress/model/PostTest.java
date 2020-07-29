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

    @Test
    public void renderedObjectTestEmbeddedInfo() throws IOException {

        String json = "{"+
                "\"title\": {\"rendered\": \"title\"},"+
                "\"content\": {\"rendered\": \"content\"},"+
                "\"guid\": {\"rendered\": \"guid\"},"+
                "\"excerpt\": {\"rendered\": \"excerpt\"},"+
                "\"_embedded\":{\"author\":[{\"id\":3,\"name\":\"Dev Test1\",\"url\":\"\",\"description\":\"\",\"link\":\"https:\\/\\/blog.svc.techinsights.com\\/author\\/dev-test1\\/\",\"slug\":\"dev-test1\",\"avatar_urls\":{\"24\":\"https:\\/\\/secure.gravatar.com\\/avatar\\/ccbb6e043bb15771e80dde5bb9312562?s=24&d=mm&r=g\",\"48\":\"https:\\/\\/secure.gravatar.com\\/avatar\\/ccbb6e043bb15771e80dde5bb9312562?s=48&d=mm&r=g\",\"96\":\"https:\\/\\/secure.gravatar.com\\/avatar\\/ccbb6e043bb15771e80dde5bb9312562?s=96&d=mm&r=g\"},\"_links\":{\"self\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/users\\/3\"}],\"collection\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/users\"}]}}],\"wp:featuredmedia\":[{\"id\":513,\"date\":\"2020-07-28T14:02:44\",\"slug\":\"mp1\",\"type\":\"attachment\",\"link\":\"https:\\/\\/blog.svc.techinsights.com\\/2020\\/07\\/24\\/this-is-a-second-test-post-with-feature_image\\/mp1\\/\",\"title\":{\"rendered\":\"MP1\"},\"author\":3,\"caption\":{\"rendered\":\"\"},\"alt_text\":\"\",\"media_type\":\"image\",\"mime_type\":\"image\\/png\",\"media_details\":{\"width\":266,\"height\":220,\"file\":\"2020\\/07\\/MP1.png\",\"sizes\":{\"thumbnail\":{\"file\":\"MP1-150x150.png\",\"width\":150,\"height\":150,\"mime_type\":\"image\\/png\",\"source_url\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-content\\/uploads\\/2020\\/07\\/MP1-150x150.png\"},\"full\":{\"file\":\"MP1.png\",\"width\":266,\"height\":220,\"mime_type\":\"image\\/png\",\"source_url\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-content\\/uploads\\/2020\\/07\\/MP1.png\"}},\"image_meta\":{\"aperture\":\"0\",\"credit\":\"\",\"camera\":\"\",\"caption\":\"\",\"created_timestamp\":\"0\",\"copyright\":\"\",\"focal_length\":\"0\",\"iso\":\"0\",\"shutter_speed\":\"0\",\"title\":\"\",\"orientation\":\"0\",\"keywords\":[]}},\"source_url\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-content\\/uploads\\/2020\\/07\\/MP1.png\",\"_links\":{\"self\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/media\\/513\"}],\"collection\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/media\"}],\"about\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/types\\/attachment\"}],\"author\":[{\"embeddable\":true,\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/users\\/3\"}],\"replies\":[{\"embeddable\":true,\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/comments?post=513\"}]}}],\"wp:term\":[[{\"taxonomy\":\"post_tag\",\"embeddable\":true,\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/tags?post=522\"},{\"id\":2,\"link\":\"https:\\/\\/blog.svc.techinsights.com\\/category\\/image-sensor\\/\",\"name\":\"Image Sensor\",\"slug\":\"image-sensor\",\"taxonomy\":\"category\",\"_links\":{\"self\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/categories\\/2\"}],\"collection\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/categories\"}],\"about\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/taxonomies\\/category\"}],\"wp:post_type\":[{\"href\":\"https:\\/\\/blog.svc.techinsights.com\\/wp-json\\/wp\\/v2\\/posts?categories=2\"}],\"curies\":[{\"name\":\"wp\",\"href\":\"https:\\/\\/api.w.org\\/{rel}\",\"templated\":true}]}}],[]]}}"+
                "}";
        Post post = mapper.readerWithView(JsonViews.Read.class).forType(Post.class).readValue(json);

        Assertions.assertNotNull(post.getAuthorName());
        Assertions.assertNotNull(post.getAuthorAvatars());
        Assertions.assertNotNull(post.getFirstFeaturedImageSrc());
        Assertions.assertNotNull(post.getTagsNames());
    }

}
