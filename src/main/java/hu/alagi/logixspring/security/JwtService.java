package hu.alagi.logixspring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.alagi.logixspring.config.SecurityConfigProperties;
import hu.alagi.logixspring.config.SecurityConfigProperties.JwtData;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

@Service
public class JwtService {
    public static final String AUTH = "auth";
    private final SecurityConfigProperties securityConfigProperties;
    private JwtData jwtData;

    public JwtService(SecurityConfigProperties securityConfigProperties) {
        this.securityConfigProperties = securityConfigProperties;
    }

    @PostConstruct
    public void init() {
        this.jwtData = securityConfigProperties.getJwtData();
    }

    public String createJwtToken(UserDetails principal) {
        String algorithmName = jwtData.getAlgorithm();
        Algorithm algorithm = getAlgorithm(algorithmName);
        return JWT.create()
                .withSubject(principal.getUsername())
                .withIssuer(jwtData.getIssuer())
                .withArrayClaim(AUTH,
                        principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis()+jwtData.getDuration().toMillis()))
                .sign(algorithm);
    }

    private Algorithm getAlgorithm(String algorithmName) {
        try {
            Method method = Algorithm.class.getMethod(algorithmName, String.class);
            return (Algorithm) method.invoke(null, jwtData.getSecret());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                 | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public UserDetails parseJwt(String jwtToken) {
        String algorithmName = jwtData.getAlgorithm();
        Algorithm alg = getAlgorithm(algorithmName);
        DecodedJWT decodedJWT = JWT.require(alg)
                .withIssuer(jwtData.getIssuer())
                .build()
                .verify(jwtToken);
        return new User(decodedJWT.getSubject(), "dummy",
                decodedJWT.getClaim(AUTH).asList(String.class).stream().map(Role::getRoleValue).toList());
    }
}
