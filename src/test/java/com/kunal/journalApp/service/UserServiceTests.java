package com.kunal.journalApp.service;

import com.kunal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            "ram"
    })
    public void testFindByUserName(String userName) {
        assertNotNull(userRepository.findByUserName(userName), "Failed for " + userName);
    }
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,2,9"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }
}
