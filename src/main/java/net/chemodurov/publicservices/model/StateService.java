package net.chemodurov.publicservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateService {
    @Id
    private String id;
    private Integer depCode;
    private String name;
}
