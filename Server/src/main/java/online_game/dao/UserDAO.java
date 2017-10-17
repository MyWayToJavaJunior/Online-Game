package online_game.dao;

import online_game.dataset.User;
import online_game.db.SessionSingleton;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

public class UserDAO {
    public static Optional<User> get(String login) {
        try(SqlSession session = SessionSingleton.getSession()) {
            return Optional.of(session.selectOne("get_user_by_login", login));
        }
    }

    public static Optional<User> getByToken(String token) {
        try(SqlSession session = SessionSingleton.getSession()) {
            return Optional.of(session.selectOne("get_user_by_token", token));
        }
    }
}
