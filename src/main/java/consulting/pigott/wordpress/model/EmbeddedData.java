package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedData {

    @JsonProperty("author")
    private List<Map<String,Object>> author;

    @JsonProperty("wp:featuredmedia")
    private List<Map<String,Object>> featureImage;
}
