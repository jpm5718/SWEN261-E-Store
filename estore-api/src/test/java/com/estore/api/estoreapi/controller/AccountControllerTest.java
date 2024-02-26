package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.persistence.AccountDAO;
import com.estore.api.estoreapi.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Account Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class AccountControllerTest {
    private AccountController AccountController;
    private AccountDAO mockAccountDAO;

    /**
     * Before each test, create a new AccountController object and inject
     * a mock Account DAO
     */
    @BeforeEach
    public void setupAccountController() {
        mockAccountDAO = mock(AccountDAO.class);
        AccountController = new AccountController(mockAccountDAO);
    }

    @Test
    public void testGetAccount() throws IOException {  // getAccount may throw IOException
        // Setup
        Account Account = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // When the same id is passed in, our mock Account DAO will return the Account object
        when(mockAccountDAO.getAccount(Account.getId())).thenReturn(Account);

        // Invoke
        ResponseEntity<Account> response = AccountController.getAccount(Account.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Account,response.getBody());
    }

    @Test
    public void testGetAccountNotFound() throws Exception { // createAccount may throw IOException
        // setup
        int id = 0;
        // our mock Account DAO will return null, simulating
        // no Accounts found
        when(mockAccountDAO.getAccount(id)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = AccountController.getAccount(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetAccountHandleException() throws Exception { // createAccount may throw IOException
        // Setup
        int AccountId = 99;
        // When getAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).getAccount(AccountId);

        // Invoke
        ResponseEntity<Account> response = AccountController.getAccount(AccountId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all AccountController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateAccount() throws IOException {  // createAccount may throw IOException
        // Setup
        Account Account = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when createAccount is called, return true simulating successful
        // creation and save
        when(mockAccountDAO.createAccount(Account)).thenReturn(Account);

        // Invoke
        ResponseEntity<Account> response = AccountController.createAccount(Account);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(Account,response.getBody());
    }

    @Test
    public void testCreateAccountFailed() throws IOException {  // createAccount may throw IOException
        // Setup
        Account Account = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when createAccount is called, return false simulating failed
        // creation and save
        when(mockAccountDAO.createAccount(Account)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = AccountController.createAccount(Account);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateAccountHandleException() throws IOException {  // createAccount may throw IOException
        // Setup
        Account Account = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );

        // When createAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).createAccount(Account);

        // Invoke
        ResponseEntity<Account> response = AccountController.createAccount(Account);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateAccount() throws IOException { // updateAccount may throw IOException
        // Setup
        Account Account = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.updateAccount(Account)).thenReturn(Account);
        ResponseEntity<Account> response = AccountController.updateAccount(Account);
        Account.setUsername("Pete");

        // Invoke
        response = AccountController.updateAccount(Account);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Account,response.getBody());
    }

    @Test
    public void testUpdateAccountFailed() throws IOException { // updateAccount may throw IOException
        // Setup
        Account Account = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.updateAccount(Account)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = AccountController.updateAccount(Account);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateAccountHandleException() throws IOException { // updateAccount may throw IOException
        // Setup
        Account Account = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // When updateAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).updateAccount(Account);

        // Invoke
        ResponseEntity<Account> response = AccountController.updateAccount(Account);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAccountes() throws IOException { // getAccountes may throw IOException
        // Setup
        Account[] Accountes = new Account[2];
        Accountes[0] = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Accountes[1] = new Account(2,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // When getAccountes is called return the Accountes created above
        when(mockAccountDAO.getAccounts()).thenReturn(Accountes);

        // Invoke
        ResponseEntity<Account[]> response = AccountController.getAllAccount();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Accountes,response.getBody());
    }
    
    @Test
    public void testGetAllAccountNotFound() throws Exception { // createAccount may throw IOException
        // our mock Account DAO will return null, simulating
        // no Accounts found
        when(mockAccountDAO.getAccounts()).thenReturn(null);

        // Invoke
        ResponseEntity<Account[]> response = AccountController.getAllAccount();

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetAccountesHandleException() throws IOException { // getAccountes may throw IOException
        // Setup
        // When getAccountes is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).getAccounts();

        // Invoke
        ResponseEntity<Account[]> response = AccountController.getAllAccount();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchAccountes() throws IOException { // findAccountes may throw IOException
        // Setup
        String searchString = "la";
        Account[] Accountes = new Account[2];
        Accountes[0] = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Accountes[1] = new Account(2,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // When findAccountes is called with the search string, return the two
        /// Accountes above
        when(mockAccountDAO.findAccounts(searchString)).thenReturn(Accountes);

        // Invoke
        ResponseEntity<Account[]> response = AccountController.searchAccount(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Accountes,response.getBody());
    }

    @Test
    public void testSearchAccountesNotFound() throws IOException { // findAccountes may throw IOException
        // Setup
        String searchString = "la";
        Account[] Accountes = new Account[2];
        Accountes[0] = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Accountes[1] = new Account(2,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // When findAccountes is called with the search string, return the two
        /// Accountes above
        when(mockAccountDAO.findAccounts(searchString)).thenReturn(null);

        // Invoke
        ResponseEntity<Account[]> response = AccountController.searchAccount(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    @Test
    public void testSearchAccountesStrict() throws IOException { // findAccountes may throw IOException
        // Setup
        String searchString = "la";
        Account[] Accountes = new Account[2];
        Accountes[0] = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Accountes[1] = new Account(2,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // When findAccountes is called with the search string, return the two
        /// Accountes above
        when(mockAccountDAO.findAccountStrict(searchString)).thenReturn(Accountes);

        // Invoke
        ResponseEntity<Account[]> response = AccountController.searchAccountStrict(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Accountes,response.getBody());
    }

    @Test
    public void testSearchAccountesStrictNotFound() throws IOException { // findAccountes may throw IOException
        // Setup
        String searchString = "la";
        Account[] Accountes = new Account[2];
        Accountes[0] = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Accountes[1] = new Account(2,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // When findAccountes is called with the search string, return the two
        /// Accountes above
        when(mockAccountDAO.findAccountStrict(searchString)).thenReturn(null);

        // Invoke
        ResponseEntity<Account[]> response = AccountController.searchAccountStrict(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    @Test
    public void testSearchAccountesStrictError() throws IOException { // findAccountes may throw IOException
        // Setup
        String searchString = "la";
        // When getAccountes is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).findAccountStrict(searchString) ;

        // Invoke
        ResponseEntity<Account[]> response = AccountController.searchAccountStrict(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchAccountesHandleException() throws IOException { // findAccountes may throw IOException
        // Setup
        String searchString = "an";
        // When createAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).findAccounts(searchString);

        // Invoke
        ResponseEntity<Account[]> response = AccountController.searchAccount(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteAccount() throws IOException { // deleteAccount may throw IOException
        // Setup
        int AccountId = 99;
        // when deleteAccount is called return true, simulating successful deletion
        when(mockAccountDAO.deleteAccount(AccountId)).thenReturn(true);

        // Invoke
        ResponseEntity<Account> response = AccountController.deleteAccount(AccountId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteAccountNotFound() throws IOException { // deleteAccount may throw IOException
        // Setup
        int AccountId = 99;
        // when deleteAccount is called return false, simulating failed deletion
        when(mockAccountDAO.deleteAccount(AccountId)).thenReturn(false);

        // Invoke
        ResponseEntity<Account> response = AccountController.deleteAccount(AccountId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteAccountHandleException() throws IOException { // deleteAccount may throw IOException
        // Setup
        int AccountId = 99;
        // When deleteAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).deleteAccount(AccountId);

        // Invoke
        ResponseEntity<Account> response = AccountController.deleteAccount(AccountId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


























    @Test
    public void testAddItemToCart() throws IOException { // updateAccount may throw IOException
        // Setup
        int vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.addItemToCart(vin_id,acc_id)).thenReturn(acc);
        ResponseEntity<Account> response = AccountController.addItemToAccount(vin_id,acc_id);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(acc,response.getBody());
    }
    @Test
    public void testAddItemToCartNotFound() throws IOException { // updateAccount may throw IOException
        // Setup
        int vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.addItemToCart(vin_id,acc_id)).thenReturn(null);
        ResponseEntity<Account> response = AccountController.addItemToAccount(vin_id,acc_id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }
    @Test
    public void testAddItemToCartError() throws IOException { // updateAccount may throw IOException
        // Setup
        int vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        doThrow(new IOException()).when(mockAccountDAO).addItemToCart(vin_id,acc_id);

        ResponseEntity<Account> response = AccountController.addItemToAccount(vin_id,acc_id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }



    @Test
    public void testRemItemToCart() throws IOException { // updateAccount may throw IOException
        // Setup
        int vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.removeItemFromCart(vin_id,acc_id)).thenReturn(acc);
        ResponseEntity<Account> response = AccountController.removeItemFromAccount(vin_id,acc_id);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(acc,response.getBody());
    }
    @Test
    public void testRemItemToCartNotFound() throws IOException { // updateAccount may throw IOException
        // Setup
        int vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.removeItemFromCart(vin_id,acc_id)).thenReturn(null);
        ResponseEntity<Account> response = AccountController.removeItemFromAccount(vin_id,acc_id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }
    @Test
    public void testRemItemToCartError() throws IOException { // updateAccount may throw IOException
        // Setup
        int vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        // when updateAccount is called, return true simulating successful
        // update and save
        doThrow(new IOException()).when(mockAccountDAO).removeItemFromCart(vin_id,acc_id);

        ResponseEntity<Account> response = AccountController.removeItemFromAccount(vin_id,acc_id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchCart() throws IOException { // updateAccount may throw IOException
        // Setup
        Integer vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Account[] accs = new Account[1] ;
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.findCarts(vin_id)).thenReturn(accs);
        ResponseEntity<Account[]> response = AccountController.searchAccountCart(vin_id) ;

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(accs,response.getBody());
    }
    @Test
    public void testSearchCartNotFound() throws IOException { // updateAccount may throw IOException
        // Setup
        Integer vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Account[] accs = new Account[1] ;
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.findCarts(vin_id)).thenReturn(null);
        ResponseEntity<Account[]> response = AccountController.searchAccountCart(vin_id) ;

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }
    @Test
    public void testSearchCartError() throws IOException { // updateAccount may throw IOException
        // Setup
        Integer vin_id = 1;
        int acc_id = 1;
        Account acc = new Account(1,"DEFAULTUSERNAME","DEFAULTPASSWORD",new ArrayList<Integer>() );
        Account[] accs = new Account[1] ; 
        // when updateAccount is called, return true simulating successful
        // update and save
        doThrow(new IOException()).when(mockAccountDAO).findCarts(vin_id);

        ResponseEntity<Account[]> response = AccountController.searchAccountCart(vin_id) ;

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
