package com.jesomi.springcloudgateway.filter;

import com.jesomi.springcloudgateway.exception.AccessTokenExpiredException;
import com.jesomi.springcloudgateway.exception.ForbiddenException;
import com.jesomi.springcloudgateway.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

            // ClientIP 정보 header 로 전달
            request.mutate().header("clientIp", this.getIpAddr(request));

            return chain.filter(exchange);
        };
    }

    private String getIpAddr(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if(StringUtils.hasLength(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if(index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeaders().getFirst("Proxy-Client-IP");
        if (StringUtils.hasLength(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        if (StringUtils.hasLength(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        if (StringUtils.hasLength(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        if (StringUtils.hasLength(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
    }
}
