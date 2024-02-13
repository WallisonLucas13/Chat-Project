package ChatProject.demo.models;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

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
        UserModel user = new UserModel(username, password, this.fotoPerfil, this.email);
        user.setId(id);
        user.setCidade(this.cidade);
        user.setEstado(this.estado);
        user.setSobrenome(this.sobrenome);
        return user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
