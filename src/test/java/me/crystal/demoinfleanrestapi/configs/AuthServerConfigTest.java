package me.crystal.demoinfleanrestapi.configs;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import me.crystal.demoinfleanrestapi.accounts.AccountService;
import me.crystal.demoinfleanrestapi.common.AppProperties;
import me.crystal.demoinfleanrestapi.common.BaseControllerTest;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AppProperties appProperties;

    @Test
    @DisplayName("인증 토큰을 발급받는 테스트")
    public void getAuthToken() throws Exception {
        this.mockMvc.perform(post("/oauth/token")
                                     .with(httpBasic(appProperties.getClientId(),
                                                     appProperties.getClientSecret()))
                                     .param("username", appProperties.getUserUsername())
                                     .param("password", appProperties.getUserPassword())
                                     .param("grant_type", "password")
            )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("access_token").exists());

    }

}
