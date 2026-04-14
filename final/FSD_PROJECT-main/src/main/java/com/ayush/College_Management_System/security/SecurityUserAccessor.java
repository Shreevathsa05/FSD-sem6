package com.ayush.College_Management_System.security;

import com.ayush.College_Management_System.security.user.AppUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserAccessor {

    public AppUserDetails requireCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required");
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof AppUserDetails user) {
            return user;
        }
        throw new AccessDeniedException("Invalid security principal");
    }

    public AppUserDetails getCurrentUser() {
        return requireCurrentUser();
    }
}
