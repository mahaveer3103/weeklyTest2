package com.example.postAPI.dao;

import com.example.postAPI.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post,Integer> {
}
