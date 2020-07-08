package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaDetailSize {

    @JsonProperty("file")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String file;

    @JsonProperty("width")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer width;

    @JsonProperty("height")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer height;

    @JsonProperty("mime_type")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String mimeType;

    @JsonProperty("source_url")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String sourceUrl;
}
