package com.soccertraining.soccer_training_api.dto.resultDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResultRequestDTO {

    @NotNull(message = "Obligatorio poner potencia de tiro")
    private Double shotPower;

    @NotNull(message = "Obligatorio poner la velocidad del jugador")
    private Double speed;

    @NotNull(message = "Obligatorio poner la cantidad de pase acertados")
    private Double passes;

    @NotNull(message = "Jugador obligatorio")
    private Long userId;

    @NotNull(message = "Entrenamiento obligatorio")
    private Long trainingId;


}
