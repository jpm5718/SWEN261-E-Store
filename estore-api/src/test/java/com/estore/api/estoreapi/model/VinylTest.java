package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for Vinyl.java
 * 
 * @author James McGuire
 */

 @Tag("Model-Tier")
 public class VinylTest{
    @Test
    public void testConstructor(){
        //Setup
        int expected_id = 99;
        String expected_title = "Test Album";
        double expected_price = 10.00;
        String expected_artist = "James McGuire";
        int expected_speed = 33;
        int expected_size = 12;
        ArrayList<Song> expected_s_List = new ArrayList<Song>();

        //Invoke
        Vinyl vinyl = new Vinyl(expected_id, expected_title, expected_price, expected_artist,
            expected_speed, expected_size, expected_s_List);
        
        //Analyze
        assertEquals(expected_id, vinyl.getId());
        assertEquals(expected_title, vinyl.getTitle());
        assertEquals(expected_price, vinyl.getPrice());
        assertEquals(expected_artist, vinyl.getArtist());
        assertEquals(expected_speed, vinyl.getSpeed());
        assertEquals(expected_size, vinyl.getSize());
        assertEquals(expected_s_List, vinyl.getsList());
    }

    @Test
    public void testPrice(){
        //Setup
        int id = 99;
        String title = "Test Album";
        double price = 10.00;
        String artist = "James McGuire";
        int speed = 33;
        int size = 12;
        ArrayList<Song> s_List = new ArrayList<Song>();
        
        Vinyl vinyl = new Vinyl(id, title, price, artist, speed, size, s_List);

        double expected_price = 20.00;

        //Invoke
        vinyl.setPrice(expected_price);

        //Analyze
        assertEquals(expected_price, vinyl.getPrice());
    }

    @Test
    public void testQuantity(){
        //Setup
        int id = 99;
        String title = "Test Album";
        double price = 10.00;
        String artist = "James McGuire";
        int speed = 33;
        int size = 12;
        ArrayList<Song> s_List = new ArrayList<Song>();
        
        Vinyl vinyl = new Vinyl(id, title, price, artist, speed, size, s_List);

        int expected_quantity = 50;

        //Invoke
        vinyl.setQuantity(expected_quantity);

        //Analyze
        assertEquals(expected_quantity, vinyl.getQuantity());
    }
 }