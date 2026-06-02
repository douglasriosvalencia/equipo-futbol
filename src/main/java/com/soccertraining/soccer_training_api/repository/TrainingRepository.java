package com.soccertraining.soccer_training_api.repository;

import com.soccertraining.soccer_training_api.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training,Long> {
    long countByWeekNumber(Integer weekNumber);//esto es para contar los 3 dias del entreno
    boolean existsByWeekNumberAndTrainingNumber(
            Integer weekNumber,
            Integer trainingNumber
    );
}
