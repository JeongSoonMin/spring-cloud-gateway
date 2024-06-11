package com.jesomi.springcloudgateway.route;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagementRoute {

//    private final String MANAGEMENT_HOST = "http://localhost:8080";
//
//    @Bean
//    public RouteLocator managementRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .method("GET", "POST").and()
//                        .path("/management/file-manage/upload/{fileManageCd}")
//                        .filters(f -> f
//                                .filter(new AuthenticationFilter().apply(
//                                        e -> e.setAllowRole(new String[]{
//                                                UserRoles.ADMIN.name(),
//                                                UserRoles.CS.name(),
//                                                UserRoles.SETTLE.name(),
//                                                UserRoles.SALES.name(),
//                                                UserRoles.MARKETING.name()})))
//                                .circuitBreaker(c -> c.setName("defaultCircuitBreaker")))
//                        .uri(MANAGEMENT_HOST))
//                .route(p -> p
//                        .method("GET").and()
//                        .path("/management/file-manage/pre-signed-url/{fileManageCd}")
//                        .filters(f -> f
//                                .filter(new DedupeResponseHeaderGatewayFilterFactory().apply(e -> e.setStrategy()))
//                                .filter(new AuthenticationFilter().apply(
//                                        e -> e.setAllowRole(new String[]{
//                                                UserRoles.ADMIN.name(),
//                                                UserRoles.CS.name(),
//                                                UserRoles.SETTLE.name(),
//                                                UserRoles.SALES.name(),
//                                                UserRoles.MARKETING.name()})))
//                                .circuitBreaker(c -> c.setName("defaultCircuitBreaker")))
//                        .uri(MANAGEMENT_HOST))
//                .route(p -> p
//                        .method("PUT", "DELETE").and()
//                        .path("/management/file-manage/upload/{fileManageCd}/{fileSeq}")
//                        .filters(f -> f
//                                .filter(new AuthenticationFilter().apply(
//                                        e -> e.setAllowRole(new String[]{
//                                                UserRoles.ADMIN.name(),
//                                                UserRoles.CS.name(),
//                                                UserRoles.SETTLE.name(),
//                                                UserRoles.SALES.name(),
//                                                UserRoles.MARKETING.name()})))
//                                .circuitBreaker(c -> c.setName("defaultCircuitBreaker")))
//                        .uri(MANAGEMENT_HOST))
//                .build();
//    }

}
