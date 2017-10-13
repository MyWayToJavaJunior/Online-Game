package online_game.base;

import lombok.Data;
import online_game.dataset.HeroState;

import java.util.List;

@Data
public class Board {
    private final HeroState me;
    private final List<HeroState> otherPlayers;

    public Board(HeroState me, List<HeroState> otherPlayers) {
        this.me = me;
        this.otherPlayers = otherPlayers;
    }
}
