package com.soccertraining.soccer_training_api.dto.trainigDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TrainigRequestDTO {

    @NotNull(message = "numero de semana obligatorio ")
    @Min(value = 1, message = "la semana debe ser mayor que cero")
    private Integer weekNumber;

    @NotNull(message = "numero de entreno obligatorio")
    @Min(value = 1, message = "el numero de entrenamiento debe ser mayor que cero")
    private Integer trainingNumber;

}
