package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.persistence.VinylFileDAO;

public class Account {
    private static final Logger LOG = Logger.getLogger(Vinyl.class.getName());
    
    @JsonProperty("id") protected int id;
    @JsonProperty("username") protected String username;
    @JsonProperty("password") protected String password;
    @JsonProperty("cart") private ArrayList<Integer> cart ;
    private ArrayList<Vinyl> vinyles ;

    public Account(@JsonProperty("id") int id, 
                   @JsonProperty("username") String username,
                   @JsonProperty("password") String password,
                   @JsonProperty("cart") ArrayList<Integer> vinyles_int) throws IOException
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cart = vinyles_int ;
        
        this.vinyles = new ArrayList<Vinyl>() ; //initialize a list of vinyl objects from the cart (list of vinyl IDs)
        for (int i = 0; i < cart.size(); i++) {
            int vinyl_id = cart.get(i) ;
            try {
                Vinyl vinyl = new VinylFileDAO("./data/vinyl.json", new ObjectMapper()).getVinyl(vinyl_id) ;
                vinyles.add(vinyl) ;
            } catch (Exception e) {
                throw e ;
            }
        }
    }

    public int getId() {return id;}

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public void setUsername(String username) {this.username = username;}

    public void setPassword(String password) {this.password = password;}

    public ArrayList<Integer> getCart() {return cart;}

    protected ArrayList<Vinyl> getCartObjects() {return vinyles;}

    /**
     * Calculates the total cost of all the items in the cart
     * @return The total cost of the cart
     */
    public double generateTotalCost() {
        double total_cost = 0.0 ;

        for (int i = 0; i < vinyles.size(); i++ ) {
            double vinyl_cost = vinyles.get(i).getPrice() ;

            total_cost += vinyl_cost ;
        }
        
        return total_cost ;
    } 
}
