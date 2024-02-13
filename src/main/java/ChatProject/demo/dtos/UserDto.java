package ChatProject.demo.dtos;

import ChatProject.demo.models.UserModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String email;

    private String fotoPerfil;

    public UserModel toUser(){
        return new UserModel(username, password, fotoPerfil, email);
    }
}
