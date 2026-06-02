package com.soccertraining.soccer_training_api.dto.trainigDTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TrainingResponseDTO {

    private Long id;


    private Integer weekNumber;


    private Integer trainingNumber;


    private LocalDateTime trainingDate;
}
