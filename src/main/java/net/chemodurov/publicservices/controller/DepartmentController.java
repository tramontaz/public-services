package net.chemodurov.publicservices.controller;

import lombok.extern.slf4j.Slf4j;
import net.chemodurov.publicservices.model.Department;
import net.chemodurov.publicservices.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public ResponseEntity getAll() {
        List<Department> result = departmentService.getAll();

        if (result != null && result.size() > 0) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(result);
        } else {
            log.warn("Couldn't found any departments.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addDepartment")
    public ResponseEntity addDeparnment(@RequestBody Department department) {
        Department result = departmentService.saveDepartment(department);
        if (result != null) {
            log.info("Department successful saved. {}", result.toString());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(result);
        } else {
            log.warn("Couldn't saved the department: {}", department.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
