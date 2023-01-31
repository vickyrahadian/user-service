package com.kode19.userservice.controller;

import com.kode19.userservice.entity.AuthRole;
import com.kode19.userservice.repository.AuthRoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthRoleRepository authRoleRepository;

    private static String roleNameCreated;
    private static String roleNameUpdated;

    @BeforeAll
    static void prepareData() {

        roleNameCreated = UUID.randomUUID().toString().toUpperCase().substring(0, 10);
        roleNameUpdated = UUID.randomUUID().toString().toUpperCase().substring(0, 10);

    }

    @Test
    @Order(1)
    void registerRoleTest() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/role")
                        .content(createContent(roleNameCreated))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(roleNameCreated)))
                .andReturn();
    }

    @Test
    @Order(2)
    void registerRoleTest_failed() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/role")
                        .content(createContent(""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Order(3)
    void registerRoleTest_failed_to_long() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/role")
                        .content(createContent("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \\\"de Finibus Bonorum et Malorum\\\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \\\"Lorem ipsum dolor sit amet..\\\", comes from a line in section 1.10.32."))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    @Order(4)
    void registerRoleTest_failed_no_content() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/role")
                        .content(createContent(""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Order(5)
    void registerRoleTest_failed_duplicate() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/role")
                        .content(createContent(roleNameCreated))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Order(971)
    void updateRole_failed_duplicate() throws Exception {
        Optional<AuthRole> byRoleName = authRoleRepository.findByRoleName(roleNameCreated);

        if (byRoleName.isPresent()) {
            this.mockMvc
                    .perform(put("/api/v1/auth/role/" + byRoleName.get().getSecureId())
                            .content(createContent(roleNameCreated))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn();
        }
    }

    @Test
    @Order(972)
    void updateRole_failed_empty() throws Exception {
        Optional<AuthRole> byRoleName = authRoleRepository.findByRoleName(roleNameCreated);

        if (byRoleName.isPresent()) {
            this.mockMvc
                    .perform(put("/api/v1/auth/role/XXX")
                            .content(createContent(roleNameUpdated))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn();
        }
    }

    @Test
    @Order(973)
    void updateRole() throws Exception {
        Optional<AuthRole> byRoleName = authRoleRepository.findByRoleName(roleNameCreated);

        if (byRoleName.isPresent()) {
            this.mockMvc
                    .perform(put("/api/v1/auth/role/" + byRoleName.get().getSecureId())
                            .content(createContent(roleNameUpdated))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(roleNameUpdated)))
                    .andReturn();
        }
    }

    @Test
    @Order(981)
    void deleteRole() throws Exception {
        Optional<AuthRole> byRoleName = authRoleRepository.findByRoleName(roleNameUpdated);
        if (byRoleName.isPresent()) {
            this.mockMvc
                    .perform(delete("/api/v1/auth/role/" + byRoleName.get().getSecureId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

        }

    }

    @Test
    @Order(982)
    void deleteRole_fail_empty() throws Exception {
        this.mockMvc
                .perform(delete("/api/v1/auth/role/XXX"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Order(1000)
    void getAllRolesTest() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/auth/role"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("status_code")))
                .andReturn();
    }

    private String createContent(String value) {
        return "{ \"" + "role_name" + "\" : \"" + value + "\" }";
    }
}