package be.brahms.TFE_RentServe.configurations.security;

import be.brahms.TFE_RentServe.utilities.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This filter checks each HTTP request for a JWT token.
 * If the token is valid, it tells Spring Security who the user is.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil JwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Creates a JwtFilter with the needed services.
     *
     * @param jwtUtil            the helper to read and check tokens
     * @param userDetailsService the service to find users by pseudo
     */
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        JwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;

    }

    /**
     * This method runs for every HTTP request.
     * It checks if the Authorization header contains a Bearer token.
     * If the token is valid, the user is set in the SecurityContext.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the rest of the filters
     * @throws ServletException if there is a problem with the request
     * @throws IOException      if there is a problem with input/output
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization"); // Get the "authorization" for the request from header

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String[] authorizations = authorizationHeader.split(" ");

            // Check if the header is present and starts with "Bearer"
            if (authorizations.length == 2) {
                String type = authorizations[0]; // "Bearer"
                String token = authorizations[1]; // Le JWT token

                if (type.equals("Bearer ") && !token.isEmpty()) { // Check if it's a Bearer token and not empty
                    if (JwtUtil.isValidToken(token)) { // Check valid token
                        String pseudo = JwtUtil.getPseudo(token);

                        UserDetails user = userDetailsService.loadUserByUsername(pseudo); // Load user details by pseudo

                        UsernamePasswordAuthenticationToken uPaT = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(uPaT); // Set authentication in security context
                    }
                }
            } else {
                // Token format is wrong (for example: only "Bearer" without token)
                throw new ServletException("Invalid Authorization header format.");
            }
        } else {
            // No Authorization header or does not start with "Bearer"
            System.out.println("Missing or incorrect Authorization header");
        }
        // Continue with the rest of the request
        filterChain.doFilter(request, response); // continue the filter chain
    }
}
