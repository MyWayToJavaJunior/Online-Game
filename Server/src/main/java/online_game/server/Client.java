package online_game.server;

import online_game.base.Board;
import online_game.dataset.HeroState;

import java.io.IOException;

public abstract class Client {
    private HeroState heroState;

    public abstract void send(Board board) throws IOException;

    public HeroState getHeroState() {
        return heroState;
    }

    public void setHeroState(HeroState heroState) {
        this.heroState = heroState;
    }
}
