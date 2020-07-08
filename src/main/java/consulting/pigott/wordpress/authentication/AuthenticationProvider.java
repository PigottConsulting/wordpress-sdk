package consulting.pigott.wordpress.authentication;

import org.apache.hc.client5.http.classic.methods.HttpUriRequest;

public interface AuthenticationProvider {

    HttpUriRequest addAuthenticationToRequest(HttpUriRequest request);
}
