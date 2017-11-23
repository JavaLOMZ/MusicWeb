package junior.academy.service;


import junior.academy.domain.User;
import junior.academy.security.JwtUserFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class JwtUserDetailsServiceImplTest  {

    @Mock
    UserService userService;

    @InjectMocks
    JwtUserDetailsServiceImpl jwtUserDetailsService;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    public User createUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("test");
        user.setPassword("password");
        return user;
    }

}