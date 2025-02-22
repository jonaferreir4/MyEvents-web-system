package com.pds.my_events.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO    {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String cpf;
    private int enrollment;
    private String email;
}
