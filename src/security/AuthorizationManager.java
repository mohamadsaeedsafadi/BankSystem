package security;

public class AuthorizationManager {

    public static void checkRole(
            String token,
            String requiredRole
    ) {
        AuthenticatedUser user =
                JwtUtil.validateToken(token);

        if (!user.getRole().equals(requiredRole)) {
            throw new SecurityException(
                    "Access denied for role: " + user.getRole()
            );
        }
    }
}
