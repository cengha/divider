package com.cengha.divider.spring;

import com.cengha.divider.model.User;
import com.cengha.divider.service.GameService;
import com.cengha.divider.service.UserService;
import com.cengha.divider.spring.annotation.ActiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ActiveGameArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ActiveGame.class) && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User userByEmail = userService.findUserByEmail(name);
        return gameService.getFirstByPlayerOneIdOrPlayerTwoId(userByEmail.getId());

    }

}
