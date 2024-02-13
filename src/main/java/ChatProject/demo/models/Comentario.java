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
public class Comentario {

    public Comentario(String username, String msg, UserModel userModel){
        this.autor = username;
        this.mensagem = msg;
        this.data = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        this.hora = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        this.perfilUser = userModel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 5)
    private Long id;

    @Column(nullable = false)
    @JsonProperty(index = 1)
    private String autor;

    @Column(nullable = false)
    @JsonProperty(index = 2)
    private String mensagem;

    @JsonProperty(index = 3)
    private String data;

    @JsonProperty(index = 4)
    private String hora;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty(index = 6)
    private List<Comentario> comentarios;

    @ManyToOne
    private UserModel perfilUser;

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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public UserModel getPerfilUser() {
        return perfilUser;
    }

    public void setPerfilUser(UserModel perfilUser) {
        this.perfilUser = perfilUser;
    }
}
