package com.example.postAPI.service;

import com.example.postAPI.dao.PostDao;
import com.example.postAPI.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    public int addPost(Post post) {
        return postDao.save(post).getId();
    }

    public List<Post> getPost() {
        return postDao.findAll();
    }
}
