package ChatProject.demo.models;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class PerfilUser {

    private String nome;
    private String sobrenome;

    private String email;

    private String cidade;

    private String estado;

    private String fotoPerfil;

    public PerfilUser(){}

    public PerfilUser(String username, String sobrenome ,String email, String cidade, String estado, String fotoPerfil) {
        this.nome = username;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.fotoPerfil = fotoPerfil;
    }

    public UserModel toUserModel(String username, String password, Long id){
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setId(id);
        user.setPassword(password);
        user.setEmail(this.email);
        user.setCidade(this.cidade);
        user.setEstado(this.estado);
        user.setFotoPerfil(this.fotoPerfil);
        user.setSobrenome(this.sobrenome);
        return user;
    }
}
