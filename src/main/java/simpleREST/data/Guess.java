package simpleREST.data;

import java.sql.Timestamp;

public class Guess {



    private int gameId;
    private int guessNo;
    private String guessValue;
    private String result;


    private Timestamp time;
    private int totalGuesses;


    public Guess() {
        this.time = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Guess{" +
                "guessNo=" + guessNo +
                ", guessValues='" + guessValue + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public int getGuessNo() {
        return guessNo;
    }

    public void setGuessNo(int guessNo) {
        this.guessNo = guessNo;
    }

    public String getGuessValue() {
        return guessValue;
    }

    public void setGuessValue(String guessValue) {
        this.guessValue = guessValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }


}
