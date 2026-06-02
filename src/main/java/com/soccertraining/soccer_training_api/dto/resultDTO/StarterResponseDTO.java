package com.soccertraining.soccer_training_api.dto.resultDTO;

import lombok.Data;

@Data
public class StarterResponseDTO {
    private Long userId;

    private String userName;

    private Double averageScore;
}
