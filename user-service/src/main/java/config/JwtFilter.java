package config;

import com.r2s.auth.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestUrl = request.getRequestURL().toString();
        String authHeader = request.getHeader("Authorization");

        System.out.println("[JwtFilter] Request: " + requestUrl);
        System.out.println("[JwtFilter] Authorization header: " + (authHeader != null ? authHeader.substring(0, Math.min(20, authHeader.length())) + "..." : "null"));

        String token = null;
        String username = null;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[JwtFilter] No Bearer token found → continue without auth");
            chain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);
        System.out.println("[JwtFilter] Token extracted (length): " + token.length());

        try {
            username = jwtUtil.extractUsername(token);
            System.out.println("[JwtFilter] Extracted username: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("[JwtFilter] Loading UserDetails for: " + username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("[JwtFilter] UserDetails loaded → authorities: " + userDetails.getAuthorities());

                boolean valid = jwtUtil.validateToken(token, userDetails);
                System.out.println("[JwtFilter] validateToken result: " + valid);

                if (valid) {
                    System.out.println("[JwtFilter] Token VALID → Setting Authentication");
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                } else {
                    System.out.println("[JwtFilter] Token INVALID theo validateToken → 401");
                    sendErrorResponse(response, "Token không hợp lệ (validate thất bại)");
                    return;
                }
            }
        } catch (ExpiredJwtException e) {
            System.out.println("[JwtFilter] Token đã hết hạn: " + e.getMessage());
            sendErrorResponse(response, "Token đã hết hạn");
            return;
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            System.out.println("[JwtFilter] Token cấu trúc sai: " + e.getMessage());
            sendErrorResponse(response, "Token không đúng định dạng");
            return;
        } catch (SignatureException e) {
            System.out.println("[JwtFilter] Signature không khớp (secret key sai?): " + e.getMessage());
            sendErrorResponse(response, "Token chữ ký không hợp lệ");
            return;
        } catch (Exception e) {
            System.out.println("[JwtFilter] Lỗi khác khi xử lý token: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            sendErrorResponse(response, "Lỗi xử lý token: " + e.getMessage());
            return;
        }

        chain.doFilter(request, response);
    }


    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write(message);
    }
}