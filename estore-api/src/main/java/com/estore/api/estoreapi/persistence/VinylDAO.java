package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Vinyl;

/**
 * Defines the interface for Vinyl object persistence
 * 
 * @author J Russ - referenced from SEWN faculty
 */
public interface VinylDAO {
    /**
     * Retrieves all {@linkplain Vinyl Vinyles}
     * 
     * @return An array of {@link Vinyl Vinyl} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Vinyl[] getVinyles() throws IOException;

    /**
     * Finds all {@linkplain Vinyl Vinyles} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Vinyl Vinyles} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Vinyl[] findVinyles(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Vinyl Vinyl} with the given id
     * 
     * @param id The id of the {@link Vinyl Vinyl} to get
     * 
     * @return a {@link Vinyl Vinyl} object with the matching id
     * <br>
     * null if no {@link Vinyl Vinyl} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Vinyl getVinyl(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Vinyl Vinyl}
     * 
     * @param Vinyl {@linkplain Vinyl Vinyl} object to be created and saved
     * <br>
     * The id of the Vinyl object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Vinyl Vinyl} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Vinyl createVinyl(Vinyl Vinyl) throws IOException;

    /**
     * Updates and saves a {@linkplain Vinyl Vinyl}
     * 
     * @param {@link Vinyl Vinyl} object to be updated and saved
     * 
     * @return updated {@link Vinyl Vinyl} if successful, null if
     * {@link Vinyl Vinyl} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Vinyl updateVinyl(Vinyl Vinyl) throws IOException;

    /**
     * Deletes a {@linkplain Vinyl Vinyl} with the given id
     * 
     * @param id The id of the {@link Vinyl Vinyl}
     * 
     * @return true if the {@link Vinyl Vinyl} was deleted
     * <br>
     * false if Vinyl with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteVinyl(int id) throws IOException;
}
