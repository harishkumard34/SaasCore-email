package com.techpuram.saascore.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.LayoutSectionRepo;
import com.techpuram.saascore.db.LayoutsRepo;
import com.techpuram.saascore.entity.modules.Layouts;
import com.techpuram.saascore.entity.modules.LayoutsSections;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LayoutsService {

    private final LayoutsRepo layoutRepo;
    private final LayoutSectionRepo layoutSectionRepo;

    private final SectionsService sectionService;

    public List<Layouts> getAllLayouts() {
        log.info("All layouts are fetched");
        return layoutRepo.findAll();
    }

    public Map<String, Object> getLayoutWithSections(Long layoutId) {
        List<LayoutsSections> layoutsSections = layoutSectionRepo.findAllByLayoutId(layoutId);

        if (layoutsSections.isEmpty()) {
            throw new RuntimeException("No sections found for the given layout: " + layoutId);
        }

        
        // Prepare the section list with detailed fields
        List<Map<String, Object>> sections = layoutsSections.stream().map(ls -> {
            Map<String, Object> sectionDetails = sectionService.getSectionWithFields(ls.getSection().getSectionId());
            Map<String, Object> sectionMap = new LinkedHashMap<>();
            // Add order information from LayoutsSections
            sectionMap.put("section_details", sectionDetails);
            sectionMap.put("section_order", ls.getSectionOrder());
            sectionMap.put("created_by", ls.getCreatedBy());
            sectionMap.put("modified_by", ls.getModifiedBy());
            sectionMap.put("created_time", ls.getCreatedTime());
            sectionMap.put("modified_time", ls.getModifiedTime());
            return sectionMap;
        }).collect(Collectors.toList());
        
        // Extract the layout from the first record
        Layouts layout = layoutsSections.get(0).getLayout();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("layout_id", layout.getLayoutId());
        response.put("layout_name", layout.getLayoutName());
       // response.put("owner", layout.getOwner());
        response.put("created_time", layout.getCreatedTime());
        response.put("modified_time", layout.getModifiedTime());
        response.put("created_by", layout.getCreatedBy());
        response.put("modified_by", layout.getModifiedBy());
        response.put("sections", sections);

        return response;
    }

    public Layouts getLayoutById(Long id) {
        log.info("Layout with id {} is fetched", id);
        return layoutRepo.findById(id).orElse(null);
    }

    public Layouts createLayout(Layouts layout) {
        layout.setCreatedTime(System.currentTimeMillis());
        layout.setModifiedTime(System.currentTimeMillis());

        log.info("Layout with id {} is created", layout.getLayoutId());
        return layoutRepo.save(layout);
    }

    public List<Layouts> updateLayout(Layouts layout) {
        layout.setModifiedTime(System.currentTimeMillis());

        log.info("Layout with id {} is updated", layout.getLayoutId());
        return layoutRepo.saveAll(List.of(layout));
    }

    public void deleteLayout(Long id) {
        layoutRepo.deleteById(id);

        log.info("Layout with id {} is deleted", id);
    }

    public void deleteAllLayouts() {
        layoutRepo.deleteAll();

        log.info("All layouts are deleted");
    }
}
