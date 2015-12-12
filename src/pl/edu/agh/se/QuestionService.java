package pl.edu.agh.se;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by kkicinger on 2015-12-12.
 */
public class QuestionService {

    private static BayesianNetworkConfiguration.Features currentQuestion;
    private static List<BayesianNetworkConfiguration.Features> questions;

    public static void init() {
        questions = new ArrayList<>(Arrays.asList(BayesianNetworkConfiguration.Features.values()));
        Collections.shuffle(questions);
        nextFeatureQuestion();
    }

    public static void nextFeatureQuestion() {
        if(getQuestions() == null) {
            setCurrentFeatureQuestion(getQuestions().get(0));
        } else if(isNextFeatureQuestionAvailable()) {
            setCurrentFeatureQuestion(getQuestions().get(getQuestions().indexOf(getCurrentQuestion()) + 1));
        }
    }

    public static boolean isNextFeatureQuestionAvailable() {
        return getCurrentQuestionNumber() != getQuestions().size();
    }

    public static int getCurrentQuestionNumber() {
        return getQuestions().indexOf(getCurrentQuestion()) + 1;
    }

    public static BayesianNetworkConfiguration.Features getCurrentQuestion() {
        return currentQuestion;
    }

    public static void setCurrentFeatureQuestion(BayesianNetworkConfiguration.Features currentQuestion) {
        QuestionService.currentQuestion = currentQuestion;
    }

    public static List<BayesianNetworkConfiguration.Features> getQuestions() {
        return questions;
    }



}
