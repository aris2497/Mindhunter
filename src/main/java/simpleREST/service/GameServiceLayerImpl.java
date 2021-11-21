package simpleREST.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import simpleREST.data.Game;
import simpleREST.data.GameDao;
import simpleREST.data.Guess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GameServiceLayerImpl implements GameServiceLayer {

    GameDao dao;

    @Autowired
    public GameServiceLayerImpl(GameDao dao) {
        this.dao = dao;
    }

    @Override
    public ArrayList<Game> getAllGames() {
        return (ArrayList<Game>) dao.getAllGames();
    }

    @Override
    public List<Guess> getAllGuesses() {
        return dao.getAllGuesses();
    }

    @Override
    public Game getGame(int id) {
        return dao.getGame(id);
    }

    @Override
    public String generateAnswer() {
        int answer[] = new int[4];
        for(int i = 0; i < answer.length; i++)
        {
            int randomNo = (int) (Math.random()*9)+1;
            if (isInArray(randomNo, answer))
            {
                i--; // Just generate another number but don't lose track of position
            } else{
                answer[i] = randomNo; //Valid -  add to the array
            }
        }
        return Arrays.toString(answer);
    }

    @Override
    public int getLastGameId() {
        return  dao.getAllGames().size();
    }

    static int[] convertFromStr(String str){
        String[] separatedStrings = str.replaceAll("\\[", "")
                .replaceAll("]", "").replaceAll(" ", "")
                .split(",");

        int[] intArray = new int[separatedStrings.length];

        for (int i = 0; i < separatedStrings.length; i++) {
            try {
                intArray[i] = Integer.valueOf(separatedStrings[i]);
            } catch (Exception e) {

                System.out.println("Unable to accept the format of: " + separatedStrings[i]);
            }
        }
        return intArray;
    }

    @Override
    public String checkGuess(int[] guesses, String answer) {
        boolean isGameOver = false;
        int partial = 0;
        int exact = 0;

        int[] generatedNos = convertFromStr(answer);
        System.out.println("answer: " + Arrays.toString(generatedNos));
        System.out.println(Arrays.toString(generatedNos));
        System.out.println(Arrays.toString(guesses));

        if(Arrays.equals(guesses,generatedNos)) // If contents of arrays are exact!
        {
            System.out.println("Both arrays are equal - Game over ");
            isGameOver = true;
        }

        if (!isGameOver)
        {
            for(int i = 0; i < guesses.length;i++) {
                if (isInArray(guesses[i], generatedNos)) {
                    System.out.println("The number " + guesses[i] + " is in the array");
                    partial++;
                }
                if (guesses[i] == generatedNos[i]) {
                    System.out.println("The number " + guesses[i] + " is an exact match");
                    exact++;
                    partial--;
                }
            }
        } else if (guesses.length != 1) { //if the format was incorrect
            System.out.println("Try again");
            return null;
        }

        return "p:" +partial+ "e:" +exact;
    }

    @Override
    public boolean isGameOver(String guessResult) {
        return guessResult.equals("p:0e:4");
    }

    @Override
    public ArrayList<Game> hideAnswers() {
        ArrayList<Game> displayArray = getAllGames(); //displayArray created to copy the value of the original array
        for(int i = 0; i < displayArray.size(); i++){
            if(displayArray.get(i).getState().equals("inProgress")){
                displayArray.get(i).setAnswer("Hidden");
            }
        }
        return displayArray;
    }

    @Override
    public List<Guess> getGamesGuesses(Game game) {
        return dao.getGamesGuesses(game);
    }

    public static boolean isInArray(int a, int[]ans)
    {
        for(int i = 0; i < ans.length; i++)
        {
            if(ans[i] == a)
            {
                return true;
            }
        }
        return false;
    }
    public void updateGame(Game game){
        dao.update(game);
    }

    @Override
    public void addGame(Game gm) {
        dao.add(gm);
    }

    @Override
    public void addGuess(Guess guess) {
        dao.addGuess(guess);
    }


    @Override
    public void finishGame(Game gm) {
        gm.setState("finished");
    }

    @Override
    public int[] checkFormat(String guessValues) {
        int[] guess = convertFromStr(guessValues);
        return guess;
    }
}
