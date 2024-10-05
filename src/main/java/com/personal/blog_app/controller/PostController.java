package com.personal.blog_app.controller;

import com.personal.blog_app.dto.ApiResponseDto;
import com.personal.blog_app.dto.PostDto;
import com.personal.blog_app.dto.PostResponse;
import com.personal.blog_app.service.IPostService;
import com.personal.blog_app.service.impl.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import static com.personal.blog_app.controller.constant.ApiConstant.*;

@RestController
@RequestMapping(value = API)
public class PostController {

    private final IPostService postService;
    private final FileService fileService;

    @Value("${project:image}")
    private String path;

    public PostController(IPostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    // createPost
    @PostMapping(value = USERS + USER_ID + CATEGORY + CATEGORY_ID + POST + NEW, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId) {
        PostDto post = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // updatePost
    @PutMapping(value = USERS + USER_ID + CATEGORY + CATEGORY_ID + POST + POST_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId, @PathVariable int postId) {
        PostDto post = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(post);
    }

    // getPost
    @GetMapping(value = POST + POST_ID)
    public ResponseEntity<PostDto> getPost(@PathVariable int postId) {
        PostDto post = postService.getPost(postId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // getAllPost
    @GetMapping(value = POST + ALL)
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = PAGE_NUMBER, defaultValue = ZERO, required = false) int pageNo,
            @RequestParam(value = PAGE_SIZE, defaultValue = ONE, required = false) int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = ID, required = false) String sortBy,
            @RequestParam(value = SORT_DIR, defaultValue = ASC, required = false) String sortDir) {
        PostResponse post = postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // getAllPostByUser
    @GetMapping(value = USERS + USER_ID + POST + ALL)
    public ResponseEntity<PostResponse> getAllPostByUser(
            @PathVariable int userId,
            @RequestParam(value = PAGE_NUMBER, defaultValue = ZERO, required = false) int pageNo,
            @RequestParam(value = PAGE_SIZE, defaultValue = ONE, required = false) int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = ID, required = false) String sortBy,
            @RequestParam(value = SORT_DIR, defaultValue = ASC, required = false) String sortDir) {
        PostResponse post = postService.getAllPostByUser(userId, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // getAllPostByCategory
    @GetMapping(value = CATEGORY + CATEGORY_ID + POST + ALL)
    public ResponseEntity<PostResponse> getAllPostByCategory(
            @PathVariable int categoryId,
            @RequestParam(value = PAGE_NUMBER, defaultValue = ZERO, required = false) int pageNo,
            @RequestParam(value = PAGE_SIZE, defaultValue = ONE, required = false) int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = ID, required = false) String sortBy,
            @RequestParam(value = SORT_DIR, defaultValue = ASC, required = false) String sortDir) {
        PostResponse post = postService.getAllPostByCategory(categoryId, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping(value = POST + SEARCH + KEYWORD)
    public ResponseEntity<PostResponse> searchPost(
            @PathVariable String keyword,
            @RequestParam(value = PAGE_NUMBER, defaultValue = ZERO, required = false) int pageNo,
            @RequestParam(value = PAGE_SIZE, defaultValue = ONE, required = false) int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = ID, required = false) String sortBy,
            @RequestParam(value = SORT_DIR, defaultValue = ASC, required = false) String sortDir) {
        PostResponse post = postService.searchPost(keyword, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(post);
    }

    // deletePost
    @DeleteMapping(value = POST + POST_ID)
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable int postId) {
        PostDto post = postService.deletePost(postId);
        ApiResponseDto apiResponseDto = new ApiResponseDto(String.format("Post: %s deleted successfully", post.getTitle()), true);
        return ResponseEntity.ok(apiResponseDto);
    }

    @PostMapping(value = "post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable int id) throws IOException {
        String fileName = fileService.uploadImage(path, image);
        PostDto post = postService.getPost(id);
        post.setImageUrl(fileName);
        PostDto updatedPost = postService.updatePost(post, id);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping(value = "api/post/image/{imageName}")
    public void serveImage(@PathVariable String imageName, HttpServletResponse httpServletResponse) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        String imageType = URLConnection.guessContentTypeFromName(imageName);
        if (imageType == null){
            imageType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        httpServletResponse.setContentType(imageType);
        StreamUtils.copy(resource, httpServletResponse.getOutputStream());
    }

}
