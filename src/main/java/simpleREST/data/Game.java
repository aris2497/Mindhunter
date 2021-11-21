package simpleREST.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private int gameId;
    private String state = "inProgress";
    private String timeStarted = "0:0:0";
    private List<Guess> guesses;
    private int noOfGuesses = 0;
    private String answer = "8234";

    public Game(){
        this.guesses = new ArrayList<Guess>();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public List<Guess> getGuesses() {
        return guesses;
    }
    public void setGuesses(List<Guess> guesses) {
        this.guesses = guesses;
    }

    public void setSingleGuess(String values) {
        Guess a = new Guess();
        a.setGuessValue(values);
        this.guesses.add(a);
    }

    public String getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(String timeStarted) {
        this.timeStarted = timeStarted;
    }
    public String getState() {
        return state;
    }
    public int getNoOfGuesses() {
        return noOfGuesses;
    }
    public String getAnswer() {
        return answer;
    }
    public void setState(String state) {
        this.state = state;
    }

    public void setNoOfGuesses(int noOfGuesses) {
        this.noOfGuesses = noOfGuesses;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
