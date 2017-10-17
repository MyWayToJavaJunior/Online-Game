package online_game.server.websocket.endpoint;

import online_game.dataset.Message;
import online_game.server.Client;
import online_game.server.websocket.WebSocketClient;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
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

    @OnOpen
    public void connect(Session session) {
        WebSocketClient client = new WebSocketClient(session);
        wsClients.put(session, client);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        Message msg = Message.fromJson(message);
        wsClients.get(session).handle(msg, clients);
    }

    @OnClose
    public void close(Session session) {
        Client client = wsClients.get(session);
        if (client == null) return;

        clients.remove(client);
    }
}
