package online_game.server;

import online_game.dataset.Board;
import online_game.dataset.Coordinate;

import java.io.IOException;
import java.util.ArrayList;
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
                client.send(getBoardForClient(client));
            }
            Thread.sleep(1000 / FPS);
        }
    }

    private Board getBoardForClient(Client self) {
        Board board = new Board(self.getHeroState(), new ArrayList<>());

        int x = self.getHeroState().getCoordinate().getX();
        int y = self.getHeroState().getCoordinate().getY();

        for (Client client : clients) {
            if (self == client) continue;

            Coordinate coordinate = client.getHeroState().getCoordinate();
            if (
                    coordinate.getX() > x-MARGIN &&
                    coordinate.getX() < x+MARGIN &&
                    coordinate.getY() > y-MARGIN &&
                    coordinate.getY() < y+MARGIN
                )
                board.getOtherPlayers().add(client.getHeroState());
        }

        return board;
    }
}
