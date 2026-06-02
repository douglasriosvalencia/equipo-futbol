package com.soccertraining.soccer_training_api.mapper;

import com.soccertraining.soccer_training_api.dto.trainigDTO.TrainigRequestDTO;
import com.soccertraining.soccer_training_api.dto.trainigDTO.TrainingResponseDTO;
import com.soccertraining.soccer_training_api.entity.Training;

public final class TrainigMapper {
    private TrainigMapper() {
    }

    public static Training toEntity(TrainigRequestDTO requestDTO){
        Training training=new Training();

        training.setWeekNumber(requestDTO.getWeekNumber());
        training.setTrainingNumber(requestDTO.getTrainingNumber());
        return training;
    }

    public static TrainingResponseDTO toResponse(Training training){
        TrainingResponseDTO trainingResponseDTO=new TrainingResponseDTO();

        trainingResponseDTO.setId(training.getId());
        trainingResponseDTO.setWeekNumber(training.getWeekNumber());
        trainingResponseDTO.setTrainingNumber(training.getTrainingNumber());
        trainingResponseDTO.setTrainingDate(training.getTrainingDate());

        return trainingResponseDTO;
    }
}
