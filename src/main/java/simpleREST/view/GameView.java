package simpleREST.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameView {
    UserIO io;

    @Autowired
    public GameView(UserIO io) {
        this.io = io;
    }

    public String getGuessValues() {
        return io.readString("Enter your 4 guesses digits (no spaces) for example: 1,2,3,4");
    }
}
