package online_game.server.websocket.endpoint;

import online_game.server.Client;

import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;
import java.util.logging.Logger;

public class RootConfigurator extends ServerEndpointConfig.Configurator {
    private final Set<Client> clients;

    public RootConfigurator(Set<Client> clients) {
        this.clients = clients;
    }

    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return (T) new RootWS(clients);
    }
}
