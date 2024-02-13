package ChatProject.demo.services;

import ChatProject.demo.configs.security.jwt.JwtService;
import ChatProject.demo.enums.RoleName;
import ChatProject.demo.models.AuthenticationResponse;
import ChatProject.demo.models.PerfilUser;
import ChatProject.demo.models.RoleModel;
import ChatProject.demo.models.UserModel;
import ChatProject.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository repository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(UserModel model) throws RuntimeException{

        boolean exist = repository.existsByUsername(model.getUsername());

        if(exist){
            throw new RuntimeException();
        }

        model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
        model.setRoles(List.of(createRoleUser()));

        repository.save(model);

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(model))
                .build();
    }

    @Transactional
    public AuthenticationResponse login(UserModel model) throws UsernameNotFoundException{

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        model.getUsername(),
                        model.getPassword()
                )
        );

        UserDetails userDetails = repository.findByUsername(model.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(""));

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(userDetails))
                .build();
    }

    @Transactional
    public UserModel getByUsername(String username) throws UsernameNotFoundException{
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário Inexistente!"));

    }

    @Transactional
    public String extractLoggedUser() throws UsernameNotFoundException{
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean exist = repository.existsByUsername(username);

        if(!exist){
            throw new UsernameNotFoundException("");
        }
        return username;
    }

    @Transactional
    public void deleteByUsername(String username) throws RuntimeException{
        String logged = extractLoggedUser();
        UserModel userModel = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));

        if(!logged.equals(username)){
            throw new RuntimeException();
        }

        repository.deleteById(userModel.getId());
    }

    public PerfilUser getPerfilUser(){
        UserModel userModel = repository.findByUsername(extractLoggedUser()).get();

        return new PerfilUser(userModel.getUsername(),
                userModel.getSobrenome(),
                userModel.getEmail(),
                userModel.getCidade(),
                userModel.getEstado(),
                userModel.getFotoPerfil());

    }

    public PerfilUser getPerfilUserByUsername(String username){
        UserModel userModel = repository.findByUsername(username).get();

        return new PerfilUser(userModel.getUsername(),
                userModel.getSobrenome(),
                userModel.getEmail(),
                userModel.getCidade(),
                userModel.getEstado(),
                userModel.getFotoPerfil());
    }

    @Transactional
    public void atualizarPerfilUsuario(PerfilUser perfilUser){
        UserModel userModel = repository.findByUsername(extractLoggedUser()).get();
        repository.save(perfilUser.toUserModel(userModel.getUsername(), userModel.getPassword(), userModel.getId()));
    }

    /*
    @Transactional
    public List<PerfilUser> filterUserByUsername(String username){

        String[] nome = username.split(" ");
        String sobrenome = username.replace(nome[0]+" ", "");

        List<UserModel> userModels = repository.findAll();


        List<PerfilUser> usersIncomplete = userModels.stream()
                    .filter(model -> model.getUsername().contains(username))
                    .map(model -> { return new PerfilUser(
                            model.getUsername(),
                            model.getSobrenome(),
                            model.getEmail(),
                            model.getCidade(),
                            model.getEstado(),
                            model.getFotoPerfil()
                    );
                    }).collect(Collectors.toList());

        List<PerfilUser> usersComplete = userModels.stream()
                .filter(model -> model.getUsername().contains(nome[0]))
                .filter(model -> model.getSobrenome().contains(sobrenome))
                .map(model -> { return new PerfilUser(
                        model.getUsername(),
                        model.getSobrenome(),
                        model.getEmail(),
                        model.getCidade(),
                        model.getEstado(),
                        model.getFotoPerfil()
                        );
                }).collect(Collectors.toList());

        if(usersComplete.isEmpty()){
            return usersIncomplete;
        }

        return usersComplete;
    }

     */
    private RoleModel createRoleUser(){
        RoleModel roleModel = new RoleModel();
        roleModel.setRoleName(RoleName.ROLE_USER);
        return roleModel;
    }

}
