package online_game.dataset;

import lombok.Data;

@Data
public class HeroState {
    private Coordinate coordinate;
    private String token;

    public HeroState(Coordinate coordinate, String token) {
        this.coordinate = coordinate;
        this.token = token;
    }
}
