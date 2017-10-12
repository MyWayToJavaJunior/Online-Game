package online_game.console_client;

import com.google.gson.Gson;
import online_game.base.Coordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocketHandler {
    private static final Logger logger = Logger.getLogger(ClientSocketHandler.class.getName());

    private static final int WORKERS_COUNT = 2;

    private final Socket socket;

    private final BlockingQueue<Coordinate> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Coordinate[]> input = new LinkedBlockingQueue<>();

    public ClientSocketHandler(Socket socket) {
        this.socket = socket;

        Executor executor = Executors.newFixedThreadPool(WORKERS_COUNT);
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) {
                    String json = stringBuilder.toString();
                    Coordinate[] coordinates = new Gson().fromJson(json, Coordinate[].class);
                    input.add(coordinates);

                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Coordinate coordinate = output.take();
                String json = new Gson().toJson(coordinate);
                out.println(json);
                out.println();
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void send(Coordinate coordinate) {
        output.add(coordinate);
    }

    public Coordinate[] poll() {
        return  input.poll();
    }

    public Coordinate[] take() throws InterruptedException {
        return input.take();
    }
}
