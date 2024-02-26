package com.estore.api.estoreapi.model;


import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Vinyl entity.
 * 
 * @author James McGuire
 */
public class Vinyl {
    private static final Logger LOG = Logger.getLogger(Vinyl.class.getName());

    //String format insert here

    /**Json Property Variables */
    @JsonProperty("id") private int id;
    @JsonProperty("title") private String title;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("artist") private String artist;
    @JsonProperty("speed") private int speed ;
    @JsonProperty("size") private int size;
    @JsonProperty("coverImagePath") private String coverImagePath ;
    @JsonProperty("sList") private ArrayList<Song> sList ;
    private Duration total_duration;

    

    /**
     * Create a Vinyl object with the given parameters
     * @param id The id of the Vinyl
     * @param title The name of the Vinyl
     * @param price The price of the Vinyl
     * @param quantity The quantity of Vinyl available at the time
     * @param artist The artist of the Vinyl
     * @param speed The speed of the Vinyl
     * @param size The size of the Vinyl
     * @param sList The Song list of the Vinyl
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Vinyl(@JsonProperty("id") int id, @JsonProperty("title") String title,
        @JsonProperty("price") double price, @JsonProperty("artist") String artist,
        @JsonProperty("speed") int speed, @JsonProperty("size") int size, 
        @JsonProperty("sList") ArrayList<Song> sList){
        this.id = id;
        this.title = title;
        this.price = price;
        this.artist = artist;
        this.speed = speed;
        this.size = size;
        this.sList = sList;
    }

    /**
     * Retrieves the id of the vinyl
     * @return the id of the vinyl
     */
    public int getId() {
        return id;
    }

    /**

     * Retrieves the title of the Vinyl
     * @return The title of the Vinyl
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the price of the Vinyl
     * @return The price of the Vinyl
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the album
     * @param price the price of the album
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the quantity of the Vinyl 
     * @return the quantity available of Vinyl
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of Vinyl
     * @param quantity Quantity of Vinyl
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the artist of the album
     * @return the album artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Retrieves the size of the Vinyl
     * @return The size of the Vinyl
     */
    public int getSize() {
        return size;
    }

    public int getSpeed(){return speed;}

    /*public File getCoverImage() {
        return coverImage;
    }*/

    /**
     * Retrieves the Song list of the Vinyl
     * @return The sList of the Vinyl
     */
    public ArrayList<Song> getsList() {
        return sList;
    }
    
    /**
     * Returns cover image
     * @return cover image path
     */
    public String getCoverImagePath() {
        return coverImagePath;
    }
    
    /**
     * 
     * @return total duration of Vinyl
     */
    public Duration getTotal_duration() {
        return total_duration;
    }

    /**
     * 
     * @param duration the duration to be added or subtracted
     * @param plusOrMinus bool to tell if added or subtracted
     */
    public void setTotal_duration(Duration duration, Boolean plusOrMinus) {
        if(plusOrMinus == true){
            this.total_duration = this.total_duration.plus(duration);
        }
        else if(plusOrMinus == false){
            this.total_duration = this.total_duration.minus(duration);
        }
        
    }

    /**
     * toString method for formatting
     */
    @Override
    public String toString() {
        return "Vinyl [artist=" + artist + ", id=" + id + ", price=" + price + ", size=" + size + ", title=" + title
                + ", Songs=" + sList + "]";
    }

    

}
