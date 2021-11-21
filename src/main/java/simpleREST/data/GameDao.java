package simpleREST.data;

import java.util.ArrayList;
import java.util.List;

public interface GameDao {

   Game add(Game game);
   Guess addGuess(Guess guess);

    List<Game> getAllGames();
    List<Guess> getAllGuesses();

    // true if item exists and is updated
    boolean update(Game game);

    // true if item exists and is deleted
    boolean deleteById(int id);


    Game getGame(int id);

    List<Guess> getGamesGuesses(Game game);

}
