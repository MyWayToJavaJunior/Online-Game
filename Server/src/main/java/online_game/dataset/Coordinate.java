package online_game.dataset;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Coordinate extends Message {
    private long id;
    private int x;
    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
