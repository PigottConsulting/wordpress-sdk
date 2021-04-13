package consulting.pigott.wordpress;

import consulting.pigott.wordpress.model.Category;
import consulting.pigott.wordpress.model.Media;
import consulting.pigott.wordpress.model.PagedResponse;
import consulting.pigott.wordpress.model.Post;
import org.apache.hc.core5.http.ContentType;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WordpressClient {

    List<Post> getAllPosts(Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException;

    PagedResponse<Post> getPosts(Integer page, Integer perPage, Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException;

    Post getPost(String id) throws IOException, URISyntaxException;

    Post savePost(Post post) throws IOException, URISyntaxException;

    void deletePost(String id) throws IOException, URISyntaxException;

    //==============  Media  ========================//

    List<Media> getAllMedia(Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException;

    PagedResponse<Media> getMedias(Integer page, Integer perPage, Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException;

    Media getMedia(String id) throws IOException, URISyntaxException;

    Media uploadMedia(File content, ContentType contentType, String filename) throws IOException, URISyntaxException;

    Media updateMedia(Media media) throws IOException, URISyntaxException;

    void deleteMedia(String id) throws IOException, URISyntaxException;

    //==============  Category  ===================//

    List<Category> getAllCategories(Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException;

    PagedResponse<Category> getCategories(Integer page, Integer perPage, Optional<Map<String, String>> queryParams) throws IOException, URISyntaxException;
}
