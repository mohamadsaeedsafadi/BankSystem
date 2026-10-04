package test.security;

import org.junit.jupiter.api.Test;
import security.AuthenticatedUser;
import security.AuthorizationManager;
import security.JwtUtil;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationManagerTest {

    @Test
    void testCheckRoleSuccess() {
        AuthenticatedUser user = new AuthenticatedUser("admin1", "ADMIN");
        String token = JwtUtil.generateToken(user);

        assertDoesNotThrow(() -> AuthorizationManager.checkRole(token, "ADMIN"));
    }

    @Test
    void testCheckRoleFailure() {
        AuthenticatedUser user = new AuthenticatedUser("user1", "CUSTOMER");
        String token = JwtUtil.generateToken(user);

        SecurityException ex = assertThrows(SecurityException.class,
                () -> AuthorizationManager.checkRole(token, "ADMIN"));
        assertTrue(ex.getMessage().contains("Access denied"));
    }
}
