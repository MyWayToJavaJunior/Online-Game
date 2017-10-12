package online_game.server.websocket.endpoint;

import online_game.base.Coordinate;
import online_game.server.Client;
import online_game.server.websocket.WebSocketClient;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ServerEndpoint(value = "/")
public class RootWS {
    private final Map<Session, WebSocketClient> wsClients = new HashMap<>();
    private final Set<Client> clients;

    public RootWS(Set<Client> wsClients) {
        this.clients = wsClients;
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        WebSocketClient client = wsClients.get(session);
        if (client == null) {
            client = new WebSocketClient(session);
            clients.add(client);
            wsClients.put(session, client);
        }

        client.setCoordinate(Coordinate.fromJson(message));
    }

    @OnClose
    public void handleClose(Session session) {
        Client client = wsClients.get(session);
        if (client == null) return;

        clients.remove(client);
    }
}
