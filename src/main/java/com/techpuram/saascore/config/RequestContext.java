package com.techpuram.saascore.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.techpuram.saascore.entity.Users;

@Component
@RequestScope
public class RequestContext {
   
    private static final ThreadLocal<Users> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(Users user) {
        currentUser.set(user);
    }

    public static Users getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }

}
