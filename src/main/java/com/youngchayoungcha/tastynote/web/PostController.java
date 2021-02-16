package com.youngchayoungcha.tastynote.web;

import com.youngchayoungcha.tastynote.web.dto.PostCreateDTO;
import com.youngchayoungcha.tastynote.web.dto.PostResponseDTO;
import com.youngchayoungcha.tastynote.service.PostService;
import com.youngchayoungcha.tastynote.web.dto.PostModifyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable(value = "postId") Long postId){
        return new ResponseEntity<>(postService.findPost(postId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> recordPost(@RequestBody PostCreateDTO postDTO) throws IOException {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable(value = "postId") Long postId, @RequestBody PostModifyDTO postDTO) throws IOException{
        return new ResponseEntity<>(postService.modifyPost(postDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deletePost(Long postId){
        postService.deletePost(postId);
        return new ResponseEntity(true, HttpStatus.OK);
    }

}
