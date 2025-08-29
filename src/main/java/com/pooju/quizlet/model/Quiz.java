package com.pooju.quizlet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    private String topic;
    private int difficulty;
    private List<Questions> questions;


}
