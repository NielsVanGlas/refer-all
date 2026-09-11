package com.niels.referall.dto.authentication;


import java.io.Serializable;

public record RefreshResponseDto(String accessToken) implements Serializable {

}