package online_game.server.socket;

import online_game.dataset.Message;
import online_game.server.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer {
    private static final Logger logger = Logger.getLogger(SocketServer.class.getName());

    private static final int PORT = 8080;
    private static final int CAPACITY = 100;

    private final Map<SocketChannel, Client> channelClients = new HashMap<>();
    private final Set<Client> clients;

    private StringBuilder readBuilder = new StringBuilder();

    public SocketServer(Set<Client> clients) {
        this.clients = clients;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {
        Executors.newSingleThreadExecutor().submit(this::run);
    }

    private Object run() throws Exception {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress("localhost", PORT));

            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);

            logger.info("Started on port: " + PORT);

            listen(selector, serverSocketChannel);
        }
        return null;
    }

    private void listen(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                try {

                    if (key.isAcceptable())
                        accept(selector, serverChannel);
                    else if (key.isReadable())
                        read(key);

                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage());
                } finally {
                    iterator.remove();
                }
            }
        }
    }

    private void accept(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel channel = serverChannel.accept();
        String remoteAddress = channel.getRemoteAddress().toString();
        logger.info("Connection Accepted: " + remoteAddress);

        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);

        Client client = new SocketClient(channel);
        channelClients.put(channel, client);
    }

    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);

        int read = channel.read(buffer);
        if (read != -1) {
            String part = new String(buffer.array()).trim();
            readBuilder.append(part);

            if (part.length() != read) {
                readCoord(channel);
            }
        } else {
            key.cancel();
            Client client = channelClients.get(channel);
            if (client == null) return;

            clients.remove(client);
            logger.info("Connection closed, key canceled");
        }
    }

    private void readCoord(SocketChannel channel) throws ClassNotFoundException, IOException {
        Message msg = Message.fromJson(readBuilder.toString());
        readBuilder = new StringBuilder();

        Client client = channelClients.get(channel);
        client.handle(msg, clients);
    }
}
