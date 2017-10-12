package online_game;

import online_game.base.Coordinate;
import online_game.console_client.SocketClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        SocketClient client = new SocketClient();
        client.start(new Coordinate(0, 0));

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            client.move(in.readLine().trim());
        }
    }
}
