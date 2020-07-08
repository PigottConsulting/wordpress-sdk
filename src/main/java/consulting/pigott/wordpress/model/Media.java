package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    @JsonProperty("date")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar date;

    @JsonProperty("date_gmt")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar dateGmt;

    @JsonProperty("id")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer id;

    @JsonProperty(value = "guid", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedObj guid;

    @JsonProperty("link")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String link;

    @JsonProperty("modified")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar modified;

    @JsonProperty("modified_gmt")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar modifiedGmt;

    @JsonProperty("slug")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String slug;

    @JsonProperty("status")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String status;

    @JsonProperty("type")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String type;

    @JsonProperty("permalink_template")
    @JsonView({JsonViews.Read.class})
    private String permalinkTemplate;

    @JsonProperty("generated_slug")
    @JsonView({JsonViews.Read.class})
    private String generatedSlug;

    @JsonProperty(value = "title", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedObj title;

    @JsonProperty("author")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer author;

    @JsonProperty("comment_status")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private CommentStatus commentStatus;

    @JsonProperty("ping_status")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private PingStatus pingStatus;

    @JsonProperty("meta")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Meta meta;

    @JsonProperty("template")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String template;

    @JsonProperty("alt_text")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String altText;

    @JsonProperty(value = "caption", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedObj caption;

    @JsonProperty(value = "description", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedObj description;

    @JsonProperty("media_type")
    @JsonView({JsonViews.Read.class})
    private MediaType mediaType;

    @JsonProperty("mime_type")
    @JsonView({JsonViews.Read.class})
    private String mimeType;

    @JsonProperty("media_details")
    @JsonView({JsonViews.Read.class})
    private MediaDetails mediaDetails;

    @JsonProperty("post")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer post;

    @JsonProperty("source_url")
    @JsonView({JsonViews.Read.class})
    private String sourceUrl;

    @JsonProperty(value = "title", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getTitleString() {
        return title == null ? null : title.getRendered();
    }

    @JsonProperty(value = "guid", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getGuidString() {
        return guid == null ? null : guid.getRendered();
    }

    @JsonProperty(value = "caption", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getCaptionString() {
        return caption == null ? null : caption.getRendered();
    }

    @JsonProperty(value = "description", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getDescriptionString() {
        return description == null ? null : description.getRendered();
    }

}
