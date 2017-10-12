package online_game.base;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class Coordinate {
    private final int x;
    private final int y;
    private final boolean me;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.me = false;
    }

    public Coordinate(int x, int y, boolean me) {
        this.x = x;
        this.y = y;
        this.me = me;
    }

    public static String toJson(Coordinate coordinate) {
        return new Gson().toJson(coordinate);
    }

    public static String toJson(Coordinate[] coordinates) {
        return new Gson().toJson(coordinates);
    }

    public static Coordinate fromJson(String json) {
        return new Gson().fromJson(json, Coordinate.class);
    }
}
