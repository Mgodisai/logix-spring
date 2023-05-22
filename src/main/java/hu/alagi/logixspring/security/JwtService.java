package hu.alagi.logixspring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.alagi.logixspring.config.SecurityConfigProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

@Service
public class JwtService {

    private final SecurityConfigProperties securityConfigProperties;

    public JwtService(SecurityConfigProperties securityConfigProperties) {
        this.securityConfigProperties = securityConfigProperties;
    }

    public String createJwtToken(UserDetails principal) {
        String algorithmName = securityConfigProperties.getAlgorithm();
        Algorithm alg = getAlgorithm(algorithmName);
        long duration = securityConfigProperties.getDuration().toMillis();
        return JWT.create()
                .withSubject(principal.getUsername())
                .withIssuer(securityConfigProperties.getIssuer())
                .withArrayClaim("auth",
                        principal.getAuthorities().toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis()+duration))
                .sign(alg);
    }

    private Algorithm getAlgorithm(String algorithmName) {
        if (algorithmName.startsWith("HMAC")) {
            try {
                Method method = Algorithm.class.getMethod(algorithmName, String.class);
                return (Algorithm) method.invoke(null, securityConfigProperties.getSecret());
            } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                     | InvocationTargetException e) {
                return Algorithm.none();
            }
        } else {
            return Algorithm.none();
        }
    }

    public UserDetails parseJwt(String jwtToken) {
        String algorithmName = securityConfigProperties.getAlgorithm();
        Algorithm alg = getAlgorithm(algorithmName);
        DecodedJWT decodedJWT = JWT.require(alg)
                .withIssuer(securityConfigProperties.getIssuer())
                .build()
                .verify(jwtToken);
        return new User(decodedJWT.getSubject(), "dummy",
                decodedJWT.getClaim("auth").asList(String.class).stream().map(Role::valueOf).toList());
    }
}
