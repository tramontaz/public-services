package net.chemodurov.publicservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    private String id;
    private StateService stateService;
    private Declarant declarant;
    private Department department;
    private Date date;
}
