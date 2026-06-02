package com.soccertraining.soccer_training_api.repository;

import com.soccertraining.soccer_training_api.entity.PlayerTrainingResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerTrainingResultRepository extends JpaRepository<PlayerTrainingResult,Long> {
    /**
     * obtine todos los resultados de un entrenamineto
     * asociado a un jugador especifico
     * lo utilizamos cuando un jugaodr ya  aver su historial
     * @param userId
     * @return
     */
    List<PlayerTrainingResult> findByUserId(Long userId);// esto es para la consulta del el jugador y ver sus resultados

    /**
     * verifica que un jugador tenga registardo un resultado para ese entrenamineto
     * evita duplicados
     * @param userId
     * @param trainingId
     * @return
     */
    boolean existsByUserIdAndTrainingId(Long userId, Long trainingId);

    /**
     * obtiene todos los resultados de una semana para poder calcular el promedio
     * @param weekNumber
     * @return
     */
    List<PlayerTrainingResult> findByTrainingWeekNumber(Integer weekNumber);
}
