package com.example.shopapp.filters;

import com.example.shopapp.components.JwtTokenUtil;
import com.example.shopapp.constants.Constants;
import com.example.shopapp.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        //filterChain.doFilter(request,response);// enable pass

        try{
            if (isByPassToken(request)){
                filterChain.doFilter(request,response);
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractNumber(token);
            if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if (jwtTokenUtil.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
            filterChain.doFilter(request,response);
        }
        catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }


    }

    private boolean isByPassToken(@NotNull HttpServletRequest request){
        final List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of(Constants.API_VERSION+"/products","GET"),
                Pair.of(Constants.API_VERSION+"/categories","GET"),
                Pair.of(Constants.API_VERSION+"/users/register","POST"),
                Pair.of(Constants.API_VERSION+"/users/login","POST")
        );
        for (Pair<String, String > byPassToken : byPassTokens){
            if (request.getServletPath().contains(byPassToken.getFirst()) && request.getMethod().equals(byPassToken.getSecond())){
                return true;
            }
        }
        return false;
    }
}
