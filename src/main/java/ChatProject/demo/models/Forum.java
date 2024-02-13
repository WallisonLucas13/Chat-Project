package ChatProject.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 2)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty(index = 1)
    private List<Post> posts;
}
