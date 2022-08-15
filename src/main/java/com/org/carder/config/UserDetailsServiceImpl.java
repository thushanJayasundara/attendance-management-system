package com.org.carder.config;

import com.org.carder.DTOs.UserDTO;
import com.org.carder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

   UserDTO userDTO = userService.getUserDTOByEmpNum(Long.parseLong(userName));
        if (userDTO != null) {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + userDTO.getUserRole());
            return new User(userDTO.getEmpNumber(), userDTO.getPassword(),grantedAuthorities);
        }
        throw new UsernameNotFoundException("Username: " + userName + " not found");
    }
}
