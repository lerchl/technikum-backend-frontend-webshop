package at.technikumwien.webshop.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Request does not contain any cookies
        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Find JWT cookie
        Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("jwt")).findFirst();

        // JWT cookie not present
        if (jwtCookie.isEmpty() || jwtCookie.get().getValue() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Create authorization token
        Optional<UsernamePasswordAuthenticationToken> authToken = createAuthToken(jwtCookie.get().getValue());

        // JWT is invalid, auth token could not be created
        if (authToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT is valid, set auth token
        SecurityContextHolder.getContext().setAuthentication(authToken.get());
        filterChain.doFilter(request, response);
    }

    private Optional<UsernamePasswordAuthenticationToken> createAuthToken(String jwt) {
        // Parse JWT
        Optional<UserPrincipal> userPrincipal = tokenService.parseToken(jwt);

        // JWT is invalid
        if (userPrincipal.isEmpty()) {
            return Optional.empty();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add admin role if user is admin
        if (userPrincipal.get().isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // Create auth token
        return Optional.of(new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities));
    }
}
