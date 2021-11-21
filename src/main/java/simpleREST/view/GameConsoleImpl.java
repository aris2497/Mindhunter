package simpleREST.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GameConsoleImpl implements UserIO{

    final private Scanner console = new Scanner(System.in);

    /**
     * A very simple method that takes in a message to display on the console
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     * A simple method that takes in a message to display on the console,
     * and then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

}
