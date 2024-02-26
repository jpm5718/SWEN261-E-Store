package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Account;
import com.estore.api.estoreapi.persistence.AccountDAO;


@RestController
@RequestMapping("account")
public class AccountController {
    private static final Logger LOG = Logger.getLogger(Account.class.getName());
    private AccountDAO AccountDAO;

    public AccountController(AccountDAO AccountDAO){
        this.AccountDAO = AccountDAO;
    }
    @GetMapping("")
    public ResponseEntity<Account[]> getAllAccount() {
        LOG.info("GET /account");
        try {
            Account[] Accounts = AccountDAO.getAccounts();
            if (Accounts != null)
                return new ResponseEntity<>(Accounts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Account> updateAccount(@RequestBody Account Account) {
        LOG.info("PUT /account " + Account);
        try {
            Account updatedAccount = AccountDAO.updateAccount(Account);
            if(updatedAccount != null)
                return new ResponseEntity<Account>(updatedAccount,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{AccountId}/add/{vinylId}")
    public ResponseEntity<Account> addItemToAccount(@PathVariable int vinylId, @PathVariable int AccountId) {
        LOG.info("GET /account/add/" + AccountId + "/" + vinylId);
        try {
            Account updatedAccount = AccountDAO.addItemToCart(vinylId, AccountId);
            if(updatedAccount != null)
                return new ResponseEntity<Account>(updatedAccount,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{AccountId}/remove/{vinylId}")
    public ResponseEntity<Account> removeItemFromAccount(@PathVariable int vinylId, @PathVariable int AccountId) {
        LOG.info("Get /account/remove/" + AccountId + "/" + vinylId);
        try {
            Account updatedAccount = AccountDAO.removeItemFromCart(vinylId, AccountId);
            if(updatedAccount != null)
                return new ResponseEntity<Account>(updatedAccount,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account Account){
        LOG.info("POST /account " + Account);
        
        try {
            Account newAccount = AccountDAO.createAccount(Account);
            if(newAccount != null){
                return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cart/")
    public ResponseEntity<Account[]> searchAccountCart(@RequestParam Integer searchInt) {
        LOG.info("GET /account?searchInt=" + searchInt);
        try {
            Account[] Accounts = AccountDAO.findCarts(searchInt);
            if (Accounts != null) {
                return new ResponseEntity<Account[]>(Accounts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Account[]> searchAccount(@RequestParam String searchStr) {
        LOG.info("GET /account/?searchStr=" + searchStr);
        try {
            Account[] Accounts = AccountDAO.findAccounts(searchStr);
            if (Accounts != null) {
                return new ResponseEntity<Account[]>(Accounts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/strict/")
    public ResponseEntity<Account[]> searchAccountStrict(@RequestParam String searchStr) {
        LOG.info("GET /account/strict/?searchStr=" + searchStr);
        try {
            Account[] Accounts = AccountDAO.findAccountStrict(searchStr);
            if (Accounts != null) {
                return new ResponseEntity<Account[]>(Accounts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable int id){
        LOG.info("GET /account/" + id);
        try{
            Account Account = AccountDAO.getAccount(id);
            if(Account != null)
                return new ResponseEntity<Account>(Account, HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            LOG.info(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable int id){
        LOG.info("DELETE /account/" + id);

        try{
            boolean result = AccountDAO.deleteAccount(id);
            if(result)
                return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
