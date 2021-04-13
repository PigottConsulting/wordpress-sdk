package consulting.pigott.wordpress;

import consulting.pigott.wordpress.authentication.AuthenticationProvider;
import consulting.pigott.wordpress.config.Config;
import consulting.pigott.wordpress.model.JsonViews;
import consulting.pigott.wordpress.model.Media;
import consulting.pigott.wordpress.model.PagedResponse;
import consulting.pigott.wordpress.model.Post;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WordpressClientImpl implements WordpressClient {

    private Config config;
    private HttpClient httpClient;
    private AuthenticationProvider authenticationProvider;

    private ObjectMapper mapper;

    public WordpressClientImpl(Config config, AuthenticationProvider authenticationProvider) throws IOException {
        this.config = config;
        this.authenticationProvider = authenticationProvider;

        this.httpClient = HttpClientBuilder.create()
                .build();

        mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    //=============== POSTS  ========================//

    @Override
    public List<Post> getAllPosts(Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException {
        return getAll(this::getPosts, queryParams);
    }

    @Override
    public PagedResponse<Post> getPosts(Integer page, Integer perPage, Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException {
        URIBuilder builder = this.makeBaseURIBuilder(page, perPage)
                .setPathSegments("wp-json", "wp", "v2", "posts")
                .setParameter("_embed", null);
        if (queryParams.isPresent()) {
            for (String key : queryParams.get().keySet()) {
                builder.addParameter(key, queryParams.get().get(key));
            }
        }

        return this.executeList(
                this.makeStandardRequest(new HttpGet(builder.build().toURL().toString())),
                Post.class,
                JsonViews.Read.class);
    }

    @Override
    public Post getPost(String id) throws IOException, URISyntaxException {
        return this.executeSingle(
                this.makeStandardRequest(
                        new HttpGet(
                                this.makeBaseURIBuilder()
                                        .setPathSegments("wp-json", "wp", "v2", "posts", id)
                                        .setParameter("_embed",null)
                                        .build().toURL().toString()
                        )
                ),
                Post.class,
                JsonViews.Read.class);
    }

    @Override
    public Post savePost(Post post) throws IOException, URISyntaxException {
        //Check if its new or existing.
        Boolean isExisting = null != post.getId();

        String url = isExisting ?
                this.makeBaseURIBuilder().setPathSegments("wp-json", "wp", "v2", "posts", post.getId().toString())
                        .build().toURL().toString() :
                this.makeBaseURIBuilder().setPathSegments("wp-json", "wp", "v2", "posts")
                        .build().toURL().toString();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(mapper.writerWithView(JsonViews.Edit.class).writeValueAsString(post), ContentType.APPLICATION_JSON));

        return this.executeSingle(
                this.makeStandardRequest(httpPost),
                Post.class,
                JsonViews.Edit.class
        );
    }

    @Override
    public void deletePost(String id) throws IOException, URISyntaxException {
        this.executeSingle(
                this.makeStandardRequest(
                        new HttpDelete(
                                this.makeBaseURIBuilder()
                                        .setPathSegments("wp-json", "wp", "v2", "posts", id)
                                        .build().toURL().toString()
                        )
                ),
                Post.class,
                JsonViews.Read.class);
    }

    //==============  Media  ========================//

    @Override
    public List<Media> getAllMedia(Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException {
        return getAll(this::getMedias, queryParams);
    }

    @Override
    public PagedResponse<Media> getMedias(Integer page, Integer perPage, Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException {

        URIBuilder builder = this.makeBaseURIBuilder(page, perPage)
                        .setPathSegments("wp-json", "wp", "v2", "media");

        if (queryParams.isPresent()) {
            for (String key : queryParams.get().keySet()) {
                builder.addParameter(key, queryParams.get().get(key));
            }
        }

        return this.executeList(
                this.makeStandardRequest(
                        new HttpGet(builder.build().toURL().toString())
                ),
                Media.class,
                JsonViews.Read.class);
    }

    @Override
    public Media getMedia(String id) throws IOException, URISyntaxException {
        return this.executeSingle(
                this.makeStandardRequest(
                        new HttpGet(
                                this.makeBaseURIBuilder()
                                        .setPathSegments("wp-json", "wp", "v2", "media", id)
                                        .build().toURL().toString()
                        )
                ),
                Media.class,
                JsonViews.Read.class);
    }

    @Override
    public Media uploadMedia(File content, ContentType contentType, String filename) throws IOException, URISyntaxException {
        String url = this.makeBaseURIBuilder()
                .setPathSegments("wp-json", "wp", "v2", "media")
                .build().toURL().toString();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new FileEntity(content, contentType));
        httpPost.addHeader("Content-Disposition", "attachment; filename=" + filename);

        return this.executeSingle(
                this.makeStandardRequest(httpPost),
                Media.class,
                JsonViews.Edit.class
        );

    }

    @Override
    public Media updateMedia(Media media) throws IOException, URISyntaxException {
        String url = this.makeBaseURIBuilder()
                .setPathSegments("wp-json", "wp", "v2", "media", media.getId().toString())
                .build().toURL().toString();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(mapper.writerWithView(JsonViews.Edit.class).writeValueAsString(media), ContentType.APPLICATION_JSON));

        return this.executeSingle(
                this.makeStandardRequest(httpPost),
                Media.class,
                JsonViews.Edit.class
        );
    }

    @Override
    public void deleteMedia(String id) throws IOException, URISyntaxException {
        this.executeSingle(
                this.makeStandardRequest(
                        new HttpDelete(
                                this.makeBaseURIBuilder()
                                        .setPathSegments("wp-json", "wp", "v2", "media", id)
                                        .build().toURL().toString()
                        )
                ),
                Media.class,
                JsonViews.Read.class);
    }


    //================= Support Methods  ==================//

    private <T> List<T> getAll(EntityGetter<T> getter, Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException {
        List<T> entities = new ArrayList<>();
        boolean lastPage = false;
        int pageNum = 1;
        while (!lastPage) {

            PagedResponse<T> response = getter.get(pageNum, 20,queryParams);
            if (response.getPages() == null) {
                response.setPages(0);
                response.setTotal(0);
            }

            entities.addAll(response);
            if (pageNum < response.getPages()) {
                pageNum++;
            } else {
                lastPage = true;
            }

        }
        return entities;
    }

    private HttpUriRequest makeStandardRequest(HttpUriRequest request) {
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Accept", "application/json");
        return this.authenticationProvider.addAuthenticationToRequest(request);
    }

    private URIBuilder makeBaseURIBuilder(Integer page, Integer perPage) throws MalformedURLException, URISyntaxException {
        return makeBaseURIBuilder()
                .addParameter("page", page.toString()).addParameter("per_page", perPage.toString());
    }

    private URIBuilder makeBaseURIBuilder() throws URISyntaxException, MalformedURLException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(config.getScheme())
                .setHost(config.getHost());
        if (config.getPort() != null) {
            uriBuilder.setPort(config.getPort());
        }
        return uriBuilder;
    }

    private <T> T executeSingle(HttpUriRequest request, Class<T> entityClass, Class<?> viewClass) throws IOException {
        SingleResponseHandler handler = new SingleResponseHandler();
        String responseBody = httpClient.execute(request, handler);
        return mapper.readerWithView(viewClass).forType(entityClass).readValue(responseBody);
    }

    private <T> PagedResponse<T> executeList(HttpUriRequest request, Class<T> entityClass, Class<?> viewClass) throws IOException {
        PagedResponseHandler handler = new PagedResponseHandler();
        String responseBody = httpClient.execute(request, handler);
        PagedResponse<T> pagedResponse = new PagedResponse<>();
        pagedResponse.setTotal(handler.totalEntries);
        pagedResponse.setPages(handler.totalPages);


        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, entityClass);

        List<T> entries = mapper.readerWithView(viewClass).forType(type).readValue(responseBody);

        pagedResponse.addAll(entries);
        return pagedResponse;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }


    private class SingleResponseHandler implements HttpClientResponseHandler<String> {
        @Override
        public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {

            final int status = response.getCode();
            if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                final HttpEntity entity = response.getEntity();
                try {
                    return entity != null ? EntityUtils.toString(entity) : null;
                } catch (final ParseException ex) {
                    throw new ClientProtocolException(ex);
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }

    private class PagedResponseHandler implements HttpClientResponseHandler<String> {

        public Integer totalPages;
        public Integer totalEntries;

        @Override
        public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {

            final int status = response.getCode();
            if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                final HttpEntity entity = response.getEntity();
                try {
                    totalEntries = Integer.valueOf(response.getHeader("X-Wp-Total").getValue());
                    totalPages = Integer.valueOf(response.getHeader("X-Wp-Totalpages").getValue());
                    return entity != null ? EntityUtils.toString(entity) : null;
                } catch (final ParseException ex) {
                    throw new ClientProtocolException(ex);
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }

    private interface EntityGetter<T> {
        PagedResponse<T> get(Integer page, Integer perPage, Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException;
    }

}
