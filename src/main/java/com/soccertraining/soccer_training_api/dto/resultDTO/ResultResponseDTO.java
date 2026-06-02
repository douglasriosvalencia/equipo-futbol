package com.soccertraining.soccer_training_api.dto.resultDTO;

import lombok.Data;

@Data
public class ResultResponseDTO {

    private Long id;

    private Double shotPower;

    private Double speed;

    private Double passes;

    private Double score;

    private String userName;

    private Integer trainingNumber;

    private Integer weekNumber;
}
