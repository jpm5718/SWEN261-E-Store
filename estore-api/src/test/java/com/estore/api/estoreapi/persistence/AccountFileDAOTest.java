package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Account File DAO class
 * 
 * @author SWEN Faculty - altered by J Russ
 */
@Tag("Persistence-tier")
public class AccountFileDAOTest {
    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAccounts = new Account[3];
        testAccounts[0] = new Account(1,"Pete","password",new ArrayList<Integer>());
        testAccounts[1] = new Account(2,"Bob","passmord",new ArrayList<Integer>());
        testAccounts[2] = new Account(3,"Amy","passw0rd",new ArrayList<Integer>());

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the account array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Account[].class))
                .thenReturn(testAccounts);
        accountFileDAO = new AccountFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetAccounts() {
        // Invoke
        Account[] accounts = accountFileDAO.getAccounts();

        // Analyze
        assertEquals(accounts.length,testAccounts.length);
        for (int i = 0; i < testAccounts.length;++i)
            assertEquals(accounts[i],testAccounts[i]);
    }

    @Test
    public void testFindAccounts() {
        // Invoke
        try {
            Account[] accounts = accountFileDAO.findAccounts("Pete");

            // Analyze
            assertEquals(accounts.length,1);
            assertEquals(accounts[0],testAccounts[0]);

        } catch (Exception e) {
            assertNotNull(null);
        }

    }

    @Test
    public void testGetAccount() {
        // Invoke
        Account account = accountFileDAO.getAccount(1);

        // Analzye
        assertEquals(account,testAccounts[0]);
    }

    @Test
    public void testDeleteAccount() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount(1),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test accounts array - 1 (because of the delete)
        // Because accounts attribute of AccountFileDAO is package private
        // we can access it directly
        assertEquals(accountFileDAO.Accounts.size(),testAccounts.length-1);
    }

    @Test
    public void testCreateAccount() {
        try {
            // Setup
            Account account = new Account(4,"Bill","pAssword",new ArrayList<Integer>());

            // Invoke
            Account result = assertDoesNotThrow(() -> accountFileDAO.createAccount(account),
                                    "Unexpected exception thrown");

            // Analyze
            assertNotNull(result);
            Account actual = accountFileDAO.getAccount(account.getId());
            assertEquals(actual.getId(),account.getId());
            assertEquals(actual.getUsername(),account.getUsername());

        } catch (Exception e) {
            assertNotNull(null);
        }
    }

    @Test
    public void testUpdateAccount() {

        try {
            // Setup
            Account account = new Account(2,"Amy","passw0rd",new ArrayList<Integer>());

            // Invoke
            Account result = assertDoesNotThrow(() -> accountFileDAO.updateAccount(account),
                                    "Unexpected exception thrown");

            // Analyze
            assertNotNull(result);
            Account actual = accountFileDAO.getAccount(account.getId());
            assertEquals(actual,account);

        } catch (Exception e) {
            assertNotNull(null);
        }
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Account[].class));

        Account account = new Account(6,"Chuck","passwoRd",new ArrayList<Integer>());

        assertThrows(IOException.class,
                        () -> accountFileDAO.createAccount(account),
                        "IOException not thrown");
    }

    @Test
    public void testGetAccountNotFound() {
        // Invoke
        Account account = accountFileDAO.getAccount(98);

        // Analyze
        assertEquals(account,null);
    }

    @Test
    public void testDeleteAccountNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(accountFileDAO.Accounts.size(),testAccounts.length);
    }

    @Test
    public void testUpdateAccountNotFound() {
        // Setup

        try {
            Account account = new Account(90,"Tod","passworD",new ArrayList<Integer>());

            // Invoke
            Account result = assertDoesNotThrow(() -> accountFileDAO.updateAccount(account),
                                                    "Unexpected exception thrown");

            // Analyze
            assertNull(result);
            
        } catch (Exception e) {
            assertNotNull(null);
        }
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the AccountFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Account[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new AccountFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}

