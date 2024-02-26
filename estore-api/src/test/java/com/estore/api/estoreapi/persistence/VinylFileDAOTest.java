package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Song;
import com.estore.api.estoreapi.model.Vinyl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Vinyl File DAO class
 * 
 * @author SWEN Faculty - altered by J Russ
 */
@Tag("Persistence-tier")
public class VinylFileDAOTest {
    VinylFileDAO vinylFileDAO;
    Vinyl[] testVinyls;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupVinylFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testVinyls = new Vinyl[3];
        testVinyls[0] = new Vinyl(1,"One",1.0,"one",1,1,new ArrayList<Song>());
        testVinyls[1] = new Vinyl(2,"Two",2.0,"two",2,2,new ArrayList<Song>());
        testVinyls[2] = new Vinyl(3,"Three",3.0,"three",3,3,new ArrayList<Song>());

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the vinyl array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Vinyl[].class))
                .thenReturn(testVinyls);
        vinylFileDAO = new VinylFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetVinyls() {
        // Invoke
        Vinyl[] vinyls = vinylFileDAO.getVinyles();

        // Analyze
        assertEquals(testVinyls.length,vinyls.length);
        for (int i = 0; i < testVinyls.length;i++)
            assertEquals(vinyls[i],testVinyls[i]);
    }

    @Test
    public void testFindVinyls() {
        // Invoke
        try {
            Vinyl[] vinyls = vinylFileDAO.findVinyles("One");

            // Analyze
            assertEquals(1,vinyls.length);
            assertEquals(vinyls[0],testVinyls[0]);

        } catch (Exception e) {
            assertNotNull(null);
        }

    }

    @Test
    public void testGetVinyl() {
        // Invoke
        Vinyl vinyl = vinylFileDAO.getVinyl(1);

        // Analzye
        assertEquals(vinyl,testVinyls[0]);
    }

    @Test
    public void testDeleteVinyl() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> vinylFileDAO.deleteVinyl(1),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test vinyls array - 1 (because of the delete)
        // Because vinyls attribute of VinylFileDAO is package private
        // we can access it directly
        assertEquals(vinylFileDAO.Vinyles.size(),testVinyls.length-1);
    }

    @Test
    public void testCreateVinyl() {
        try {
            // Setup
            Vinyl vinyl = new Vinyl(4,"Four",4.0,"four",4,4,new ArrayList<Song>());

            // Invoke
            Vinyl result = assertDoesNotThrow(() -> vinylFileDAO.createVinyl(vinyl),
                                    "Unexpected exception thrown");

            // Analyze
            assertNotNull(result);
            Vinyl actual = vinylFileDAO.getVinyl(vinyl.getId());
            assertEquals(actual.getId(),vinyl.getId());
            assertEquals(actual.getTitle(),vinyl.getTitle());

        } catch (Exception e) {
            assertNotNull(null);
        }
    }

    @Test
    public void testUpdateVinyl() {

        try {
            // Setup
            Vinyl vinyl = new Vinyl(2,"Four",4.0,"four",4,4,new ArrayList<Song>());

            // Invoke
            Vinyl result = assertDoesNotThrow(() -> vinylFileDAO.updateVinyl(vinyl),
                                    "Unexpected exception thrown");

            // Analyze
            assertNotNull(result);
            Vinyl actual = vinylFileDAO.getVinyl(vinyl.getId());
            assertEquals(actual,vinyl);

        } catch (Exception e) {
            assertNotNull(null);
        }
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Vinyl[].class));

        Vinyl vinyl = new Vinyl(5,"Five",5.0,"five",5,5,new ArrayList<Song>());

        assertThrows(IOException.class,
                        () -> vinylFileDAO.createVinyl(vinyl),
                        "IOException not thrown");
    }

    @Test
    public void testGetVinylNotFound() {
        // Invoke
        Vinyl vinyl = vinylFileDAO.getVinyl(98);

        // Analyze
        assertEquals(vinyl,null);
    }

    @Test
    public void testDeleteVinylNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> vinylFileDAO.deleteVinyl(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(vinylFileDAO.Vinyles.size(),testVinyls.length);
    }

    @Test
    public void testUpdateVinylNotFound() {
        // Setup

        try {
            Vinyl vinyl = new Vinyl(92,"Four",4.0,"four",4,4,new ArrayList<Song>());

            // Invoke
            Vinyl result = assertDoesNotThrow(() -> vinylFileDAO.updateVinyl(vinyl),
                                                    "Unexpected exception thrown");

            // Analyze
            assertNull(result);
            
        } catch (Exception e) {
            assertNotNull(null);
        }
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the VinylFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Vinyl[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new VinylFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}

