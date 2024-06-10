package com.jesomi.springcloudgateway.filter;

import com.jesomi.springcloudgateway.exception.AccessTokenExpiredException;
import com.jesomi.springcloudgateway.exception.ForbiddenException;
import com.jesomi.springcloudgateway.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Arrays;
import java.util.Base64;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationFilter extends
        AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Data
    public static class Config {
        private String[] allowRole;
    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String accessToken = request.getHeaders().getFirst("Authorization");
            Jws<Claims> claimsJws;

            if (accessToken != null) {
                accessToken = accessToken.replace("Bearer ", "");

                try {
                    claimsJws = Jwts.parser()
                            .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                            .parseClaimsJws(accessToken);
                } catch (Exception e) {
                    throw new AccessTokenExpiredException();
                }

                if (claimsJws != null) {
                    // 토큰 값이 파싱 될 경우 header에 사용자 정보 추가하여 전달.
                    request.mutate().header("userId", claimsJws.getBody().getSubject()).build();
                    request.mutate().header("userRole", claimsJws.getBody().get("roles").toString())
                            .build();
                }
            } else {
                claimsJws = null;
            }

            if (config.getAllowRole() != null) {
                // allow role이 지정되어 있을 경우 필수 권한 체크
                if (claimsJws == null) {
                    throw new UnAuthorizedException();
                }

                // 해당 권한 가지고 있지 않을 경우 Forbidden Exception 처리
                Arrays.stream(config.getAllowRole())
                        .filter(e -> e.equals(claimsJws.getBody().get("roles").toString()))
                        .findFirst()
                        .orElseThrow(ForbiddenException::new);
            }

            return chain.filter(exchange);
        };
    }
}
