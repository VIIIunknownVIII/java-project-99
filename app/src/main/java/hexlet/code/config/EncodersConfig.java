package hexlet.code.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import hexlet.code.component.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class EncodersConfig {

    @Autowired
    private RsaKeyProperties rsaKeys;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        if (rsaKeys.getPublicKey() == null) {
            throw new IllegalArgumentException("Public key cannot be null");
        }
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        if (rsaKeys.getPublicKey() == null || rsaKeys.getPrivateKey() == null) {
            throw new IllegalArgumentException("RSA keys cannot be null");
        }
        JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey())
                .privateKey(rsaKeys.getPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(jwk);
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(jwkSet));
    }
}
