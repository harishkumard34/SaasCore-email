package com.techpuram.saascore.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.ModulesLayoutsRepo;
import com.techpuram.saascore.db.ModulesRepo;
import com.techpuram.saascore.entity.modules.Modules;
import com.techpuram.saascore.entity.modules.ModulesLayouts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModulesService {

    private final ModulesRepo modulesRepo;
    private final ModulesLayoutsRepo modulesLayoutsRepo;

    private final LayoutsService layoutsService;

    public List<Modules> getAllModules() {
        log.info("All modules are fetched");
        return modulesRepo.findAll();
    }

    public Map<String, Object> getModuleWithLayouts(Long moduleId) {
        List<ModulesLayouts> modulesLayouts = modulesLayoutsRepo.findAllByModuleId(moduleId);

        if (modulesLayouts.isEmpty()) {
            throw new RuntimeException("No layouts found for the given module: " + moduleId);
        }

        // Prepare the layout list with detailed sections
        List<Map<String, Object>> layouts = modulesLayouts.stream().map(ml -> {
            Map<String, Object> layoutDetails = layoutsService.getLayoutWithSections(ml.getLayout().getLayoutId());

            Map<String, Object> layoutMap = new LinkedHashMap<>();
            layoutMap.put("layout_details", layoutDetails);
            layoutMap.put("layout_order", ml.getLayoutOrder());
            layoutMap.put("created_by", ml.getCreatedBy());
            layoutMap.put("modified_by", ml.getModifiedBy());
            layoutMap.put("created_time", ml.getCreatedTime());
            layoutMap.put("modified_time", ml.getModifiedTime());

            return layoutMap;
        }).collect(Collectors.toList());

        Modules module = modulesLayouts.get(0).getModule();
        // Construct the final response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("module_name", module.getModuleName());
        response.put("plural_name", module.getPluralName());
        response.put("singular_name", module.getSingularName());
        response.put("system_defined", module.getSystemDefined());
        response.put("is_disabled", module.getIsDisabled());
        response.put("table_name", module.getTableName());
        response.put("created_time", module.getCreatedTime());
        response.put("modified_time", module.getModifiedTime());
        response.put("created_by", module.getCreatedBy());
        response.put("modified_by", module.getModifiedBy());
        response.put("layouts", layouts);

        return response;
    }

    public Modules getModulesById(Long id) {
        log.info("Modules with id {} is fetched", id);
        return modulesRepo.findById(id).orElse(null);
    }

    public List<Modules> getModulesByName(String moduleName) {
        List<Modules> moduleList = new java.util.ArrayList<>();

        List<Modules> modules = modulesRepo.findAll();

        for (Modules module : modules) {
            if (module.getModuleName().equals(moduleName)) {
                log.info("Modules with name {} is fetched", moduleName);
                moduleList.add(module);
            } else {
                throw new RuntimeException("module " + moduleName + " not found");
            }
        }

        return moduleList;
    }

}