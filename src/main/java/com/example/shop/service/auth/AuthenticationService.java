package com.example.shop.service.auth;

import com.example.shop.model.entity.User;
import com.example.shop.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        login = login.trim();
        if (login==null || login==""){
            throw new UsernameNotFoundException("Invalid login credentials:(");
        }

        Optional<User> user = userService.getByLogin(login);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid login credentials:(");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        return new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(),grantedAuthorities);
    }
}
