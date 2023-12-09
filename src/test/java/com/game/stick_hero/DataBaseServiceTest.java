package com.game.stick_hero;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseServiceTest {

    @Test
    void getMainUser()  {
       // DataBaseService.connect();
        assertThrows(UserNotFoundException.class, ()->{DataBaseService.getMainUser("test","testi","test");});

    }


    @BeforeAll
    static void connect(){
        DataBaseService.connect();
    }
    @AfterAll
    static void close(){
        DataBaseService.mongoClient.close();
        System.out.println("Hello");
    }

}