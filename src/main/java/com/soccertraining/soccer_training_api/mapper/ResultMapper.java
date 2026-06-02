package com.soccertraining.soccer_training_api.mapper;

import com.soccertraining.soccer_training_api.dto.resultDTO.ResultRequestDTO;
import com.soccertraining.soccer_training_api.dto.resultDTO.ResultResponseDTO;
import com.soccertraining.soccer_training_api.entity.PlayerTrainingResult;

public final class ResultMapper {
    private ResultMapper(){

    }

    //metodo de toEntiti (set)
    public static PlayerTrainingResult toEntity(ResultRequestDTO requestDTO){
        PlayerTrainingResult playerTrainingResult= new PlayerTrainingResult();

        playerTrainingResult.setShotPower(requestDTO.getShotPower());
        playerTrainingResult.setSpeed(requestDTO.getSpeed());
        playerTrainingResult.setPasses(requestDTO.getPasses());

        return playerTrainingResult;
    }

    //metodo reponse
    public static ResultResponseDTO toResponse(PlayerTrainingResult result){
        ResultResponseDTO responseDTO=new ResultResponseDTO();

        responseDTO.setId(result.getId());
        responseDTO.setShotPower(result.getShotPower());
        responseDTO.setSpeed(result.getSpeed());
        responseDTO.setPasses(result.getPasses());
        responseDTO.setScore(result.getScore());

        responseDTO.setWeekNumber(result.getTraining().getWeekNumber());
        responseDTO.setTrainingNumber(result.getTraining().getTrainingNumber());
        responseDTO.setUserName(result.getUser().getName()+" "+result.getUser().getSurName());

        return responseDTO;

    }
}
