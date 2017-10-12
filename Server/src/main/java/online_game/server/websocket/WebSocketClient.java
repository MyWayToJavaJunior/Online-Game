package online_game.server.websocket;

import online_game.base.Coordinate;
import online_game.server.Client;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/")
public class WebSocketClient implements Client {
    private static final Logger logger = Logger.getLogger(WebSocketClient.class.getName());

    private final Session session;
    private Coordinate coordinate = new Coordinate(0, 0);

    public WebSocketClient(Session session) {
        this.session = session;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public void send(Coordinate[] coords) throws IOException {
        session.getBasicRemote().sendText(Coordinate.toJson(coords));
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }
}
