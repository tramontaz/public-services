package net.chemodurov.publicservices.model.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DataFromApplicationPage {
    private String id;
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private Long phoneNumber;
    private Date dob;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
}