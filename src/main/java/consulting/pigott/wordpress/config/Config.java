package consulting.pigott.wordpress.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Config {

    private String scheme;
    private String host;
    private Integer port;

}
