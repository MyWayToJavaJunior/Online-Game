package online_game.server;

import online_game.base.Coordinate;

import java.io.IOException;

public interface Client {
    void send(Coordinate[] coords) throws IOException;
    void setCoordinate(Coordinate coordinate);
    Coordinate getCoordinate();
}
