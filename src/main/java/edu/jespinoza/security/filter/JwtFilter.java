package edu.jespinoza.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jespinoza.exception.CustomMessage;
import edu.jespinoza.security.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("authorizationHeader {}", authorizationHeader);
        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            if (jwtService.isTokenExpired(token)) {
                String msg = "Sesión Expiró";
                log.error(msg);
                CustomMessage securityMessage = new CustomMessage(400, msg);
                showError(response, securityMessage);
                return;
            }

            try {
                userName = jwtService.extractUsername(token);
            } catch (Exception e) {
                log.error(e.getMessage());
                CustomMessage securityMessage = new CustomMessage(403, e.getMessage());
                showError(response, securityMessage);
                return;
            }
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = service.loadUserByUsername(userName);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void showError(HttpServletResponse response,
                           CustomMessage securityMessage)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("text/json");
        ObjectMapper jsonArray = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.println(jsonArray.writeValueAsString(securityMessage));
    }
}
