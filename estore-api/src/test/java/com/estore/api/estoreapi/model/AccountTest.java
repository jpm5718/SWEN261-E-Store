package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class AccountTest {

    @Test
    public void testCtor() throws IOException {
        // Setup
        int id = 100;
        String username = "Bob";
        String password = "bob's_password";  
        ArrayList<Integer> cart = new ArrayList<>();
        cart.add(1);
        cart.add(101);
             

        // Invoke
        Account account = new Account(id, username, password, cart);

        // Analyze
        assertEquals(id,account.getId());
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
        
    }

    @Test
    public void testUsername() throws IOException {
        // Setup
        int id = 100;
        String username = "";
        String password = "bob's_password"; 
        ArrayList<Integer> cart = new ArrayList<>();
        cart.add(1);
        cart.add(101); 
        Account account = new Account(id, username, password, cart);
        String expected_string = "Bob";
        account.setUsername(expected_string);

        // Invoke
        String actual_string = account.getUsername();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

}