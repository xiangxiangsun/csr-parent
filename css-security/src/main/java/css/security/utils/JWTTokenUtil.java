package css.security.utils;

import css.security.common.config.JWTConfig;
import css.security.entity.SysUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTTokenUtil {
    public static String createAccessToken(SysUser sysUser){
        String token = Jwts.builder()
                //放入用户名和用户ID
        .setId(sysUser.getId()+"")
                //主题
        .setSubject(sysUser.getUsername())
                //签发时间
        .setIssuedAt(new Date())
                //签发者
        .setIssuer("sxx")
                //自定义权限
        //失效时间
        .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                //签名算法和密钥
        .signWith(SignatureAlgorithm.HS512,JWTConfig.secret)
        .compact();
        return token;
    }
}
