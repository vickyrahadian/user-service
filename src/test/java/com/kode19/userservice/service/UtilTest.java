package com.kode19.userservice.service;

import com.kode19.userservice.util.DataProcessingUtil;
import com.kode19.userservice.util.PaginationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UtilTest {


    @Test
    void tryInitiateUtilityClass() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<DataProcessingUtil> constructor = DataProcessingUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();



    }

    @Test
    void tryInitiateUtilityClass2() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<PaginationUtil> constructor = PaginationUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();

    }
}