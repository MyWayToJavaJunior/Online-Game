package online_game.console_client;

import online_game.base.Coordinate;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient {
    private static final Logger logger = Logger.getLogger(ClientSocketHandler.class.getName());

    private static final String HOST = "localhost";
    private static final int FPS = 1000/60;
    private static final int FIELD_SIZE = 21;

    private ClientSocketHandler client;
    private Coordinate cur;

    public void start(Coordinate initialCoordinate) throws IOException {
        client = new ClientSocketHandler(new Socket(HOST, 8080));
        client.send(initialCoordinate);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                while (true) {
                    Coordinate[] coordinates = client.take();
                    outputMap(coordinates);

                    Thread.sleep(FPS);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    public void move(String s) {
        switch (s) {
            case "w":
                moveTo(new Coordinate(cur.getX(), cur.getY()-1));
                break;
            case "d":
                moveTo(new Coordinate(cur.getX()+1, cur.getY()));
                break;
            case "s":
                moveTo(new Coordinate(cur.getX(), cur.getY()+1));
                break;
            case "a":
                moveTo(new Coordinate(cur.getX()-1, cur.getY()));
                break;
            default:
                break;
        }
    }

    public void moveTo(Coordinate coordinate) {
        client.send(coordinate);
    }

    private void outputMap(Coordinate[] coordinates) {
        Coordinate me = new Coordinate(0, 0);
        for (Coordinate coordinate : coordinates) if(coordinate.isMe()) me = coordinate;
        cur = me;

        boolean[][] field = new boolean[FIELD_SIZE][FIELD_SIZE];
        int bias = FIELD_SIZE/2;
        int x = me.getX();
        int y = me.getY();

        for (Coordinate coordinate : coordinates)
            field[bias+coordinate.getY()-y][bias+coordinate.getX()-x] = true;

        for (boolean[] i : field) {
            for (boolean j : i) {
                String s = j ? "# " : "- ";
                System.out.print(s);
            }
            System.out.println();
        }
        System.out.println();
    }
}
