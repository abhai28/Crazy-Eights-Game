package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@SpringBootTest
public class AcceptanceTest {
    @Autowired
    GameData gd;


    @Test
    public void testRow67(){
        //gd.set
    }
}
