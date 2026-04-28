/// Author: Nastia Kotliar
/// Finishing date: 5.13.2026

package manager;

import java.util.Scanner;

public class InputManager {
    Scanner scanner = new Scanner(System.in);
    GameManager game;

    // Class constructor
    public InputManager(GameManager gameManager) {
        this.game = gameManager;
    }

    // Needed for no-input scenes
    public void waitForEnter() {
        scanner.nextLine();
    }

    public void collectName() {
        String input = scanner.nextLine();

        while (input.isEmpty()) {
            noNameError();
            input = scanner.nextLine();
        }

        game.userName = input;
    }

    //Convert input to integer
    public void collectUserChoice(int min, int max) {
        while (true) {
            try {
                game.userChoice = Integer.parseInt(scanner.nextLine());
                if (game.userChoice >= min && game.userChoice <= max) {
                    return;
                } else {
                    wrongNumberError();
                }
            } catch (NumberFormatException e) {
                wrongNumberError();
            }
        }
    }

    ///
    ///Below are the errors
    ///

    public void wrongNumberError() {
        System.out.println("Sorry, I don't understand what do you want. Please type the number from choices above:");
    }

    public void noNameError() {
        System.out.println("Please tell me your name:");
    }

    public void alreadyDoneError () {
        System.out.println("Oops! Looks like we already done it. Choose something else.");
    }
}
