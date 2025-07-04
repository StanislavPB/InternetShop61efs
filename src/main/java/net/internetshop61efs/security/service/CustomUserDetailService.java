package net.internetshop61efs.security.service;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User foundedUser = userService.findByEmail(email);
        UserDetails userDetails = new UserToUserDetails(foundedUser);
        return userDetails;

    }
}
