package online_game.dataset;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class User {
    private long id;
    private String login;
    private String password;
    private String token;
}
