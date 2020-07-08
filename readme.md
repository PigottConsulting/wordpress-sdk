#Wordpress SDK

## Overview
This SDK is meant to allow for Java applications to interact with a Wordpress Installation.  It provides for 
authentication and mapping of objects to/from the wordpress API to standard Java Objects.

## Install
This module is available using Maven and can be found in Maven Central.
```xml
<dependency>
    <groupId>consulting.pigott.wordpress</groupId>
    <artifactId>wordpress-sdk</artifactId>
    <version>0.1.0</version>
</dependency>
```


## Examples
### Get All Posts
```java
List<Post> posts = client.getAllPosts(Optional.empty());
```
### Get All Posts Per Category
Note: The category is the id, not the name.
```java
List<Post> posts = client.getAllPosts(Optional.of(Collections.singletonMap("categories","2")));
```
### Get Single Post
```java
Post post = client.getPost("120");
```
### Save Post
This loads a single Post and then deletes the ID, which means it will be a new post when saved.
```java
Post post = client.getPost("174");

post.setTitle(RenderedObj.builder().rendered("A brand new post").build());
post.setId(null);

client.savePost(post);
```

### Media
Media Get/GetAll/Upload/Update is also implemented in the same schema as Posts.
### Configuration 
You can optionally set the port as well if not suing the standard schema port.
```java
WordpressClient client = new WordpressClientImpl(Config.builder()
     .scheme("https")
     .host("blog.example.com")
     .build(), new BasicAuthenticationProvider("username","password"));

```

### Authentication Provider
You can use any authentication provider which implements `AuthenticationProvider`.  There are 2 included in the library. 
* `ApiKeyAuthenticationProvider` handles Bearer Token authentiction.
* `BasicAuthenticationProvider` handles Basic Authentication (user/password).