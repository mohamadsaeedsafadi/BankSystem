package test.security;

import org.junit.jupiter.api.Test;
import security.AuthenticatedUser;
import security.JwtUtil;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void testGenerateAndValidateToken() {
        AuthenticatedUser user = new AuthenticatedUser("user1", "ADMIN");

        String token = JwtUtil.generateToken(user);
        assertNotNull(token);

        AuthenticatedUser decoded = JwtUtil.validateToken(token);

        assertEquals("user1", decoded.getUserId());
        assertEquals("ADMIN", decoded.getRole());
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        AuthenticatedUser user = new AuthenticatedUser("user2", "CUSTOMER");
        String token = JwtUtil.generateToken(user);

        AuthenticatedUser decoded = JwtUtil.validateToken(token);
        assertEquals("user2", decoded.getUserId());
    }
}
