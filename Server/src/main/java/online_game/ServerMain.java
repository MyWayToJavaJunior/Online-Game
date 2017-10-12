package online_game;

import online_game.server.Client;
import online_game.server.Server;
import online_game.server.socket.SocketServer;
import online_game.server.websocket.WebSocketServer;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.Set;

public class ServerMain {

    public static void main(String[] args) throws Exception {
        Set<Client> clients = new ConcurrentHashSet<>();

        new Server(clients).start();
        new SocketServer(clients).start();
        new WebSocketServer(clients).start();
    }
}
