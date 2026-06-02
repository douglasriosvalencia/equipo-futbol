package com.soccertraining.soccer_training_api.controller;

import com.soccertraining.soccer_training_api.dto.resultDTO.StarterResponseDTO;
import com.soccertraining.soccer_training_api.dto.trainigDTO.TrainigRequestDTO;
import com.soccertraining.soccer_training_api.dto.trainigDTO.TrainingResponseDTO;
import com.soccertraining.soccer_training_api.exception.HttpGlobalResponse;
import com.soccertraining.soccer_training_api.service.crud.ResultService;
import com.soccertraining.soccer_training_api.service.crud.TrainigService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@AllArgsConstructor
public class TrainingController {
    private final TrainigService trainingService;

    private final ResultService resultService;
    //metodo de crar----------------------------------------------------------
    @PostMapping
    public ResponseEntity<HttpGlobalResponse<TrainingResponseDTO>> createTraining(@Valid @RequestBody TrainigRequestDTO requestDTO){

        TrainingResponseDTO training = trainingService.createTrainig(requestDTO);

        HttpGlobalResponse<TrainingResponseDTO> response = new HttpGlobalResponse<>();

        response.setData(training);
        response.setMessage("Entrenamiento creado correctamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //metodo de obtener------------------------------------------------------
    @GetMapping("/starters/{week}")
    public ResponseEntity<HttpGlobalResponse<List<StarterResponseDTO>>> getStarters(@PathVariable Integer week){

        List<StarterResponseDTO> starters = resultService.getStarters(week);

        HttpGlobalResponse<List<StarterResponseDTO>> response = new HttpGlobalResponse<>();

        response.setData(starters);
        response.setMessage("Titulares obtenidos correctamente");

        return ResponseEntity.ok(response);
    }
}
