package com.soccertraining.soccer_training_api.service.crud;

import com.soccertraining.soccer_training_api.dto.trainigDTO.TrainigRequestDTO;
import com.soccertraining.soccer_training_api.dto.trainigDTO.TrainingResponseDTO;
import com.soccertraining.soccer_training_api.entity.Training;
import com.soccertraining.soccer_training_api.exception.BusinessException;
import com.soccertraining.soccer_training_api.exception.DuplicateResourceException;
import com.soccertraining.soccer_training_api.mapper.TrainigMapper;
import com.soccertraining.soccer_training_api.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TrainigService {
    private final TrainingRepository trainingRepository;

    //metodo de crear entrenamineto
    public TrainingResponseDTO createTrainig(TrainigRequestDTO requestDTO){

        //validacion de que ya existe el enytrenamineto
        if(trainingRepository.existsByWeekNumberAndTrainingNumber(requestDTO.getWeekNumber(),
                                requestDTO.getTrainingNumber())
        ){
            throw new DuplicateResourceException("El entrenamiento ya existe");
        }

        //validacion de los 3 entrenamineto
        long totalTrainings = trainingRepository.countByWeekNumber(
                        requestDTO.getWeekNumber());

        //validacion del cumplimento de las 3
        if(totalTrainings >= 3){
            throw new BusinessException("La semana ya tiene 3 entrenamientos");
        }
        /*
        if(requestDTO.getTrainingNumber() < 1 ||
        requestDTO.getTrainingNumber() > 3){
        throw new BusinessException("El número de entrenamiento debe estar entre 1 y 3");
           }
         */
        //mappeo la entrada
        Training entity= TrainigMapper.toEntity(requestDTO);
        entity.setTrainingDate(LocalDateTime.now());//mapeo la hora

        //guradamos los resultados en la base de datos
        Training saved=trainingRepository.save(entity);

        return TrainigMapper.toResponse(saved);


    }
}
