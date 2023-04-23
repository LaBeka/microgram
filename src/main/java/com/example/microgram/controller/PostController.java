package com.example.microgram.controller;

import com.example.microgram.dto.PostDto;
import com.example.microgram.dto.PostDtoShow;
import com.example.microgram.dto.PostFrontDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.Post;
import com.example.microgram.entity.User;
import com.example.microgram.service.PostService;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/post")
public class PostController {
    private PostService postService;
    private UserService userService;

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity createNewPost(@RequestParam("photo") MultipartFile photo,
                                   @RequestParam("description") String description,
                                   @RequestParam("userId") String userId) throws IOException {
        Long userID = 0L;
        try{
            userID = Long.parseLong(userId);
        } catch(Exception e){
            return new ResponseEntity("Not found user", HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userService.findUserById(userID);
        if(user.isEmpty()) {
            return new ResponseEntity("No user found by this id", HttpStatus.BAD_REQUEST);
        }
        Path userImageDirectory = Paths.get("data/images/", userId);
        Files.createDirectories(userImageDirectory);
        Path newFilePath = userImageDirectory.resolve(photo.getOriginalFilename()); //ad this file name
        photo.transferTo(newFilePath);
        PostDto post = new PostDto(photo.getOriginalFilename(), description);
        String validateErrors = post.validatePostData();
        if (!validateErrors.isEmpty()) {
            return new ResponseEntity(validateErrors, HttpStatus.BAD_REQUEST);
        }
        PostFrontDto createdPost = postService.createNewPost(post, user.get());

        ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
//        uriBuilder.path("/post/" + createdPost.getPostId() + "/image"); //it will give post/create/post/{postId}/image
        uriBuilder.replacePath("/post/" + createdPost.getPostId() + "/image");
        createdPost.setPhoto(uriBuilder.build().toString());
        return new ResponseEntity(createdPost, HttpStatus.OK);
    }
    @GetMapping(value = "{postId}/image")
    public ResponseEntity getImageDataFromPost(@PathVariable String postId, HttpServletRequest request) throws MalformedURLException {
        Optional<Post> postById = postService.findPostById(Long.parseLong(postId));
        Path userImageDirectory = Paths.get("data/images/", postById.get().getUserId().toString());
        Path filePath = userImageDirectory.resolve(postById.get().getPhoto());
        Resource resource = new UrlResource(filePath.toUri());

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }
        if (contentType == null) {// Fallback to the default content type if type could not be determined
            contentType = "application/octet-stream";//binary data == octet-stream
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value = "/myPosts")
    public ResponseEntity getMyPosts(Authentication auth){
        User user = (User) auth.getPrincipal();
        List<PostDtoShow> myPosts = postService.getMyPosts(user);
        if(myPosts.size() == 0){
            return new ResponseEntity("No my posts to show", HttpStatus.OK);
        }
        return new ResponseEntity(myPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/othersPosts")
    public ResponseEntity getOthersPosts(Authentication auth){
        User user = (User) auth.getPrincipal();
        List<PostDtoShow> othersPosts = postService.getOthersPosts(user);
        if(othersPosts.size() == 0){
            return new ResponseEntity("No Posts found", HttpStatus.OK);
        }
        return new ResponseEntity(othersPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/followingsPosts")
    public ResponseEntity<List<PostDto>> getPostsBySubscription(Authentication auth){
        User user = (User) auth.getPrincipal();
        List<PostDtoShow> posts = postService.getOthersPostsByFollowings(user);
        if(posts.size() == 0){
            return new ResponseEntity("no posts out there.", HttpStatus.OK
            );
        }
        return new ResponseEntity(posts, HttpStatus.OK);
    }
    @DeleteMapping(value = "/deletePost/{postId}")
    public ResultDto deleteThePost(Authentication auth, @PathVariable Long postId){
        User user = (User) auth.getPrincipal();

        return postService.deleteThePost(postId, user);
    }
    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:63342")

    public ResponseEntity getAllPosts(){
        List<PostFrontDto> allPosts = postService.allPosts();
        if(allPosts.size() == 0){
            return new ResponseEntity("No Posts found", HttpStatus.OK);
        }
        for(PostFrontDto p : allPosts){
            ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
            uriBuilder.replacePath("/post/" + p.getPostId() + "/image");
            p.setPhoto(uriBuilder.build().toString());
        }
        System.out.println(allPosts.toString());
        return new ResponseEntity(allPosts, HttpStatus.OK);
    }
}
