package ChatProject.demo.services;

import ChatProject.demo.models.Comentario;
import ChatProject.demo.models.Post;
import ChatProject.demo.models.UserModel;
import ChatProject.demo.repositories.ComentarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    public void comentarInPost(Long idPost, String message){

        UserModel userModel = userService.getByUsername(userService.extractLoggedUser());
        Comentario comentario = createComentario(message, userModel);

        postService.save(addComentarioInPost(postService.getById(idPost), comentario));
    }

    @Transactional
    public void comentarInComentario(Long idComentario, String message){

        UserModel userModel = userService.getByUsername(userService.extractLoggedUser());
        Comentario comentario = comentarioRepository.findById(idComentario).get();

        Comentario newComentario = createComentario(message, userModel);

        comentarioRepository.save(addComentarioInComentario(comentario, newComentario));
    }

    @Transactional
    public void deletarComentario(Long id) throws RuntimeException{

        String logged = userService.extractLoggedUser();
        Comentario comentario = comentarioRepository.findById(id).get();

        if(!logged.equals(comentario.getAutor())){
            throw new RuntimeException();
        }

        comentarioRepository.deleteById(id);
    }

    private Post addComentarioInPost(Post post, Comentario comentario){
        List<Comentario> comentarios = post.getComentarios();
        comentarios.add(comentario);
        post.setComentarios(comentarios);
        return post;
    }

    private Comentario addComentarioInComentario(Comentario comentario, Comentario newComentario){
        List<Comentario> comentarios = comentario.getComentarios();
        comentarios.add(newComentario);
        comentario.setComentarios(comentarios);
        return comentario;
    }

    private Comentario createComentario(String message, UserModel userModel){
        return new Comentario(userModel.getUsername(), message, userModel);
    }

    @Transactional
    public List<Comentario> getCommentsOfComment(Long id){
        return comentarioRepository.findById(id).get().getComentarios();
    }



}
