package com.emreuzun.garage.application.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class ApiResponseDto {

    private String message;

}
