package com.techpuram.saascore.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpuram.saascore.entity.modules.Fields;
import com.techpuram.saascore.service.FieldsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/fields")
@RequiredArgsConstructor
@Validated

public class FieldsController {

    private final FieldsService fieldsService;


    /**
     * Fetches all the fields
     * URL: localhost:8080/v1/fields
     *
     * @return List of all fields
     */
    @GetMapping
    public ResponseEntity<List<Fields>> getAllfields(){
        return ResponseEntity.ok().body(fieldsService.getAllFields());
    }

    /**
     * Fetches a  by ID.
     * URL: localhost:8080/v1/fields/{id}
     *
     * @param id - fields ID
     * @return Fields with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fields> getFieldsByID(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(fieldsService.getFieldsByID(id));
    }

    /**
     * Creates a new Fields
     * URL: localhost:8080/v1/fields
     *
     * @param fields - Fields object to be created
     * @return Created Fields
     */


    //  @PostMapping
    //  public ResponseEntity<Fields> createFields(@RequestBody Fields fields)
    //  {
    //      return ResponseEntity.ok().body(fieldsService.createFields(fields));
    //  }
 

    @PostMapping
    public ResponseEntity<List<Fields>> createFields(@RequestBody List<Fields> fieldsList) {
        return ResponseEntity.ok(fieldsService.createFields(fieldsList));
    }



    /**
     * Updates an existing Fields
     * URL: localhost:8080/v1/fields
     *
     * @param fields - Fields object to be updated
     * @return Updated Fields
     */
    @PutMapping
    public ResponseEntity<List<Fields>> updateFields(@RequestBody Fields fields)
    {
        return ResponseEntity.ok().body(fieldsService.updateFields(fields));
    }



    /**
     * Deletes a Fields entity by ID.
     * URL: localhost:8080/v1/fields/{id}
     *
     * @param id - ID of the Fields to be deleted
     * @return ResponseEntity with status OK if deletion is successful
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFields(@PathVariable("id") Long id) {
        fieldsService.deleteFields(id);
        return ResponseEntity.ok().build();
    }


    /**
     * Deletes all Fields entities from the database.
     * URL: localhost:8080/v1/fields
     *
     * @return ResponseEntity with status OK if deletion is successful
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAllFields() {
        fieldsService.deleteAllFields();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/label")
public ResponseEntity<Fields> updateFieldLabel(@PathVariable("id") Long id, @RequestBody String newLabel) {
    Fields updatedField = fieldsService.updateFieldLabel(id, newLabel);
    return ResponseEntity.ok(updatedField);
}


}


