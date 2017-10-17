package online_game.server.socket;

import online_game.dataset.Board;
import online_game.dataset.Message;
import online_game.server.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class SocketClient extends Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private static final String MESSAGES_SEPARATOR = "\n\n";

    private final SocketChannel channel;

    public SocketClient(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void send(Board board) throws IOException {
        String json = Message.toJson(board) + MESSAGES_SEPARATOR;
        ByteBuffer buffer = ByteBuffer.wrap(json.getBytes());

        logger.info("Send message " + json + ", to (" + channel.getRemoteAddress() + ")");

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }
}
