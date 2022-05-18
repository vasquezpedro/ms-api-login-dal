package com.bci.exercises.dal.util;

import java.util.Date;

import com.bci.exercises.dal.exceptions.AutenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {
    

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    static Logger slogger = LoggerFactory.getLogger(JwtTokenUtil.class);
    
    public String generateJwtToken(String id){
        return Jwts.builder().setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    

    public void validateToken(String token,String id) throws AutenticationException{
        try {
            Jws<Claims> jwsClaims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            String valueKeySub=(String) jwsClaims.getBody().get("sub");
            if(!valueKeySub.equalsIgnoreCase(id)){
                throw new AutenticationException(Constantes.TOKEN_MSG_ERROR,2);
            }
        }catch (MalformedJwtException e){
            slogger.error(Constantes.TOKEN_MSG_MALFORMED,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_MALFORMED,Constantes.TOKEN_CODE_ERROR);
        }catch (UnsupportedJwtException e){
            slogger.error(Constantes.TOKEN_MSG_UNSUPPORTED,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_UNSUPPORTED,Constantes.TOKEN_CODE_ERROR);
        }catch (ExpiredJwtException e){
            slogger.error(Constantes.TOKEN_MSG_EXPIRED,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_EXPIRED,Constantes.TOKEN_CODE_ERROR);
        }catch (IllegalArgumentException e){
            slogger.error(Constantes.TOKEN_MSG_ILLEGAL_ARGS,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_ILLEGAL_ARGS,Constantes.TOKEN_CODE_ERROR);
        }
    }

    public void validateToken(String token) throws AutenticationException{
        try {
            Jws<Claims> jwsClaims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }catch (MalformedJwtException e){
            slogger.error(Constantes.TOKEN_MSG_MALFORMED,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_MALFORMED,Constantes.TOKEN_CODE_ERROR);
        }catch (UnsupportedJwtException e){
            slogger.error(Constantes.TOKEN_MSG_UNSUPPORTED,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_UNSUPPORTED,Constantes.TOKEN_CODE_ERROR);
        }catch (ExpiredJwtException e){
            slogger.error(Constantes.TOKEN_MSG_EXPIRED,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_EXPIRED,Constantes.TOKEN_CODE_ERROR);
        }catch (IllegalArgumentException e){
            slogger.error(Constantes.TOKEN_MSG_ILLEGAL_ARGS,e);
            throw new AutenticationException(Constantes.TOKEN_MSG_ILLEGAL_ARGS,Constantes.TOKEN_CODE_ERROR);
        }
    }
}
