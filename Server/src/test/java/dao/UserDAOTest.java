package dao;

import online_game.dao.UserDAO;
import online_game.dataset.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {
    @Test
    public void get() {
        User actual = UserDAO.get("user1").get();
        User expected = new User("user1", "pass1", "pass_token_1", "X", null);

        equalsUser(actual, expected);
    }

    @Test
    public void getByToken() {
        User actual = UserDAO.getByToken("pass_token_1").get();
    }

    private void equalsUser(User actual, User expected) {
        assertEquals(actual.getLogin(), expected.getLogin());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getPassToken(), expected.getPassToken());
        assertEquals(actual.getHeroToken(), expected.getHeroToken());
    }
}
