package net.chemodurov.publicservices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Declarant {
    @Id
    private String id;
    private String surname;
    private String name;
    private String patronymic; //я сам офигел, но если верить гуглу, то это и есть перевод стова отчество
    private String email;
    private Long phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
}
