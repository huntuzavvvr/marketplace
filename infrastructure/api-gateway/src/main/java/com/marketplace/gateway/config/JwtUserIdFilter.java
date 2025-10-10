package com.marketplace.gateway.config;

import com.marketplace.common.utils.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class JwtUserIdFilter implements GlobalFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no jwt found");
        }

        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION).substring(7);

        if (!jwtTokenUtil.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "token not valid");
        }
        try {

            String user = jwtTokenUtil.extractUsername(token);

            String role = jwtTokenUtil.extractRole(token);
            ServerWebExchange mutatedRequest = exchange.mutate().request(r -> r.headers(h -> {
                h.add("X-Auth-User", user);
                h.add("X-Auth-Role", role);
            })).build();


            return chain.filter(mutatedRequest);
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
