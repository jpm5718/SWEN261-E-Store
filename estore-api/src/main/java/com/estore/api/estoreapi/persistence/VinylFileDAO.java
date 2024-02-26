package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Vinyl;

/**
 * Implements the functionality for JSON file-based peristance for Vinyles
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author J Russ - referenced from SEWN faculty
 */
@Component
public class VinylFileDAO implements VinylDAO {
    private static final Logger LOG = Logger.getLogger(VinylFileDAO.class.getName());
    Map<Integer,Vinyl> Vinyles;   // Provides a local cache of the Vinyl objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Vinyl
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new Vinyl
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Vinyl File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public VinylFileDAO(@Value("${vinyl.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the Vinyles from the file
    }

    /**
     * Generates the next id for a new {@linkplain Vinyl Vinyl}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Vinyl Vinyles} from the tree map
     * 
     * @return  The array of {@link Vinyl Vinyles}, may be empty
     */
    private Vinyl[] getVinylesArray() {
        return getVinylesArray(null);
    }

    /**
     * Generates an array of {@linkplain Vinyl Vinyles} from the tree map for any
     * {@linkplain Vinyl Vinyles} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Vinyl Vinyles}
     * in the tree map
     * 
     * @return  The array of {@link Vinyl Vinyles}, may be empty
     */
    private Vinyl[] getVinylesArray(String containsText) { // if containsText == null, no filter
        Integer count = 0;
        Set<Vinyl> VinylArrayList = new HashSet<>();
        for (Vinyl Vinyl : Vinyles.values()) {
            if(containsText == null){
                VinylArrayList.add(Vinyl);
                continue;
            }
            else{
                String[] text = containsText.split(" ");
                
                for(String x:text){
                    x = x.toLowerCase();
                    if(Vinyl.getTitle().toLowerCase().contains(x) || Vinyl.getArtist().toLowerCase().contains(x)){
                        if(count == 0){
                            VinylArrayList.add(Vinyl);
                        }
                        count = 1;
                        continue;
                    }
                    else if(count == 1 && containsText != null){
                        VinylArrayList.remove(Vinyl);
                    }
                    else if(containsText != null){
                        VinylArrayList.remove(Vinyl);
                        count = 1;
                    }
                }
                count = 0;
            }
        }    
        Vinyl[] VinylArray = new Vinyl[VinylArrayList.size()];
        VinylArrayList.toArray(VinylArray);
        Comparator<Vinyl> byId = new Comparator<Vinyl>() {
            @Override
            public int compare(Vinyl o1, Vinyl o2) {
                return o1.getId() - o2.getId() ;
            }
        };
        Arrays.sort(
            VinylArray,
            byId
        );
        return VinylArray;
    }

    /**
     * Saves the {@linkplain Vinyl Vinyles} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Vinyl Vinyles} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Vinyl[] VinylArray = getVinylesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),VinylArray);
        return true;
    }

    /**
     * Loads {@linkplain Vinyl Vinyles} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        Vinyles = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Vinyles
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Vinyl[] VinylArray = objectMapper.readValue(new File(filename),Vinyl[].class);

        // Add each Vinyl to the tree map and keep track of the greatest id
        for (Vinyl Vinyl : VinylArray) {
            Vinyles.put(Vinyl.getId(),Vinyl);
            if (Vinyl.getId() > nextId)
                nextId = Vinyl.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Vinyl[] getVinyles() {
        synchronized(Vinyles) {
            return getVinylesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Vinyl[] findVinyles(String containsText) {
        synchronized(Vinyles) {
            return getVinylesArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Vinyl getVinyl(int id) {
        synchronized(Vinyles) {
            if (Vinyles.containsKey(id))
                return Vinyles.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Vinyl createVinyl(Vinyl Vinyl) throws IOException {
        synchronized(Vinyles) {
            // We create a new Vinyl object because the id field is immutable
            // and we need to assign the next unique id
            Vinyl newVinyl = new Vinyl(nextId(),Vinyl.getTitle(), Vinyl.getPrice(), Vinyl.getArtist(),
                 Vinyl.getSize(), Vinyl.getSpeed(), Vinyl.getsList());
            Vinyles.put(newVinyl.getId(),newVinyl);
            save(); // may throw an IOException
            return newVinyl;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Vinyl updateVinyl(Vinyl Vinyl) throws IOException {
        synchronized(Vinyles) {
            if (Vinyles.containsKey(Vinyl.getId()) == false)
                return null;  // Vinyl does not exist

            Vinyles.put(Vinyl.getId(),Vinyl);
            save(); // may throw an IOException
            return Vinyl;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteVinyl(int id) throws IOException {
        synchronized(Vinyles) {
            if (Vinyles.containsKey(id)) {
                Vinyles.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
