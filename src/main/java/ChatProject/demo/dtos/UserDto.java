package ChatProject.demo.dtos;

import ChatProject.demo.models.UserModel;
import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank String username, @NotBlank String password,String email, String fotoPerfil){

    public UserModel toUser(){
        return new UserModel(username, password, fotoPerfil, email);
    }
}
