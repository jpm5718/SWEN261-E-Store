package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Account;

/**
 * Implements the functionality for JSON file-based peristance for Accounts
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author J Russ - referenced from SEWN faculty
 */
@Component
public class AccountFileDAO implements AccountDAO {
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    Map<Integer,Account> Accounts;   // Provides a local cache of the Account objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Account
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new Account
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Account File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AccountFileDAO(@Value("${account.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the Accounts from the file
    }

    // because Accounts need to have SPECIFIC AccountIDs, we need to add logc that makes sure duplicate IDs are not created
    // /**
    //  * Generates the next id for a new {@linkplain Account Account}
    //  * 
    //  * @return The next id
    //  */
    // private synchronized static int nextId() {
    //     int id = nextId;
    //     ++nextId;
    //     return id;
    // }

    /**
     * Generates an array of {@linkplain Account Accounts} from the tree map
     * 
     * @return  The array of {@link Account Accounts}, may be empty
     */
    private Account[] getAccountsArray() {
        return getAccountsArrayInt(null);
    }

    /**
     * Generates an array of {@linkplain Account Accounts} from the tree map for any
     * {@linkplain Account Accounts} that contains the vinylID specified by containsNum
     * <br>
     * If containsNum is null, the array contains all of the {@linkplain Account Accounts}
     * in the tree map
     * 
     * @return  The array of {@link Account Accounts}, may be empty
     */
    private Account[] getAccountsArrayInt(Integer containsNum) { // if containsText == null, no filter
        ArrayList<Account> AccountArrayList = new ArrayList<>();

        for (Account Account : Accounts.values()) {
            if (containsNum == null || Account.getCart().contains(containsNum)) {
                AccountArrayList.add(Account);
            }
        }

        Account[] AccountArray = new Account[AccountArrayList.size()];
        AccountArrayList.toArray(AccountArray);
        return AccountArray;
    }

    /**
     * Generates an array of {@linkplain Account Accounts} from the tree map for any
     * {@linkplain Account Accounts} that contains the username specified by containsStr
     * <br>
     * If containsStr is null, the array contains all of the {@linkplain Account Accounts}
     * in the tree map
     * 
     * @return  The array of {@link Account Accounts}, may be empty
     */
    private Account[] getAccountsArrayStr(String containsStr) { // if containsText == null, no filter
        ArrayList<Account> AccountArrayList = new ArrayList<>();

        for (Account Account : Accounts.values()) {
            if (containsStr == null || Account.getUsername().contains(containsStr)) {
                AccountArrayList.add(Account);
            }
        }

        Account[] AccountArray = new Account[AccountArrayList.size()];
        AccountArrayList.toArray(AccountArray);
        return AccountArray;
    }

    private Account[] getAccountsArrayStrStrict(String strictStr) {
        ArrayList<Account> AccountArrayList = new ArrayList<>();

        for (Account Account : Accounts.values()) {
            if (Account.getUsername().contentEquals(strictStr)) {
                AccountArrayList.add(Account);
            }
        }

        Account[] AccountArray = new Account[AccountArrayList.size()];
        AccountArrayList.toArray(AccountArray);
        return AccountArray;
    }

    /**
     * Saves the {@linkplain Account Accounts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Account Accounts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Account[] AccountArray = getAccountsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),AccountArray);
        return true;
    }

    /**
     * Loads {@linkplain Account Accounts} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        Accounts = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Accounts
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Account[] AccountArray = objectMapper.readValue(new File(filename),Account[].class);

        // Add each Account to the tree map and keep track of the greatest id
        for (Account Account : AccountArray) {
            Accounts.put(Account.getId(),Account);
            if (Account.getId() > nextId)
                nextId = Account.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account[] getAccounts() {
        synchronized(Accounts) {
            return getAccountsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account[] findCarts(Integer containsNum) {
        synchronized(Accounts) {
            return getAccountsArrayInt(containsNum);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account[] findAccounts(String containsStr) throws IOException {
        synchronized(Accounts) {
            return getAccountsArrayStr(containsStr);
        }
    }

    public Account[] findAccountStrict(String containsStr) throws IOException {
        synchronized(Accounts) {
            return getAccountsArrayStrStrict(containsStr);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account getAccount(int id) {
        synchronized(Accounts) {
            if (Accounts.containsKey(id))
                return Accounts.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account createAccount(Account Account) throws IOException {
        synchronized(Accounts) {
            // We create a new Account object because the id field is immutable
            Account newAccount = new Account(Account.getId(),Account.getUsername(),Account.getPassword(),Account.getCart());
            Accounts.put(newAccount.getId(),newAccount);
            save(); // may throw an IOException
            return newAccount;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account updateAccount(Account Account) throws IOException {
        synchronized(Accounts) {
            if (Accounts.containsKey(Account.getId()) == false)
                return null;  // Account does not exist

            Accounts.put(Account.getId(),Account);
            save(); // may throw an IOException
            return Account;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteAccount(int id) throws IOException {
        synchronized(Accounts) {
            if (Accounts.containsKey(id)) {
                Accounts.remove(id);
                return save();
            }
            else
                return false;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account addItemToCart(int vinylId, int AccountId) throws IOException {
        VinylDAO vinylDAO = new VinylFileDAO("./data/vinyl.json", new ObjectMapper()) ;
        synchronized(Accounts) {
            if (Accounts.containsKey(AccountId) == false || vinylDAO.getVinyl(vinylId) == null)
                return null;  // Account does not exist

            System.out.println("this is a test out");
            Account account = Accounts.get(AccountId) ;
            account.getCart().add(vinylId) ; // add vinylId to Account int array 
            
            Accounts.put(AccountId,account);
            save(); // may throw an IOException
            return account;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account removeItemFromCart(int vinylId, int AccountId) throws IOException {
        synchronized(Accounts) {
            if (Accounts.containsKey(AccountId) == false )
                return null;  // Account does not exist

            Integer id = vinylId ;
            Account Account = Accounts.get(AccountId) ;
            Account.getCart().remove(id) ; // remove vinylId from Account int array 
            
            Accounts.put(AccountId,Account);
            save(); // may throw an IOException
            return Account;
        }
    }

    @Override
    public Account clearCart(int AccountId) throws IOException {
        synchronized(Accounts) {
            if (Accounts.containsKey(AccountId) == false )
                return null;  // Account does not exist

            Account Account = Accounts.get(AccountId) ;
            Account.getCart().clear();
            
            Accounts.put(AccountId,Account);
            save(); // may throw an IOException
            return Account;
        }
    }
    
}
