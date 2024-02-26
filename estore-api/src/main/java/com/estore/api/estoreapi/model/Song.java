package com.estore.api.estoreapi.model;

import java.util.logging.Logger;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Song {
    private static final Logger LOG = Logger.getLogger(Vinyl.class.getName());

    @JsonProperty("title") protected String title;
    @JsonProperty("artist") protected String artist;
    @JsonProperty("songLink") protected String songLink;


//https://stackoverflow.com/questions/3046669/how-do-i-get-a-mp3-files-total-time-in-java
//This link is possible way to get duration from mp3 file. May use later. 

    public Song(@JsonProperty("title") String title, @JsonProperty("artist") String artist){
        this.title = title;
        this.artist = artist;
    }
    
    //Need to consturct for if file is added for 10%. Will not need duration for that. 
}


