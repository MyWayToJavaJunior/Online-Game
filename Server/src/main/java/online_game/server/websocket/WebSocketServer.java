package online_game.server.websocket;

import online_game.server.Client;
import online_game.server.websocket.endpoint.RootConfigurator;
import online_game.server.websocket.endpoint.RootWS;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

public class WebSocketServer {
    private static final int PORT = 7070;

    private final Set<Client> clients;

    public WebSocketServer(Set<Client> clients) {
        this.clients = clients;
    }

    public void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        Server server = new Server(PORT);
        server.setHandler(context);

        ServerContainer wsContainer = WebSocketServerContainerInitializer.configureContext(context);
        wsContainer.addEndpoint(ServerEndpointConfig.Builder
                .create(RootWS.class, "/")
                .configurator(new RootConfigurator(this.clients))
                .build());

        server.start();
        server.join();
    }
}
