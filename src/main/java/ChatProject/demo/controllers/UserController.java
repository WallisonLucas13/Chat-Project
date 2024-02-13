package ChatProject.demo.controllers;

import ChatProject.demo.dtos.UserDto;
import ChatProject.demo.models.AuthenticationResponse;
import ChatProject.demo.models.PerfilUser;
import ChatProject.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserDto dto) {

        try {
            AuthenticationResponse res = service.register(dto.toUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid UserDto dto){

        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.login(dto.toUser()));
        }
        catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> delete(@PathVariable("username") String username){
        try{
            service.deleteByUsername(username);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não pode apagar o seu colega!");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<PerfilUser> getUserByToken(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPerfilUser());
    }
    @GetMapping("/user/ByUsername/{username}")
    public ResponseEntity<PerfilUser> getUserByName(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPerfilUserByUsername(username));
    }

    @PutMapping("/user/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody PerfilUser perfilUser){
        service.atualizarPerfilUsuario(perfilUser);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    /*
    @GetMapping("/user/filter/{username}")
    public ResponseEntity<List<PerfilUser>> filterUser(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(service.filterUserByUsername(username));
    }

     */


}
