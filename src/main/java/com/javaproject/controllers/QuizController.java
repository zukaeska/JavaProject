package com.javaproject.controllers;

import com.javaproject.dto.QuizQuestion;
import com.javaproject.dto.ScoreData;
import com.javaproject.dto.utils.QuestionsGenerator;
import com.javaproject.dto.utils.Sha256HashGenerator;
import com.javaproject.entity.Score;
import com.javaproject.repository.ScoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/Quiz")
public class QuizController {
    private ScoreRepository scoreRepository;

    public QuizController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

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
        Score score = new Score(data.username, data.score);

        List<Score> topScores = scoreRepository.findAll();
        int topScoresCount = topScores.size();

        if (topScoresCount < 20) {
            // If there is room in the top 20, simply save the new score
            scoreRepository.save(score);
        } else {
            // Sort the top scores in ascending order based on the score value
            topScores.sort(Comparator.comparing(Score::getScore));

            Score lowestScore = topScores.get(0);
            if (score.getScore() > lowestScore.getScore()) {
                // Delete the lowest score
                scoreRepository.delete(lowestScore);

                // Save the new score
                scoreRepository.save(score);
            }
        }
    }
    @GetMapping("/UpdateLeaderboard")
    public List<ScoreData> getLeaderBaord(){
        List<Score> scores = scoreRepository.findAll();
        List<ScoreData> topScores = new ArrayList<>();
        for (Score scr : scores) {
            ScoreData newScore = new ScoreData();
            newScore.score = scr.getScore();
            newScore.username = scr.getUserName();
            topScores.add(newScore);
        }
        topScores.sort(Comparator.comparing(ScoreData::getScore).reversed());
        return topScores;
    }
    private static String generateApiUrlForKey(String key) {
        return "https://flagcdn.com/256x192/" + key +".png"; // Return the generated API URL
    }

}
