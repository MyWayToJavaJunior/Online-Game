package online_game.server;

import online_game.base.Coordinate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private static final int MARGIN = 10;
    private static final int FPS = 10;

    private final Set<Client> clients;

    public Server(Set<Client> clients) {
        this.clients = clients;
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(this::send);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private Object send() throws InterruptedException, IOException {
        while (true) {
            for (Client client : clients) {
                client.send(getNearestClient(client, client.getCoordinate()));
            }
            Thread.sleep(1000 / FPS);
        }
    }

    private Coordinate[] getNearestClient(Client self, Coordinate coordinate) {
        List<Coordinate> result = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();

        result.add(new Coordinate(x, y, true));

        for (Client client : clients) {
            if (self == client) continue;

            Coordinate coord = client.getCoordinate();
            if (coord.getX() > x-MARGIN && coord.getX() < x+MARGIN && coord.getY() > y-MARGIN && coord.getY() < y+MARGIN)
                result.add(coord);
        }

        return result.toArray(new Coordinate[result.size()]);
    }
}
