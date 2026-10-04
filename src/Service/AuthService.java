package Service;

import security.Role;
import security.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static AuthService instance = null;

    private Map<String, User> users = new HashMap<>();

    private AuthService() {}

    // Singleton getInstance()
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    // Register user manually (used in Main)
    public void registerUser(String username, String password, Role... roles) {
        User user = new User(username, password);
        for (Role r : roles) user.addRole(r);
        users.put(username, user);
    }

    // Login OR auto-register for new users
    public User login(String username, String password) {

        // يوجد مسبقاً
        if (users.containsKey(username)) {
            User u = users.get(username);

            if (u.getPassword().equals(password)) {
                return u;
            }

            System.out.println("Wrong password!");
            return null;
        }

        // حساب جديد → نسجّله تلقائياً
        User newUser = new User(username, password);
        newUser.addRole(Role.CUSTOMER);
        users.put(username, newUser);

        System.out.println("New user created automatically: " + username);

        return newUser;
    }

    // Check role
    public boolean hasRole(User u, Role role) {
        return u.getRoles().contains(role);
    }
}
