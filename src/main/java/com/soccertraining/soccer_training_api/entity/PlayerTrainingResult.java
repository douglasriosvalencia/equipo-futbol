package com.soccertraining.soccer_training_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "player_training_results")
public class PlayerTrainingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shot_power", nullable = false)
    private Double shotPower;

    @Column( nullable = false)
    private Double speed;

    @Column(nullable = false)
    private Double passes;

    @Column(nullable = false)
    private Double score;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_id",nullable = false)
    private Training training;
}
