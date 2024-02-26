package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Account;
import com.estore.api.estoreapi.model.Vinyl;

/**
 * Defines the interface for Account object persistence
 * 
 * @author J Russ - referenced from SEWN faculty
 */
public interface AccountDAO {
    /**
     * Retrieves all {@linkplain Account Accounts}
     * 
     * @return An array of {@link Account Account} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account[] getAccounts() throws IOException;

    /**
     * Finds all {@linkplain Account Accounts} whose username list contains the given string
     * 
     * @param containsStr The text to match against
     * 
     * @return An array of {@link Account Accounts} whose username list contains the given string, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account[] findAccounts(String containsStr) throws IOException;

    Account[] findAccountStrict(String containsStr) throws IOException;

    /**
     * Finds all {@linkplain Account Accounts} whose username list contains the given string
     * 
     * @param containsStr The text to match against
     * 
     * @return An array of {@link Account Accounts} whose username list contains the given string, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account[] findCarts(Integer containsNum) throws IOException;

    /**
     * Retrieves a {@linkplain Account Account} with the given id
     * 
     * @param id The id of the {@link Account Account} to get
     * 
     * @return a {@link Account Account} object with the matching id
     * <br>
     * null if no {@link Account Account} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account getAccount(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Account Account}
     * 
     * @param Account {@linkplain Account Account} object to be created and saved
     * <br>
     * The id of the Account object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Account Account} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account createAccount(Account Account) throws IOException;

    /**
     * Updates and saves a {@linkplain Account Account}
     * 
     * @param {@link Account Account} object to be updated and saved
     * 
     * @return updated {@link Account Account} if successful, null if
     * {@link Account Account} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Account updateAccount(Account Account) throws IOException;

    /**
     * Deletes a {@linkplain Account Account} with the given id
     * 
     * @param id The id of the {@link Account Account}
     * 
     * @return true if the {@link Account Account} was deleted
     * <br>
     * false if Account with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteAccount(int id) throws IOException;

    /**
     * adds a {@linkplain Vinyl Vinyl} to the {@linkplain Account Account} with the given id
     * 
     * @param vinylId The id of the {@link Vinyl vinyl} to be added to the {@link Account Account}
     * @param AccountId The id of the {@link Account Account}
     * 
     * @return updated {@link Account Account} if successful, null if
     * {@link Account Account} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Account addItemToCart(int vinylId, int AccountId) throws IOException;

    /**
     * removes a {@linkplain Vinyl Vinyl} from the {@linkplain Account Account} with the given id
     * 
     * @param vinylId The id of the {@link Vinyl vinyl} to be removed from the {@link Account Account}
     * @param AccountId The id of the {@link Account Account}
     * 
     * @return updated {@link Account Account} if successful, null if
     * {@link Account Account} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Account removeItemFromCart(int vinylId, int AccountId) throws IOException;

    /**
     * removes all {@linkplain Vinyl Vinyl} from the {@linkplain Account Account}
     * 
     * @param AccountId The id of the {@link Account Account}
     * 
     * @return updated {@link Account Account} if successful, null if
     * {@link Account Account} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Account clearCart(int AccountId) throws IOException;
}
