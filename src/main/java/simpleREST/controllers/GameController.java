package simpleREST.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simpleREST.data.Game;
import simpleREST.data.Guess;
import simpleREST.service.GameServiceLayer;
import simpleREST.view.GameView;

import java.util.ArrayList;
import java.util.List;

@RestController
// 1.tells Spring MVC to scan for methods that can handle HTTP requests.
//2.Makes the class injectable - It will be injected into Spring MVC core dependents.
//3.Tells Spring MVC to convert method results to JSON.

@RequestMapping("/api")
public class GameController {

    GameServiceLayer service;
    GameView view;
    int gameId = 0;
    String answer = "";
    String guessValues = "";
    boolean isGameOver = false;
    Game gm;
    String guessResult;
    int[] guessChecked;


    @Autowired
    public GameController(GameServiceLayer service, GameView view) {
        this.service = service;
        this.view = view;
        //run();
    }

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

    @PostMapping("/begin")
    public int Begin() {
        gm = new Game();
        gameId = service.getLastGameId() + 1;

        // setting new game id to last id+1
        gm.setGameId(gameId);
        answer = service.generateAnswer();
        gm.setAnswer(answer);
        System.out.println(answer);
        gm.setState("inProgress");


        service.addGame(gm);

        return gameId;
    }

    /**
     *
     * @param gameId
     * @param guessValues
     * @return guess object
     */

    @PostMapping("/guess")
    public Guess Guess(int gameId, String guessValues) {
        gm = service.getGame(gameId); //getting the name with the selected id

        int guessId = gm.getNoOfGuesses() + 1; //increasing value of guess for the current game

        gm.setNoOfGuesses(gm.getNoOfGuesses() + 1); //increasing number of guesses associated with the game

        Guess guess = new Guess(); //creating new guess
        guessChecked = service.checkFormat(guessValues); //checking if guess format is correct
        guessResult = service.checkGuess(guessChecked, gm.getAnswer()); //checking how successful was the guess; resulting in (p:?e?)

        guess.setGuessNo(guessId); //setting values for new guess
        guess.setTime("just now");
        guess.setGuessValue(guessValues);
        guess.setResult(guessResult);
        guess.setGameId(gameId);

        isGameOver = service.isGameOver(guessResult); //checking if the arrays are the same

        if(isGameOver){
            gm.setState("finished");
        }
        service.updateGame(gm); //updating the status of the game to 'finished'
        service.addGuess(guess);

        return guess;
    }

    /**
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
     *
     * @param id - gameId
     * @return all guesses for selected game
     */
    @GetMapping("/game/{id}")
    public List<Guess> getSelectGame(@PathVariable int id) {
        //get the game with selected id
        Game game = service.getGame(id);

        //fetch all guesses with matching gameId
        return service.getGamesGuesses(game);
    }
}
