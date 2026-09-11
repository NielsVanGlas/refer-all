package com.niels.referall.dto.authentication;


import java.io.Serializable;

public record OtpResponseDto(String token) implements Serializable {

}