package home.fastcalcul.Models;

/**
 * Created by rozan_000 on 08/07/2017.
 */

public class HighScores {
    private String name;
    private int score;

    public HighScores() {
        this.name = "";
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
