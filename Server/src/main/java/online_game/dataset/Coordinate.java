package online_game.dataset;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
