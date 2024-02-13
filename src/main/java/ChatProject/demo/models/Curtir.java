package ChatProject.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Curtir {


    public Curtir(UserModel userModel) {
        this.userModel = userModel;
        this.autor = userModel.getUsername();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private UserModel userModel;

    private String autor;
}
