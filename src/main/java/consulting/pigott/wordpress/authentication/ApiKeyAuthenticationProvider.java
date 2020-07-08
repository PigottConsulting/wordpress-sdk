package consulting.pigott.wordpress.authentication;

import org.apache.hc.client5.http.classic.methods.HttpUriRequest;

public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private String apiKey;

    public ApiKeyAuthenticationProvider(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public HttpUriRequest addAuthenticationToRequest(HttpUriRequest request) {
        request.addHeader("Authorization","Bearer "+this.apiKey);
        return request;
    }
}
