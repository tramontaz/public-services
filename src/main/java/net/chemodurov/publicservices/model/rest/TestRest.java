package net.chemodurov.publicservices.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestRest {
    private String name;
    private String email;
    private String subject;
    private String message;
}
