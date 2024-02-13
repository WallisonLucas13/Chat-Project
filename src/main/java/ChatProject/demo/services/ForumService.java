package ChatProject.demo.services;

import ChatProject.demo.models.Forum;
import ChatProject.demo.models.Post;
import ChatProject.demo.repositories.ForumRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumRepository repository;

    @Autowired
    private PostService postService;

    @Transactional
    public void postar(Post post){
        post.setCurtirs(List.of());
        repository.save(addPostInForum(post, getForum()));
    }

    @Transactional
    public void deletarPost(Long id) throws RuntimeException{

        String loggedUser = userService.extractLoggedUser();
        Post post = postService.getById(id);

        System.out.println("Usuário logado: " + loggedUser + " | " + post.getAutor());

        if(!loggedUser.equals(post.getAutor())){
            throw new RuntimeException();
        }

        postService.deleteById(id);
    }

    @Transactional
    public Forum getForum(){
        return forum();
    }

    @Transactional
    private Forum addPostInForum(Post post, Forum forum){
        List<Post> posts = forum.getPosts();
        posts.add(post);
        forum.setPosts(posts);
        return forum;
    }

    private Forum forum(){

        if(repository.findById(1l).isEmpty()){
            Forum forum = new Forum();
            forum.setPosts(List.of());
            repository.save(forum);
            return forum;
        }
        return repository.findById(1l).get();
    }

}
