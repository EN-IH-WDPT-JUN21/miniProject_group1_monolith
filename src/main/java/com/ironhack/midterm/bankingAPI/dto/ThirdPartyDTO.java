package com.ironhack.midterm.bankingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyDTO {
    @NotNull(message="username cannot be empty")
    private String username;
    @NotNull(message="password cannot be empty")
    private String password;
    @NotNull(message="name cannot be empty")
    private String name;
    @NotNull(message="key cannot be empty")
    private String key;
}
