package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE `" + Util.DATABASE_NAME + "`.`"+ Util.TABLE_NAME + "` ("
                            + "  `id` BIGINT(21) NOT NULL AUTO_INCREMENT,"
                            + "  `name` VARCHAR(45) NOT NULL,"
                            + "  `lastName` VARCHAR(45) NOT NULL,"
                            + "  `age` TINYINT(3) NOT NULL,"
                            + "  PRIMARY KEY (`id`));")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.out.println("Таблица уже существовала");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table " + Util.TABLE_NAME).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.out.println("Таблица не существовала");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Добавлен " + user);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("Удален " + user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<User> userList = (List<User>) session.createQuery("FROM User").list();
            session.getTransaction().commit();
            System.out.println("Список получен");
            return userList;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        }
    }
}
