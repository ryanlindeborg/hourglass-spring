package com.ryanlindeborg.hourglass.hourglassspring.security;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityUtils {
    /**
     * This is the generic auth error that will be returned no matter what auth error gets thrown (Security by obscurity)
     * @param response
     */
    public static void sendAuthError(HttpServletResponse response) {
        try {
            response.setHeader("Content-Type", "application/json");
            response.setStatus(403);
            response.getWriter().println("{ \"error\": \"Access denied. Please re-authenticate\" }");
            response.getWriter().flush();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
