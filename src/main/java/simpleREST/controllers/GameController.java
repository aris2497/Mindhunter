package simpleREST.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simpleREST.data.Game;
import simpleREST.data.Guess;
import simpleREST.service.GameServiceLayer;
import simpleREST.view.GameView;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RestController
/*
1.tells Spring MVC to scan for methods that can handle HTTP requests.
2.Makes the class injectable - It will be injected into Spring MVC core dependents.
3.Tells Spring MVC to convert method results to JSON.
*/

@RequestMapping("/api")
public class GameController {

    GameServiceLayer service;
    GameView view;
    Timestamp timestamp;

    int gameId = 0;
    String answer = "";
    Game gm;


    @Autowired
    public GameController(GameServiceLayer service, GameView view) {
        this.service = service;
        this.view = view;
        //run(); //console IO and view
    }

    /**
     * Starts a game, generates an answer,
     * and sets the correct status.
     *
     * @return 201 CREATED message as well as the created gameId.
     */
    @PostMapping("/begin")
    public int Begin() {
        gm = new Game();
        timestamp = new Timestamp(System.currentTimeMillis());
        // setting new game id to last id+1
        gameId = service.getLastGameId() + 1;
        gm.setGameId(gameId);
        answer = service.generateAnswer();
        gm.setAnswer(answer);
        gm.setState("inProgress");
        gm.setTimeStarted(timestamp.toString());

        service.addGame(gm);

        return gameId;
    }

    /**
     * Makes a guess by passing the guess and gameId in as JSON. The program calculate
     * the results of the guess and mark the game finished if the guess is correct.
     *
     * @param gameId
     * @param guessValues
     * @return Guess object with the results filled in.
     */

    @PostMapping("/guess")
    public Guess Guess(int gameId, String guessValues) {
        gm = service.getGame(gameId); //getting the name with the selected id

        int guessId = gm.getNoOfGuesses() + 1; //increasing value of guess for the current game

        gm.setNoOfGuesses(gm.getNoOfGuesses() + 1); //increasing number of guesses associated with the game

        Guess guess = new Guess(); //creating new guess
        int[] guessChecked = service.checkFormat(guessValues); //checking if guess format is correct
        String guessResult = service.checkGuess(guessChecked, gm.getAnswer()); //checking how successful was the guess; resulting in (p:?e?)

        guess.setGuessNo(guessId); //setting values for new guess
        guess.setGuessValue(guessValues);
        guess.setResult(guessResult);
        guess.setGameId(gameId);

        boolean isGameOver = service.isGameOver(guessResult); //checking if the arrays are the same

        if (isGameOver) {
            gm.setState("finished"); //seting status of the game from inProgress to finished
        }
        service.updateGame(gm); //updating the status of the game to 'finished'
        service.addGuess(guess);

        return guess;
    }

    /**
     * Returns a list of all games. In-progress games do not display their answer.
     *
     * @return hidden inProgress answers array
     */
    @GetMapping("/game")
    public ArrayList<Game> getAllGames() {
        //fetch all games stored in new list that hides
        //answers where game is inProgress
        return service.hideAnswers();
    }

    /**
     * Returns a list of guesses for the specified game sorted by time.
     *
     * @param id - gameId
     * @return all guesses for selected game
     */
    @GetMapping("/guesses/{id}")
    public List<Guess> getSelectGuesses(@PathVariable int id) {
        //fetch all guesses with matching gameId
        List<Guess> listOfGuesses = service.getGamesGuesses(service.getGame(id));

        //sort guesses by the time
        Collections.sort(listOfGuesses, new CustomComparator());

        return listOfGuesses;
    }

    @GetMapping("/game/{id}")
    public Game getSelectedGame(@PathVariable int id) {
        //get the game with selected id
        Game game = service.getGame(id);

        return game;
    }

    /*
    //for the console view and IO
    private void run() {
        Begin();

        while (!isGameOver) {
            guessValues = view.getGuessValues();
            guessChecked = service.checkFormat(guessValues);

            //if the answer format was accepted increase the number of guess
            // and create new Guess, otherwise ask again
            if (guessChecked != null) {
                Guess(gameId, guessResult);
            }
        }
        service.finishGame(gm);
        service.updateGame(gm);
    }
*/
    public class CustomComparator implements Comparator<Guess> {

        @Override
        public int compare(Guess o1, Guess o2) {
            return o1.getTime().compareTo(o2.getTime());
        }
    }
}
