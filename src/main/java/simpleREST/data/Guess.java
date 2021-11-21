package simpleREST.data;

public class Guess {


    private int gameId;
    private int guessNo;
    private String guessValues;
    private String result;
    private String time;
    private int totalGuesses;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Guess() {
        this.guessNo = ++totalGuesses;
//        for(int i =0; i < guessValue.length; i++){
//            guessValue[i] = guesses[i];
//        }
    }

    @Override
    public String toString() {
        return "Guess{" +
                "guessNo=" + guessNo +
                ", guessValues='" + guessValues + '\'' +
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
        return guessValues;
    }

    public void setGuessValue(String guessValue) {
        this.guessValues = guessValue;
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


}
