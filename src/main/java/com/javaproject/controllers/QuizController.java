package com.javaproject.controllers;

import com.javaproject.dto.FlagCodesResponse;
import com.javaproject.dto.QuizQuestion;
import com.javaproject.dto.ScoreData;
import com.javaproject.dto.utils.QuestionsGenerator;
import com.javaproject.dto.utils.Sha256HashGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@RestController
@RequestMapping("/Quiz")
public class QuizController {


    @PostMapping("/Start")
    public List<QuizQuestion> startQuiz() {
        var randomCountries = QuestionsGenerator.getRandomDictionaryElements(20);
        List<QuizQuestion> result = new ArrayList<>();

        randomCountries.keys().asIterator().forEachRemaining(key -> {
            String flagSrc = generateApiUrlForKey(key);
            String country = randomCountries.get(key);
            String answerHash = Sha256HashGenerator.getSHA256Hash(country);
            List<String> randomAnswers = QuestionsGenerator.getRandomDictionaryValues(3, country);
            randomAnswers.add(country);

            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.FlagSrc = flagSrc;
            quizQuestion.AnswerHash = answerHash;
            quizQuestion.PossibleAnswers = randomAnswers;

            result.add(quizQuestion);
        });

        return result;
    }
    @PostMapping("/Finish")
    public void finishQuiz(@RequestBody ScoreData data) {
        var i = 5;
    }
    @GetMapping("/Leaderboard")
    public List<ScoreData> getLeaderBaord(){
        return new ArrayList<ScoreData>();
    }
    private static String generateApiUrlForKey(String key) {
        return "https://flagcdn.com/256x192/" + key +".png"; // Return the generated API URL
    }

}
