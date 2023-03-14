package com.example.postAPI.controller;

import com.example.postAPI.model.Post;
import com.example.postAPI.service.PostService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/")
public class PostController {

    @Autowired
    PostService service;

    @PostMapping("/post")
    public ResponseEntity<String> addPost(@RequestBody String requestPost){
        Post post = setPost(requestPost);
        int id = service.addPost(post);
        return new ResponseEntity<>("Post saved with id - "+id, HttpStatus.CREATED);
    }

    @GetMapping("/post")
    public ResponseEntity<String> getPost(){
        List<Post> posts = service.getPost();
        return new ResponseEntity<>(posts.toString(),HttpStatus.OK);
    }

    private Post setPost(String requestPost) {
        JSONObject json = new JSONObject(requestPost);
        Post post = new Post();
        post.setTitle(json.getString("title"));
        post.setDescription(json.getString("description"));
        return post;
    }
}
