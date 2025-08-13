package com.kefiya.home.configs.security.access;

import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class RateFilter implements Filter {
    final Logger logger = LoggerFactory.getLogger(RateFilter.class);
    private final Bucket bucket;

   public RateFilter(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

       if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            logger.error("rate exceeded");
            httpServletResponse.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "rate exceeded");
        }
    }


    @Override
    public void destroy() {
    }
}
