
package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/user/*")
public class UserAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        
        boolean loggedIn = (session != null && session.getAttribute("userId") != null);

        String uri = req.getRequestURI();

        // ---------- PUBLIC PAGES ----------
        boolean isPublicPage =
        		uri.endsWith("/ShoppingCart/") ||
                uri.endsWith("/user/index.jsp") ||
                uri.endsWith("/user/product-detail.jsp") ||
                uri.endsWith("/user/login.jsp") ||
                uri.endsWith("/user/register.jsp") ||
                uri.endsWith("/user/about.jsp");

       
//        // ---------- STATIC RESOURCES ----------
//        boolean isStaticResource =
//                uri.contains("/assets/") ||
//                uri.contains("/product-images/");

        // ---------- ALLOW ----------
        if (loggedIn || isPublicPage ){//|| isStaticResource) {

            chain.doFilter(request, response);
        } else {

            resp.sendRedirect(req.getContextPath()
                    + "/user/login.jsp?msg=Please+Login");
        }
    }
}
