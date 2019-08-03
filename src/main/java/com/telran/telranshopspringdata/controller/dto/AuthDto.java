package com.telran.telranshopspringdata.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//dto для регистрации нашего пользователя
//для ДОБАВЛЕНИЯ
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthDto {
    private String email;
    private String password;
}
