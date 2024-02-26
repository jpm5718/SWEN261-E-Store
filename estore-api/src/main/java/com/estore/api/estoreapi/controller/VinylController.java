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

import com.estore.api.estoreapi.model.Vinyl;
import com.estore.api.estoreapi.persistence.VinylDAO;


@RestController
@RequestMapping("vinyl")
public class VinylController {
    private static final Logger LOG = Logger.getLogger(Vinyl.class.getName());
    private VinylDAO vinylDAO;

    public VinylController(VinylDAO vinylDAO){
        this.vinylDAO = vinylDAO;
    }
    @GetMapping("")
    public ResponseEntity<Vinyl[]> getAllVinyl() {
        LOG.info("GET /vinyl");
        try {
            Vinyl[] vinyls = vinylDAO.getVinyles();
            if(vinyls != null)
                return new ResponseEntity<>(vinyls, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Vinyl> updateVinyl(@RequestBody Vinyl vinyl) {
        LOG.info("PUT /vinyl " + vinyl);
        try {
            Vinyl updatedVinyl = vinylDAO.updateVinyl(vinyl);
            if(updatedVinyl != null)
                return new ResponseEntity<Vinyl>(updatedVinyl,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<Vinyl> createVinyl(@RequestBody Vinyl vinyl){
        LOG.info("POST /vinyl " + vinyl);
        
        try {
            Vinyl newVinyl = vinylDAO.createVinyl(vinyl);
            if(newVinyl != null){
                return new ResponseEntity<Vinyl>(newVinyl, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Vinyl[]> searchVinyl(@RequestParam String searchKey) {
        LOG.info("Get /vinyl/?searchKey="+searchKey);
        try {
            Vinyl[] vinyls = vinylDAO.findVinyles(searchKey);
            if (vinyls != null) {
                return new ResponseEntity<Vinyl[]>(vinyls, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vinyl> getVinyl(@PathVariable int id){
        LOG.info("GET /vinyl/" + id);
        try{
            Vinyl vinyl = vinylDAO.getVinyl(id);
            if(vinyl != null)
                return new ResponseEntity<Vinyl>(vinyl, HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            LOG.info(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Vinyl> deleteVinyl(@PathVariable int id){
        LOG.info("DELETE /vinyl/" + id);

        try{
            boolean result = vinylDAO.deleteVinyl(id);
            if(result)
                return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
