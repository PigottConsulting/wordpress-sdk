package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenderedAndProtectedObj {
    @JsonProperty("rendered")
    private String rendered;

    @JsonProperty("protected")
    private Boolean _protected;
}
