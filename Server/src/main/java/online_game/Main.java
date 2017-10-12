package online_game;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) throw new RuntimeException("Input mode");
        switch (args[0]) {
            case "server":
                ServerMain.main(args);
                break;
            case "console_client":
                ClientMain.main(args);
                break;
            default:
                throw new RuntimeException("Wrong mode");
        }
    }
}
