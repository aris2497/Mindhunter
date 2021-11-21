package simpleREST.service;

import simpleREST.data.Game;
import simpleREST.data.Guess;
import java.util.ArrayList;
import java.util.List;

/*Service layer to manage the game rules, such as
generating initial answers for a game and calculating the results of a
guess.*/

public interface GameServiceLayer {
    ArrayList<Game> getAllGames(); //getting list of all games

    List<Guess> getAllGuesses();

    Game getGame(int id);

    String generateAnswer();

    int getLastGameId();

    String checkGuess(int[] guessValues, String answer);

    boolean isGameOver(String guessResult);

    ArrayList<Game> hideAnswers();

    List<Guess> getGamesGuesses(Game game);
    void updateGame(Game game);

    void addGame(Game gm);

    void addGuess(Guess guess);

    void finishGame(Game gm);

    int[] checkFormat(String guessValues);
}
