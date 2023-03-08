package com.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//1. 요청의 헤더에서 Bearer 토큰을 가져온다. 이 작업은 parseBearerToken() 메서드에서 이뤄진다.
//2.TokenProvider를 이용 토큰을 인증하고 UsernamePasswordAuthenticationToken을 작성한다. 이 오브젝트에 사용자의 인증 정보를 저장하고
//SecurityContext에 인증된 사용자를 등록한다. 서버가 요청이 끝나기 전까지 방금 인증한 사용자의 정보를 갖고 있어야 하기 때문이다.
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 토큰 가져오기.
            String token = parseBearerToken(request);
            log.info("Filter is running...");
            // 토큰 검사하기. JWT이므로 인가 서버에 요청 하지 않고도 검증 가능.
            if (token != null && !token.equalsIgnoreCase("null")) {
                // userId 가져오기. 위조 된 경우 예외 처리 된다.
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID : " + userId );
                // 인증 완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // 인증된 사용자의 정보. 문자열이 아니어도 아무거나 넣을 수 있다. 보통 UserDetails라는 오브젝트를 넣는데, 우리는 안 만들었음.
                        null, // @AuthenticationPrincipal를 사용하면 userId를 쓸 수 있다.
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); //시큐리티콘텍스트 생성
                securityContext.setAuthentication(authentication); //컨텍스트에 인증정보 입력
                SecurityContextHolder.setContext(securityContext); //시큐리티 컨텍스트 홀더에 컨텍스트 등록
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }
    private String parseBearerToken(HttpServletRequest request){
        //HTTP요청의 헤더를 파싱해 Bearer토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
