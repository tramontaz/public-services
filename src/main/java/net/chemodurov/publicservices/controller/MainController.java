package net.chemodurov.publicservices.controller;

import lombok.RequiredArgsConstructor;
import net.chemodurov.publicservices.model.rest.DataFromApplicationPage;
import net.chemodurov.publicservices.service.AppPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class MainController {
    private final AppPageService appPageService;

    @GetMapping(value = "/")
    public String homepage(){
        return "index";
    }

    @GetMapping("/getAll")
    public Map<String, Object> getAll() {
        return appPageService.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody DataFromApplicationPage data) {
        Map<String, String> result = appPageService.validateAndSave(data);
        if ("ok".equals(result.get("data")))
            return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(data);
        else {
            StringBuilder messageForUser = constructErrorMessage(data, result);
            return new ResponseEntity<>(messageForUser.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping("/getPages")
    public Map<String, Object> getAllByPages(@RequestParam("draw") int draw,
                                             @RequestParam("start") int start,
                                             @RequestParam("length") int length,
                                             @RequestParam("search[value]") String searchParam) {
        Map<String, Object> request = new HashMap<>();
        request.put("draw", draw);
        request.put("start", start);
        request.put("length", length);
        request.put("searchParam", searchParam);
        return appPageService.getAllByPages(request);
    }

    private StringBuilder constructErrorMessage(@RequestBody DataFromApplicationPage data, Map<String, String> result) {
        StringBuilder messageForUser = new StringBuilder();
        messageForUser.append("Проверьте данные перед отправкой!");
        if (!data.getId().equals(result.get("stateService")))
            messageForUser.append("\nПроверьте поле 'Услуга'");
        if (!data.getSurname().equals(result.get("surname")))
            messageForUser.append("\nПроверьте поле 'Фамилия'");
        if (!data.getName().equals(result.get("name")))
            messageForUser.append("\nПроверьте поле 'Имя'");
        if (!data.getPatronymic().equals(result.get("patronymic")))
            messageForUser.append("\nПроверьте поле 'Отчество'");
        if (!data.getEmail().equals(result.get("email")))
            messageForUser.append("\nПроверьте поле 'Email'");
        if (!String.valueOf(data.getPhoneNumber()).equals(result.get("phoneNumber")))
            messageForUser.append("\nПроверьте поле 'Номер телефона'");
        if (!String.valueOf(data.getDob().getTime()).equals(result.get("dob")))
            messageForUser.append("\nПроверьте поле 'Дата рождения'");
        return messageForUser;
    }
}
