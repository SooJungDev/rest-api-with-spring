package me.crystal.demoinfleanrestapi.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import me.crystal.demoinfleanrestapi.common.BaseControllerTest;


@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest extends BaseControllerTest {


    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    public void findByUsername() {
        //Given
        String password = "soojung";
        String username = "soojung@email.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(account);

        //when
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //Then
        assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test
    public void findByUsernameFail() {
        //Expected
        String username = "random@email.com";

    assertThrows(UsernameNotFoundException.class, ()->{
            accountService.loadUserByUsername(username);
        });

    }

}
