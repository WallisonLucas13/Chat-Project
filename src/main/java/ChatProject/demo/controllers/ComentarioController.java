package ChatProject.demo.controllers;

import ChatProject.demo.models.Comentario;
import ChatProject.demo.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
@CrossOrigin("*")
public class ComentarioController {

    @Autowired
    private ComentarioService service;

    @PostMapping("/post/{id}")
    public ResponseEntity<String> postar(@PathVariable("id") Long id, @RequestBody Comentario comentario){

        try {
            service.comentarInPost(id, comentario.getMensagem());
            return ResponseEntity.status(HttpStatus.OK).build();

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciais Inválidas!");
        }
    }

    @PostMapping("/comentario/{id}")
    public ResponseEntity<String> comentar(@PathVariable("id") Long id, @RequestBody Comentario comentario){

        try {
            service.comentarInComentario(id, comentario.getMensagem());
            return ResponseEntity.status(HttpStatus.OK).build();

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciais Inválidas!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComentario(@PathVariable("id") Long id){
        try{
            service.deletarComentario(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não foi você que comentou isso!");
        }
    }

    @GetMapping("/AllOf/{id}")
    public ResponseEntity<List<Comentario>> getComments(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCommentsOfComment(id));
    }
}
