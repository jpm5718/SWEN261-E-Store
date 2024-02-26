package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for Song.java
 * 
 * @author James McGuire
 */

 @Tag("Model-Tier")
 public class SongTest{
    @Test
    public void testConstructor(){
        //Setup
        String expected_title = "Test Track";
        String expected_author = "James McGuire";
        String expected_duration = "4:00";

        //Invoke
        Song song = new Song(expected_title, expected_author);

        //Analyze
        //No getters, not sure what to assert 
    }
}