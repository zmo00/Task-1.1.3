package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Stack;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE `" + Util.DATABASE_NAME + "`.`"+ Util.TABLE_NAME + "` ("
                            + "  `id` BIGINT(21) NOT NULL AUTO_INCREMENT,"
                            + "  `name` VARCHAR(45) NOT NULL,"
                            + "  `lastName` VARCHAR(45) NOT NULL,"
                            + "  `age` TINYINT(3) NOT NULL,"
                            + "PRIMARY KEY (`id`));"
            );
            System.out.println("Таблица создана");
        } catch (SQLException sqlException) {
            System.out.println("Таблица уже существовала");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE " + Util.TABLE_NAME);
            System.out.println("Таблица удалена");
        } catch (SQLException sqlException) {
            System.out.println("Таблица не существовала");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(
                    "INSERT INTO " + Util.TABLE_NAME + "(name, lastName, age)"
                    + "VALUES ('" + name + "', '" + lastName + "', " + age + ");"
            );
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException sqlException) {
            System.out.println("Не удалось внести user'а в таблицу");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM " + Util.TABLE_NAME + "WHERE id = " + id + ";");
            System.out.println("Удаление user'а из таблицы завершилось успешно");
        } catch (SQLException sqlException) {
            System.out.println("Не удалось удалить user'а из таблицы");
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            List<User> userList = new Stack<>();
            User user;

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Util.TABLE_NAME);

            while (resultSet.next()) {
                user = new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4)
                );
                user.setId(resultSet.getLong(1));
                userList.add(user);
            }

            System.out.println("Получение списка user'ов из таблицы завершилось успешно");
            return userList;
        } catch (SQLException sqlException) {
            System.out.println("Не удалось получить список user'ов из таблицы");
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM " + Util.TABLE_NAME + ";");
            System.out.println("Таблица очищена");
        } catch (SQLException sqlException) {
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
