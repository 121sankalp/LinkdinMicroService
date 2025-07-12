package com.example.postService.repositories;

import com.example.postService.entities.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    // ✅ Fixed casing
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}