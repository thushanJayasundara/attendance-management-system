package com.org.carder.config;

import com.org.carder.DTOs.UserDTO;

import com.org.carder.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       response.setStatus(HttpServletResponse.SC_OK);
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        HttpSession session = request.getSession();

        UserDTO userDTO = userService.getUserDTOByEmpNum(Long.valueOf(authentication.getName()));

        session.setAttribute("username", userDTO.getName());
        session.setAttribute("department", userDTO.getDepartmentDTO().getDepartmentTitle());
        session.setAttribute("empNumber" , userDTO.getEmpNumber());


        if (roles.contains("ROLE_ADMIN"))
            response.sendRedirect("/dash");
         if (roles.contains("ROLE_EMPLOYEE"))
            response.sendRedirect("/userCom");
         if (roles.contains("ROLE_MANAGER"))
             response.sendRedirect("/mComReplay");

    }
}
