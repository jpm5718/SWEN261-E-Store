package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.persistence.VinylDAO;
import com.estore.api.estoreapi.model.Song;
import com.estore.api.estoreapi.model.Vinyl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Vinyl Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class VinylControllerTest {
    private VinylController VinylController;
    private VinylDAO mockVinylDAO;

    /**
     * Before each test, create a new VinylController object and inject
     * a mock Vinyl DAO
     */
    @BeforeEach
    public void setupVinylController() {
        mockVinylDAO = mock(VinylDAO.class);
        VinylController = new VinylController(mockVinylDAO);
    }

    @Test
    public void testGetVinyl() throws IOException {  // getVinyl may throw IOException
        // Setup
        Vinyl Vinyl = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );;
        // When the same id is passed in, our mock Vinyl DAO will return the Vinyl object
        when(mockVinylDAO.getVinyl(Vinyl.getId())).thenReturn(Vinyl);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.getVinyl(Vinyl.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Vinyl,response.getBody());
    }

    @Test
    public void testGetVinylNotFound() throws Exception { // createVinyl may throw IOException
        // setup
        int id = 0;
        // our mock Vinyl DAO will return null, simulating
        // no Vinyls found
        when(mockVinylDAO.getVinyl(id)).thenReturn(null);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.getVinyl(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetVinylHandleException() throws Exception { // createVinyl may throw IOException
        // Setup
        int VinylId = 99;
        // When getVinyl is called on the Mock Vinyl DAO, throw an IOException
        doThrow(new IOException()).when(mockVinylDAO).getVinyl(VinylId);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.getVinyl(VinylId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all VinylController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateVinyl() throws IOException {  // createVinyl may throw IOException
        // Setup
        Vinyl Vinyl = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // when createVinyl is called, return true simulating successful
        // creation and save
        when(mockVinylDAO.createVinyl(Vinyl)).thenReturn(Vinyl);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.createVinyl(Vinyl);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(Vinyl,response.getBody());
    }

    @Test
    public void testCreateVinylFailed() throws IOException {  // createVinyl may throw IOException
        // Setup
        Vinyl Vinyl = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // when createVinyl is called, return false simulating failed
        // creation and save
        when(mockVinylDAO.createVinyl(Vinyl)).thenReturn(null);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.createVinyl(Vinyl);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateVinylHandleException() throws IOException {  // createVinyl may throw IOException
        // Setup
        Vinyl Vinyl = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );

        // When createVinyl is called on the Mock Vinyl DAO, throw an IOException
        doThrow(new IOException()).when(mockVinylDAO).createVinyl(Vinyl);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.createVinyl(Vinyl);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateVinyl() throws IOException { // updateVinyl may throw IOException
        // Setup
        Vinyl Vinyl = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // when updateVinyl is called, return true simulating successful
        // update and save
        when(mockVinylDAO.updateVinyl(Vinyl)).thenReturn(Vinyl);
        ResponseEntity<Vinyl> response = VinylController.updateVinyl(Vinyl);

        // Invoke
        response = VinylController.updateVinyl(Vinyl);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Vinyl,response.getBody());
    }

    @Test
    public void testUpdateVinylFailed() throws IOException { // updateVinyl may throw IOException
        // Setup
        Vinyl Vinyl = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // when updateVinyl is called, return true simulating successful
        // update and save
        when(mockVinylDAO.updateVinyl(Vinyl)).thenReturn(null);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.updateVinyl(Vinyl);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateVinylHandleException() throws IOException { // updateVinyl may throw IOException
        // Setup
        Vinyl Vinyl = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // When updateVinyl is called on the Mock Vinyl DAO, throw an IOException
        doThrow(new IOException()).when(mockVinylDAO).updateVinyl(Vinyl);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.updateVinyl(Vinyl);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetVinyles() throws IOException { // getVinyles may throw IOException
        // Setup
        Vinyl[] Vinyles = new Vinyl[2];
        Vinyles[0] = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        Vinyles[1] = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // When getVinyles is called return the Vinyles created above
        when(mockVinylDAO.getVinyles()).thenReturn(Vinyles);

        // Invoke
        ResponseEntity<Vinyl[]> response = VinylController.getAllVinyl();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Vinyles,response.getBody());
    }
    
    @Test
    public void testGetAllVinylNotFound() throws Exception { // createVinyl may throw IOException
        // our mock Vinyl DAO will return null, simulating
        // no Vinyls found
        when(mockVinylDAO.getVinyles()).thenReturn(null);

        // Invoke
        ResponseEntity<Vinyl[]> response = VinylController.getAllVinyl();

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetVinylesHandleException() throws IOException { // getVinyles may throw IOException
        // Setup
        // When getVinyles is called on the Mock Vinyl DAO, throw an IOException
        doThrow(new IOException()).when(mockVinylDAO).getVinyles();

        // Invoke
        ResponseEntity<Vinyl[]> response = VinylController.getAllVinyl();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchVinyles() throws IOException { // findVinyles may throw IOException
        // Setup
        String searchString = "la";
        Vinyl[] Vinyles = new Vinyl[2];
        Vinyles[0] = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        Vinyles[1] = new Vinyl (2,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // When findVinyles is called with the search string, return the two
        /// Vinyles above
        when(mockVinylDAO.findVinyles(searchString)).thenReturn(Vinyles);

        // Invoke
        ResponseEntity<Vinyl[]> response = VinylController.searchVinyl(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(Vinyles,response.getBody());
    }

    @Test
    public void testSearchVinylesNotFound() throws IOException { // findVinyles may throw IOException
        // Setup
        String searchString = "la";
        Vinyl[] Vinyles = new Vinyl[2];
        Vinyles[0] = new Vinyl (1,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        Vinyles[1] = new Vinyl (2,"TITLE",1.11,"ARTIST",1,1,new ArrayList<Song>() );
        // When findVinyles is called with the search string, return the two
        /// Vinyles above
        when(mockVinylDAO.findVinyles(searchString)).thenReturn(null);

        // Invoke
        ResponseEntity<Vinyl[]> response = VinylController.searchVinyl(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    @Test
    public void testSearchVinylesHandleException() throws IOException { // findVinyles may throw IOException
        // Setup
        String searchString = "an";
        // When createVinyl is called on the Mock Vinyl DAO, throw an IOException
        doThrow(new IOException()).when(mockVinylDAO).findVinyles(searchString);

        // Invoke
        ResponseEntity<Vinyl[]> response = VinylController.searchVinyl(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteVinyl() throws IOException { // deleteVinyl may throw IOException
        // Setup
        int VinylId = 99;
        // when deleteVinyl is called return true, simulating successful deletion
        when(mockVinylDAO.deleteVinyl(VinylId)).thenReturn(true);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.deleteVinyl(VinylId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteVinylNotFound() throws IOException { // deleteVinyl may throw IOException
        // Setup
        int VinylId = 99;
        // when deleteVinyl is called return false, simulating failed deletion
        when(mockVinylDAO.deleteVinyl(VinylId)).thenReturn(false);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.deleteVinyl(VinylId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteVinylHandleException() throws IOException { // deleteVinyl may throw IOException
        // Setup
        int VinylId = 99;
        // When deleteVinyl is called on the Mock Vinyl DAO, throw an IOException
        doThrow(new IOException()).when(mockVinylDAO).deleteVinyl(VinylId);

        // Invoke
        ResponseEntity<Vinyl> response = VinylController.deleteVinyl(VinylId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}