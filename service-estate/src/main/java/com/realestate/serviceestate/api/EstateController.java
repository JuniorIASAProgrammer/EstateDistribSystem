package com.realestate.serviceestate.api;

import com.realestate.serviceestate.api.dto.EstateDto;
import com.realestate.serviceestate.repo.model.Estate;
import com.realestate.serviceestate.service.EstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("EstateMarketplace/estate")
public class EstateController {

    private final EstateService estateService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Estate>> getAll() {
        final List<Estate> estateList = estateService.fetchAll();
        return ResponseEntity.ok(estateList);
    }

    @GetMapping("/getById/id={id}")
    public ResponseEntity<Estate> getById(@PathVariable long id){
        try {
            final Estate estate = estateService.fetchById(id);
            return ResponseEntity.ok(estate);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody EstateDto estate){
        long id = estateService.create(estate);
        String location = String.format("/estate/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/update/id={id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody EstateDto estate){
        try{
            estateService.update(id, estate);
            return new ResponseEntity<>("Information updated", HttpStatus.ACCEPTED);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/id={id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        estateService.delete(id);
        return new ResponseEntity<>("Profile deleted", HttpStatus.OK);
    }
}
