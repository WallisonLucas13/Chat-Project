package ChatProject.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Entity
@NoArgsConstructor
public class Post {

    public Post(String username, String titulo,String msg, String foto , UserModel perfilUser){
        this.autor = username;
        this.perfilUser = perfilUser;
        this.titulo = titulo;
        this.foto = foto;
        this.mensagem = msg;
        this.data = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        this.hora = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 5)
    private Long id;

    @Column(nullable = false)
    @JsonProperty(index = 1)
    private String autor;

    private String titulo;

    @Column(nullable = false)
    @JsonProperty(index = 2)
    private String mensagem;

    @JsonProperty(index = 3)
    private String data;

    @JsonProperty(index = 4)
    private String hora;

    @ManyToOne
    private UserModel perfilUser;

    @Column(length = 1000000)
    private String foto;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty(index = 6)
    private List<Comentario> comentarios;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Curtir> curtirs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public UserModel getPerfilUser() {
        return perfilUser;
    }

    public void setPerfilUser(UserModel perfilUser) {
        this.perfilUser = perfilUser;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Curtir> getCurtirs() {
        return curtirs;
    }

    public void setCurtirs(List<Curtir> curtirs) {
        this.curtirs = curtirs;
    }
}
