package consulting.pigott.wordpress.authentication;

import org.apache.hc.client5.http.classic.methods.HttpUriRequest;

import java.util.Base64;

public class BasicAuthenticationProvider implements AuthenticationProvider {

    private String username;
    private String password;

    public BasicAuthenticationProvider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public HttpUriRequest addAuthenticationToRequest(HttpUriRequest request) {
        request.addHeader("Authorization", "Basic "+ Base64.getEncoder().encodeToString((username+":"+password).getBytes()));
        return request;
    }
}
