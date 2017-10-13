package online_game.server.websocket;

import online_game.base.Board;
import online_game.base.MessageHelper;
import online_game.server.Client;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/")
public class WebSocketClient extends Client {
    private static final Logger logger = Logger.getLogger(WebSocketClient.class.getName());

    private final Session session;

    public WebSocketClient(Session session) {
        this.session = session;
    }

    @Override
    public void send(Board board) throws IOException {
        session.getBasicRemote().sendText(MessageHelper.toJson(board));
    }
}
