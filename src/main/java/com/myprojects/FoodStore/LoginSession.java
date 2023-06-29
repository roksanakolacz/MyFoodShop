package com.myprojects.FoodStore;

import com.myprojects.FoodStore.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class LoginSession {
    private Integer userId;

    public LoginSession(Integer userId)
    {
        this.userId = userId;
    }


}

