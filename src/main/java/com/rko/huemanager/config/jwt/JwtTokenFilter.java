package com.rko.huemanager.config.jwt;

import com.rko.huemanager.service.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final EmployeeService employeeService;
    private final String secretKey;

    private boolean isSkippablePath(String path) {
        // 인증이 필요 없는 경로 리스트
        List<String> skipPaths = Arrays.asList(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/api/employee/signup",
                "/api/employee/login"
        );

        // antPathMatcher를 사용하여 경로 패턴 매칭 확인
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return skipPaths.stream().anyMatch(p -> pathMatcher.match(p, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isSkippablePath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("without Bearer error");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        UserDetails employeeDetails = employeeService.loadUserByUsername(JwtTokenUtils.getUsername(token, secretKey));

        if (!JwtTokenUtils.validate(token, employeeDetails, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                employeeDetails, null, employeeDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
