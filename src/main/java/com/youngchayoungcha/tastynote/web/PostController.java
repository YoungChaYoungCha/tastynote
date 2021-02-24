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
import java.util.List;

@RestController
@RequestMapping(value = "posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable(value = "postId") Long postId){
        return new ResponseEntity<>(postService.findPost(postId), HttpStatus.OK);
    }

    // Page 5 씩 index
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getPostList(@RequestParam(value = "page")int page, @RequestParam(value = "length", defaultValue = "5")int size) {
        // TODO Filter Type -> 최신순, 거리순,
        return new ResponseEntity<>(postService.getPostList(page, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@ModelAttribute PostCreateDTO post) throws IOException {
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.OK);
    }

    //TODO 게시물 주인만 post를 modify할 수 있도록 수정
    @PutMapping(value = "/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable(value = "postId") Long postId, @ModelAttribute PostModifyDTO post) throws IOException{
        return new ResponseEntity<>(postService.modifyPost(post), HttpStatus.OK);
    }

    //TODO 게시물 주인만 post를 삭제할 수 있도록 수정
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity deletePost(@PathVariable(value = "postId") Long postId){
        postService.deletePost(postId);
        return new ResponseEntity(true, HttpStatus.OK);
    }

}
