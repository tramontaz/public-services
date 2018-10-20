package net.chemodurov.publicservices.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.chemodurov.publicservices.model.StateService;
import net.chemodurov.publicservices.service.StateServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@Slf4j
public class StateServiceController {
    private final StateServiceService service;

    @GetMapping("/")
    public ResponseEntity getAll() {
        List<StateService> result = service.getAll();

        if (result != null && result.size() > 0) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(result);
        } else {
            log.warn("Couldn't found any departments.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addService")
    public ResponseEntity addStateService(@RequestBody StateService stateService) {
        StateService result = service.saveDepartment(stateService);
        if (result != null) {
            log.info("StateService successful saved. {}", result.toString());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(result);
        } else {
            log.warn("Couldn't saved the stateService: {}", stateService.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
