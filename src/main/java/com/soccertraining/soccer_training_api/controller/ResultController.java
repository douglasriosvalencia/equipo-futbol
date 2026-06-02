package com.soccertraining.soccer_training_api.controller;

import com.soccertraining.soccer_training_api.dto.resultDTO.ResultRequestDTO;
import com.soccertraining.soccer_training_api.dto.resultDTO.ResultResponseDTO;
import com.soccertraining.soccer_training_api.exception.HttpGlobalResponse;
import com.soccertraining.soccer_training_api.service.crud.ResultService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    /**
     * Registrar resultado de un jugador
     * en un entrenamiento.
     */
    @PostMapping
    public ResponseEntity<HttpGlobalResponse<ResultResponseDTO>> createResult(@Valid @RequestBody ResultRequestDTO requestDTO){

        ResultResponseDTO result = resultService.create(requestDTO);

        HttpGlobalResponse<ResultResponseDTO> response = new HttpGlobalResponse<>();

        response.setData(result);
        response.setMessage("Resultado registrado correctamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consultar resultados de un jugador.
     */
    @GetMapping("/player/{userId}")
    public ResponseEntity<HttpGlobalResponse<List<ResultResponseDTO>>> getResultsByPlayer(@PathVariable Long userId){

        List<ResultResponseDTO> results = resultService.getResultsByPlayer(userId);

        HttpGlobalResponse<List<ResultResponseDTO>> response = new HttpGlobalResponse<>();

        response.setData(results);
        response.setMessage("Resultados obtenidos correctamente");

        return ResponseEntity.ok(response);
    }
}
