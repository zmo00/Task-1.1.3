package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        System.out.println();

        User[] users = new User[] {
                new User("Vova", "Zhurikov", (byte) 29),
                new User("Mike", "Zhurikov", (byte) 21),
                new User("Grisha", "Zhurikov", (byte) 18),
                new User("Fedja", "Zhurikov", (byte) 14),
        };
        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        System.out.println();

        List<User> userList = userService.getAllUsers();
        if (userList != null) {
            for (User user : userList) {
                System.out.println(user.toString());
            }
        }
        System.out.println();

        userService.cleanUsersTable();
        System.out.println();

        userService.dropUsersTable();
    }
}
