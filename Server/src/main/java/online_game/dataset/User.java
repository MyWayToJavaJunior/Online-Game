package online_game.dataset;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class User extends Message {
    private long id;
    private String login;
    private String password;
    private String passToken;
    private String heroToken;
    private HeroState heroState;

    public User() {
    }

    public User(String login, String password, String passToken, String heroToken, HeroState heroState) {
        this.login = login;
        this.password = password;
        this.passToken = passToken;
        this.heroToken = heroToken;
        this.heroState = heroState;
    }
}
