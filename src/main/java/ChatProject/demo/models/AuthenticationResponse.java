package ChatProject.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class AuthenticationResponseBuilder{
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        public static AuthenticationResponseBuilder builder(){
            return new AuthenticationResponseBuilder();
        }

        public AuthenticationResponseBuilder token(String token){
            this.authenticationResponse.token = token;
            return this;
        }

        public AuthenticationResponse build(){
            return authenticationResponse;
        }
    }
}
