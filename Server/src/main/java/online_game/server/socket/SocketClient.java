package online_game.server.socket;

import com.google.gson.Gson;
import online_game.base.Coordinate;
import online_game.server.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient implements Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private static final String MESSAGES_SEPARATOR = "\n\n";

    private final SocketChannel channel;
    private Coordinate coordinate = new Coordinate(0, 0);

    public SocketClient(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void send(Coordinate[] coords) throws IOException {
        String json = Coordinate.toJson(coords) + MESSAGES_SEPARATOR;
        ByteBuffer buffer = ByteBuffer.wrap(json.getBytes());

        logger.info("Send message " + json + ", to (" + channel.getRemoteAddress() + ")");

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }
}
