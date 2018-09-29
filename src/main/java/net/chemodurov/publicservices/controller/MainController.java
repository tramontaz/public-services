package net.chemodurov.publicservices.controller;

import net.chemodurov.publicservices.model.rest.DataFromApplicationPage;
import net.chemodurov.publicservices.service.AppPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/applications")
public class MainController {
    private final AppPageService appPageService;

    @Autowired
    public MainController(AppPageService appPageService) {
        this.appPageService = appPageService;
    }

    @GetMapping(value = "/")
    public String homepage(){
        return "index";
    }

    @GetMapping("/getAll")
    public Map<String, Object> getAll() {
        return appPageService.getAll();
    }

    @PostMapping("/save")
    public Map<String, String> save(@RequestBody DataFromApplicationPage data) {
        return appPageService.validateAndSave(data);
    }

    @GetMapping("/getPages")
    public Map<String, Object> getAllByPages(@RequestParam("draw") int draw,
                                             @RequestParam("start") int start,
                                             @RequestParam("length") int length) {
        Map<String, Object> request = new HashMap<>();
        request.put("draw", draw);
        request.put("start", start);
        request.put("length", length);
        return appPageService.getAllByPages(request);
    }
}
