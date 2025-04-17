package com.p3backEnd.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.p3backEnd.configuration.SpringSecurityConfig;

@Service
public class JWTService {

    private final JwtEncoder jwtEncoder;
    private final SpringSecurityConfig springSecurityConfig;
    
    public JWTService(JwtEncoder jwtEncoder,
    		          SpringSecurityConfig springSecurityConfig) {
    	this.springSecurityConfig = springSecurityConfig;
        this.jwtEncoder = jwtEncoder;
    }
    
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }
    
	public String generateTokenForRegistering(String name) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.DAYS))
				.subject(name)
				.build();
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
		return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
	}
	
    public String getNameFromToken(String token){
        token = deleteBearerFromToken(token);
        if(token != null){
            Jwt claims = this.springSecurityConfig.jwtDecoder().decode(token);
            return claims.getSubject();
        } else {
            return "";
        }
    }

    public String deleteBearerFromToken(String token) {
        if(token != null){
            if(token.startsWith("Bearer ")) return token.substring(7);
        }
        return null;
    }
}