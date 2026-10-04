package test.security;

import org.junit.jupiter.api.Test;
import security.Role;
import security.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testAddAndCheckRole() {
        User user = new User("testUser", "pass123");

        assertFalse(user.hasRole(Role.ADMIN));

        user.addRole(Role.ADMIN);

        assertTrue(user.hasRole(Role.ADMIN));
    }

    @Test
    void testUsernameAndPassword() {
        User user = new User("alice", "secret");

        assertEquals("alice", user.getUsername());
        assertEquals("secret", user.getPassword());
    }
}
