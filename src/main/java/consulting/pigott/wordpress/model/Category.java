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
public class Category {

    @JsonProperty("id")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer id;

    @JsonProperty("count")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer count;

    @JsonProperty("description")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String description;

    @JsonProperty("link")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String link;

    @JsonProperty("name")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String name;

    @JsonProperty("taxonomy")
    @JsonView({JsonViews.Read.class})
    private String taxonomy;

    @JsonProperty("parent")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer parent;
}
