package com.jesomi.springcloudgateway.route;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthsRoute {

//    private final String AUTHS_HOST = "http://localhost:8080";
//
//    @Bean
//    public RouteLocator authsRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .method("GET", "POST").and()
//                        .path("//management/file-manage/upload1/{fileManageCd}")
//                        .filters(f -> f
//                                .filter(new AuthenticationFilter().apply(
//                                        e -> e.setAllowRole(new String[]{
//                                                UserRoles.ADMIN.name(),
//                                                UserRoles.CS.name(),
//                                                UserRoles.SETTLE.name(),
//                                                UserRoles.SALES.name(),
//                                                UserRoles.MARKETING.name()})))
//                                .circuitBreaker(c -> c.setName("defaultCircuitBreaker")))
//                        .uri(AUTHS_HOST))
//                .build();
//    }

}
