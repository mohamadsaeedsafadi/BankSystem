package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "SuperSecretKey";
    private static final Algorithm ALGORITHM =
            Algorithm.HMAC256(SECRET);

    public static String generateToken(AuthenticatedUser user) {

        return JWT.create()
                .withClaim("userId", user.getUserId())
                .withClaim("role", user.getRole())
                .withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + 3600000)
                )
                .sign(ALGORITHM);
    }

    public static AuthenticatedUser validateToken(String token) {

        DecodedJWT jwt = JWT.require(ALGORITHM)
                .build()
                .verify(token);

        return new AuthenticatedUser(
                jwt.getClaim("userId").asString(),
                jwt.getClaim("role").asString()
        );
    }
}
