package model;

import java.util.List;

public class Question {

    private final int id;
    private final String clue;
    private final String answer;
    private final List<String> letters;

    public Question(int id, String clue, String answer, List<String> letters) {
        this.id = id;
        this.clue = clue;
        this.answer = answer;
        this.letters = letters;
    }

    public int getId() {
        return id;
    }

    public String getClue() {
        return clue;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getLetters() {
        return letters;
    }
}
