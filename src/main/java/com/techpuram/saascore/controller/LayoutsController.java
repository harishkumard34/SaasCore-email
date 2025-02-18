package com.techpuram.saascore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.techpuram.saascore.entity.modules.Layouts;
import com.techpuram.saascore.service.LayoutsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/layouts")
public class LayoutsController {

    @Autowired
    private final LayoutsService sectionService;

    /**
     * Returns a list of all layouts
     * URL: localhost:8080/v1/layouts/{id}
     *
     * @return a list of Layouts objects
     */

    @GetMapping
    public ResponseEntity<?> getLayouts() {
        try {
            return ResponseEntity.ok().body(sectionService.getAllLayouts());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    /**
     * Retrieves a layout along with its associated sections based on the given
     * layout ID.
     * URL: localhost:8080/v1/layouts/{id}
     *
     * @param layoutId the ID of the layout to retrieve
     * @return a ResponseEntity containing a map with layout and section details
     */

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getLayoutWithSections(@PathVariable("id") Long layoutId) {
        try {
            return ResponseEntity.ok().body(sectionService.getLayoutWithSections(layoutId));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    /**
     * Creates a new layout.
     * URL: localhost:8080/v1/layouts/{id}
     *
     * @param section the Layouts object to be created
     * @return a ResponseEntity containing the created Layouts object
     */
    @PostMapping
    public ResponseEntity<Layouts> createLayout(@RequestBody Layouts section) {
        return ResponseEntity.ok().body(sectionService.createLayout(section));
    }

    /**
     * Updates an existing layout with the given details.
     * URL: localhost:8080/v1/layouts
     *
     * @param section the Layouts object to be updated
     * @return a ResponseEntity containing the updated Layouts object
     */
    @PutMapping
    public ResponseEntity<List<Layouts>> updateLayout(@RequestBody Layouts section) {
        return ResponseEntity.ok().body(sectionService.updateLayout(section));
    }

    /**
     * Deletes the layout with the given ID.
     * URL: localhost:8080/v1/layouts/{id}
     *
     * @param id the ID of the layout to delete
     * @return a ResponseEntity indicating the deletion result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLayout(@PathVariable("id") Long id) {
        sectionService.deleteLayout(id);
        return ResponseEntity.ok().build();
    }
}