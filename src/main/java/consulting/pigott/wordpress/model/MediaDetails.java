package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaDetails {

    @JsonProperty("width")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer width;

    @JsonProperty("height")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer height;

    @JsonProperty("file")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String file;

    @JsonProperty("sizes")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Map<String,MediaDetailSize> sizes = new HashMap<>();

    /*@JsonProperty("image_meta")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Meta imageMeta;*/
}
