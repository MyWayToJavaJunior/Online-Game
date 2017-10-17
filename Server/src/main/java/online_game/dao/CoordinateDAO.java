package online_game.dao;

import online_game.dataset.Coordinate;
import online_game.db.SessionSingleton;
import org.apache.ibatis.session.SqlSession;

public class CoordinateDAO {
    public static void update(Coordinate coord) {
        try(SqlSession session = SessionSingleton.getSession()) {
            session.update("update_coord", coord);
        }
    }
}
