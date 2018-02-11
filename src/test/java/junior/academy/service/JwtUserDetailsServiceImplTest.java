package junior.academy.service;


import junior.academy.domain.Authority;
import junior.academy.domain.AuthorityName;
import junior.academy.domain.User;
import junior.academy.security.JwtUser;
import junior.academy.security.JwtUserFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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

    @Test
    public void loadUserByUsername(){
        Optional<User> user=Optional.ofNullable(getUser());
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        List<GrantedAuthority> authorities=getAuthorityList();
        JwtUser jwtUser=new JwtUser(1L,"test",null,"password", authorities,true,null);
        assertEquals(jwtUserDetailsService.loadUserByUsername(user.get().getUsername()).getUsername(),jwtUser.getUsername());
    }

    public User getUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("test");
        user.setPassword("password");

        List<Authority> authorities=new ArrayList<>();
        Authority authority1=new Authority();
        authority1.setAuthorityId(1L);
        authority1.setName(AuthorityName.ROLE_USER);
        authorities.add(authority1);
        user.setAuthorities(authorities);
        return user;
    }

    public List<GrantedAuthority> getAuthorityList(){
        List<Authority> authorities=new ArrayList<>();
        Authority authority1=new Authority();
        authority1.setAuthorityId(1L);
        authority1.setName(AuthorityName.ROLE_USER);
        authorities.add(authority1);

        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());

    }

}