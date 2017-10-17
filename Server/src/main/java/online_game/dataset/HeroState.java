package online_game.dataset;

import lombok.Data;

@Data
public class HeroState extends Message {
    private Coordinate coordinate;
    private int health;
    private User user;

    public HeroState() {
    }

    public HeroState(Coordinate coordinate, User user) {
        this.coordinate = coordinate;
        this.user = user;
    }


}
