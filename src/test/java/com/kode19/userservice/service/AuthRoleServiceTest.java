package com.kode19.userservice.service;

import com.kode19.userservice.dto.AuthRoleDTO;
import com.kode19.userservice.dto.converter.AuthRoleConverter;
import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;
import com.kode19.userservice.entity.AuthRole;
import com.kode19.userservice.repository.AuthRoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthRoleServiceTest {

    @Autowired
    private AuthRoleService authRoleService;

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
    void createRole() {
        AuthRoleRequestDTO dto = new AuthRoleRequestDTO();
        dto.setRoleName(roleNameCreated);
        DataProcessSuccessResponseDTO<AuthRoleDTO> role = authRoleService.createRole(dto);

        assertThat(role.getData().getRoleName(), is(equalTo(dto.getRoleName())));

    }

    @Test
    @Order(2)
    void updateRole() {

        Optional<AuthRole> byRoleName = authRoleRepository.findByRoleName(roleNameCreated);
        AuthRoleRequestDTO dto = new AuthRoleRequestDTO();
        dto.setRoleName(roleNameUpdated);

        if (byRoleName.isPresent()) {
            DataProcessSuccessResponseDTO<AuthRoleDTO> authRoleDTODataProcessSuccessResponseDTO = authRoleService.updateRole(byRoleName.get().getSecureId(), dto);
            assertThat(authRoleDTODataProcessSuccessResponseDTO.getData().getRoleName(), is(equalTo(dto.getRoleName())));
        }
    }

    @Test
    @Order(3)
    void getAllRoles() {

        PagingResponseDTO<AuthRoleDTO> allRoles = authRoleService.getAllRoles(0, 10, "roleName", "DESC", null);
        assertTrue(allRoles.getData().size() > 0);

    }

    @Test
    @Order(4)
    void deleteRole() {
        Optional<AuthRole> byRoleName = authRoleRepository.findByRoleName(roleNameUpdated);
        if(byRoleName.isPresent()){
            authRoleService.deleteRole(byRoleName.get().getSecureId());
            byRoleName = authRoleRepository.findByRoleName(roleNameUpdated);
            assertTrue(byRoleName.isEmpty(), "FAIL TO DELETE DATA");
        }

    }

    @Test
    @Order(5)
    void tryInitiateUtilityClass() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<AuthRoleConverter> constructor = AuthRoleConverter.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();

    }
}