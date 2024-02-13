package ChatProject.demo.controllers;

import ChatProject.demo.models.Forum;
import ChatProject.demo.models.Post;
import ChatProject.demo.services.ForumService;
import ChatProject.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
@CrossOrigin("*")
public class ForumController {

    @Autowired
    private ForumService service;

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<String> postar(@RequestBody Post post){

        try {
            service.postar(postService.createPost(post));
            return ResponseEntity.status(HttpStatus.OK).build();

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciais Inválidas!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        try{
            service.deletarPost(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Este Post não pertence a você!");
        }
    }

    @GetMapping("/myPosts")
    public ResponseEntity<List<Post>> myPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.myPosts());
    }

    @GetMapping("/")
    public ResponseEntity<Forum> getChat(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getForum());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @PutMapping("/like/{username}/{id}")
    public ResponseEntity<String> curtir(@PathVariable("username") String username, @PathVariable("id") Long id){
        String res = postService.curtirPost(username, id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
