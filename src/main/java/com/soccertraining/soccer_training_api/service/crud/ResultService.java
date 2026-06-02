package com.soccertraining.soccer_training_api.service.crud;

import com.soccertraining.soccer_training_api.dto.resultDTO.ResultRequestDTO;
import com.soccertraining.soccer_training_api.dto.resultDTO.ResultResponseDTO;
import com.soccertraining.soccer_training_api.dto.resultDTO.StarterResponseDTO;
import com.soccertraining.soccer_training_api.entity.PlayerTrainingResult;
import com.soccertraining.soccer_training_api.entity.Training;
import com.soccertraining.soccer_training_api.entity.User;
import com.soccertraining.soccer_training_api.exception.BusinessException;
import com.soccertraining.soccer_training_api.exception.DuplicateResourceException;
import com.soccertraining.soccer_training_api.exception.ResourceNotFoundException;
import com.soccertraining.soccer_training_api.mapper.ResultMapper;
import com.soccertraining.soccer_training_api.repository.PlayerTrainingResultRepository;
import com.soccertraining.soccer_training_api.repository.TrainingRepository;
import com.soccertraining.soccer_training_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ResultService {
    private final PlayerTrainingResultRepository resultRepository;

    private final UserRepository userRepository;

    private final TrainingRepository trainingRepository;

    /**
     * Registra el resultado de un jugador
     * para un entrenamiento específico.
     * Flujo:
     * 1. Validar que no exista resultado previo.
     * 2. Buscar jugador.
     * 3. Buscar entrenamiento.
     * 4. Calcular score.
     * 5. Guardar resultado.
     */
    //metodo crear
    public ResultResponseDTO create(ResultRequestDTO requestDTO){

        //validacion del jugador con reslutado ya asignado para este entrenamineto
        if(resultRepository.existsByUserIdAndTrainingId(requestDTO.getUserId(), requestDTO.getTrainingId())){
            throw new DuplicateResourceException(
                    "El jugador ya tiene resultado para este entrenamiento");
        }

        //busqueda de player busca el jugador que hizo ese entrenamineot
        User user = userRepository.findById(requestDTO.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));

        //busqueda de entrenamineto para asignarle a el jugador
        Training training = trainingRepository.findById(requestDTO.getTrainingId()).orElseThrow(
                                () -> new ResourceNotFoundException("Entrenamiento no encontrado"));



        //mapemaos la request
        PlayerTrainingResult entity = ResultMapper.toEntity(requestDTO);

        entity.setUser(user);

        entity.setTraining(training);

        //calculo de porcentajes
        double score =
                (entity.getShotPower() * 0.20)
                        + (entity.getSpeed() * 0.30)
                        + (entity.getPasses() * 0.50);

        //guradamos el score (resultado del calculo)
        entity.setScore(score);

        //gurdamos en la base de datos
        PlayerTrainingResult saved=resultRepository.save(entity);

        //retornamos
        return ResultMapper.toResponse(saved);

    }
    /**
     * Obtiene todos los resultados
     * de un jugador específico.
     *
     * Utilizado por el rol PLAYER
     * para consultar su desempeño.
     */
    //metodo para ver los resultados
    public List<ResultResponseDTO> getResultsByPlayer(Long userId){

        //validacion
        User user = userRepository.findById(userId).orElseThrow(()
                        -> new ResourceNotFoundException("Jugador no encontrado"));

        return resultRepository.findByUserId(user.getId())
                .stream()
                .map(ResultMapper::toResponse)
                .toList();
    }

    /**
     * Obtiene los 5 jugadores titulares
     * de una semana determinada.
     *
     * Reglas:
     * - Deben existir los 3 entrenamientos.
     * - Se calcula el promedio semanal
     *   de cada jugador.
     * - Se ordenan de mayor a menor.
     * - Se retornan los 5 mejores.
     */
    //metodo de logica
    public List<StarterResponseDTO> getStarters(Integer weekNumber){

        // Verificar que la semana tenga
        // los 3 entrenamientos requeridos.
        long trainings = trainingRepository.countByWeekNumber(weekNumber);

        if(trainings < 3){throw new BusinessException("La semana no tiene los 3 entrenamientos completos");

        }
        // Obtener todos los resultados
        // asociados a la semana.
        List<PlayerTrainingResult> results = resultRepository.findByTrainingWeekNumber(weekNumber);

        return results.stream()
                .collect(Collectors.groupingBy(PlayerTrainingResult::getUser)
                ).entrySet()
                .stream()
                .map(entry -> {
                    User user = entry.getKey();

                    double average =
                            entry.getValue()
                                    .stream()
                                    .mapToDouble(PlayerTrainingResult::getScore)
                                    .average()
                                    .orElse(0);

                    StarterResponseDTO dto = new StarterResponseDTO();

                    dto.setUserId(user.getId());

                    dto.setUserName(user.getName());

                    dto.setAverageScore(average);

                    return dto;

                })
                // Agrupar resultados por jugador,
                // calcular promedio semanal,
                // ordenar y seleccionar los mejores 5.
                .sorted(Comparator.comparing(StarterResponseDTO::getAverageScore).reversed())
                .limit(5)
                .toList();
    }
}
