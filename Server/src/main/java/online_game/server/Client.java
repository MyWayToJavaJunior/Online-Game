package online_game.server;

import online_game.dao.CoordinateDAO;
import online_game.dao.UserDAO;
import online_game.dataset.Board;
import online_game.dataset.Coordinate;
import online_game.dataset.HeroState;
import online_game.dataset.Message;
import online_game.dataset.User;

import java.io.IOException;
import java.util.Set;

public abstract class Client {
    private HeroState heroState;

    public abstract void send(Board board) throws IOException;

    public HeroState getHeroState() {
        return heroState;
    }

    public void handle(Message msg, Set<Client> clients) {
        if (msg instanceof User) {
            User user = UserDAO.getByToken(((User) msg).getPassToken()).orElseThrow(() -> new RuntimeException(""));

            heroState = user.getHeroState();
            clients.add(this);
        } else if (msg instanceof Coordinate) {
            heroState.setCoordinate((Coordinate) msg);
            CoordinateDAO.update(heroState.getCoordinate());
        }
    }
}
