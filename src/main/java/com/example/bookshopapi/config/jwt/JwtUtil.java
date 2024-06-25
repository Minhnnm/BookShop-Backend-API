package com.example.bookshopapi.config.jwt;

import com.example.bookshopapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        // Tạo JWT từ thông tin người dùng
        // Sử dụng thư viện JWT để tạo mã JWT và thêm thông tin người dùng
        return Jwts.builder()
//                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000)) // 10 giờ
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UserDetails extractUserDetails(String token) {
        // Trích xuất thông tin người dùng từ JWT
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return new org.springframework.security.core.userdetails.User(username, "", new ArrayList<>());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // Kiểm tra tính hợp lệ của token
        final String username = extractId(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();
            return expirationDate.before(currentDate); //true đã hết hạn or false còn hiệu lực
        } catch (Exception e) {
            return false;
        }
    }

    public LocalDateTime extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();//Chuyển đổi đối tượng Instant thành LocalDateTime dựa trên múi giờ hệ thống mặc định của máy tính
    }

    public String extractId(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
