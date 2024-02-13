package ChatProject.demo.services;

import ChatProject.demo.models.Curtir;
import ChatProject.demo.models.Post;
import ChatProject.demo.models.UserModel;
import ChatProject.demo.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserService userService;

    @Transactional
    public Post getById(Long id){
        return repository.findById(id).get();
    }

    @Transactional
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    @Transactional
    public void save(Post post){
        repository.save(post);
    }

    @Transactional
    public Post createPost(Post post) throws UsernameNotFoundException {
        UserModel userModel = userService.getByUsername(userService.extractLoggedUser());

        return new Post(userModel.getUsername(), post.getTitulo(), post.getMensagem(), post.getFoto(), userModel);
    }

    @Transactional
    public List<Post> myPosts(){
        String username = userService.extractLoggedUser();
        return repository.findAllByAutor(username);
    }

    @Transactional
    public Post getPostById(Long id){
        return repository.findById(id).get();
    }

    @Transactional
    public String curtirPost(String username, Long idPost){

        UserModel userModel = userService.getByUsername(username);

        Post post = repository.findById(idPost).get();

        if(post.getCurtirs().stream().filter(curtir -> curtir.getUserModel().getUsername().equals(username))
                .count() > 0){

            repository.save(removeLikeInPost(username, post));
            return "removeu";
        }

        repository.save(addLikeInPost(new Curtir(userModel), post));

        return "curtiu";
    }

    private Post addLikeInPost(Curtir curtir, Post post) {

        List<Curtir> curtirs = post.getCurtirs();
        curtirs.add(curtir);
        post.setCurtirs(curtirs);
        return post;
    }

    private Post removeLikeInPost(String username, Post post){

        List<Curtir> curtirs = post.getCurtirs()
                .stream().filter(curtir -> !Objects.equals(curtir.getUserModel().getUsername(), username))
                .collect(Collectors.toList());

        post.setCurtirs(curtirs);
        return post;
    }
}
