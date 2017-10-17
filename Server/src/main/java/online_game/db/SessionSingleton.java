package online_game.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class SessionSingleton {
    private static SqlSessionFactory factory;

    public static SqlSession getSession() {
        if (factory == null)
            try {
                factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis/config.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        return factory.openSession(false);
    }
}
