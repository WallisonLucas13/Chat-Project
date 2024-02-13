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
@Setter
@Getter
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

}
