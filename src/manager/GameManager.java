/// Author: Nastia Kotliar
/// Finishing date: 5.13.2026

package manager;

import resources.scenes.Outputs;

public class GameManager {
    DisplayManager displayManager = new DisplayManager(this);
    InputManager inputManager = new InputManager(this);
    Outputs outputs = new Outputs();

    boolean gameStarted = false;
    //TODO: add pause to the game

    public String userName;
    public int userChoice;

    int currentScene = 0;

    //Status Checkers
    boolean isSeedlingsFounded;
    boolean isToolsFounded;
    boolean isSoilFounded;
    boolean isBedsFixed;
    boolean isGardenWeeded;

    // Starts the game thread
    public void startGame() {
        gameStarted = true;

        while (gameStarted) {
            displayManager.displayScene(currentScene);
            handleScene();
        }
    }

    //Pretty language
    public void handleScene() {
        switch (currentScene){
            case 0, 1 -> noInputScene();
            case 2 -> collectNameScene();
            case 3 -> followOliverScene();
            case 4 -> exploreGardenScene();
            case 5 -> cleanGardenScene();
            case 6 -> plantSeedsScene();
            case 21 -> sceneAreYouSure();
            case 22 -> endScene();
        }
    }

    public void noInputScene(){
        inputManager.waitForEnter();

        currentScene++;
    }

    public void collectNameScene(){
        inputManager.collectName();

        currentScene = 3;
    }

    public void followOliverScene(){
        inputManager.collectUserChoice(1, 2);

        if (userChoice == 2){
            currentScene = 21;
        } else if (userChoice == 1) {
            currentScene = 4;
        }
    }

    public void exploreGardenScene() {
        while (true) { //This loop-fix was suggested by chatty. I was trying to create something on my own, but didn't get anything but extra boolean :(
            inputManager.collectUserChoice(1, 3);

            // Checks if actions were done
            if ((userChoice == 1 && isSeedlingsFounded) ||
                    (userChoice == 2 && isToolsFounded) ||
                    (userChoice == 3 && isSoilFounded)) {
                inputManager.alreadyDoneError();
                continue;
            }

            if (userChoice == 1) {
                isSeedlingsFounded = true;
            } else if (userChoice == 2) {
                isToolsFounded = true;
            } else if (userChoice == 3) {
                isSoilFounded = true;
            }

            System.out.println(outputs.EXPLORE[userChoice - 1]);
            inputManager.waitForEnter();
            currentScene = 5;

            return;
        }
    }

    public void cleanGardenScene(){
        if (isBedsFixed && isGardenWeeded) {
            currentScene = 6;
            return;
        }

        while (true) {
            inputManager.collectUserChoice(1, 2);

            if ((userChoice == 1 && isBedsFixed) ||
                    (userChoice == 2 && isGardenWeeded)) {
                inputManager.alreadyDoneError();
                continue;
            }

            if (userChoice == 1) {
                if (isToolsFounded) {
                    isBedsFixed = true;
                    currentScene = 6;
                } else {
                    userChoice = 3;
                    currentScene = 4;
                }
            } else if (userChoice == 2) {
                isGardenWeeded = true;
                currentScene = 6;
            }

            System.out.println(outputs.GARDEN_CHORES[userChoice - 1]);
            inputManager.waitForEnter();
            return;
        }
    }

    // Goes through all checkers and handels the last scene
    public void plantSeedsScene(){
        if (isSeedlingsFounded) {
            inputManager.collectUserChoice(1, 4);
        } else {
            inputManager.collectUserChoice(1, 3);
        }

        if (!isSoilFounded) {
            System.out.println("Oops! We're missing the soil! Let's go back to the compostpile and find it.");
            currentScene = 4;
            inputManager.waitForEnter();
            return;
        }

        if (!isGardenWeeded) {
            System.out.println("Oops! We need to remove weeds first.");
            currentScene = 5;
            inputManager.waitForEnter();
            return;
        }

        if (!isBedsFixed) {
            System.out.println("Oops! We need to fix beds first.");
            currentScene = 5;
            inputManager.waitForEnter();
            return;
        }

        if (isSeedlingsFounded && (userChoice >= 1 && userChoice <= 4)) {
            System.out.println(outputs.PLANT_OPTIONS[userChoice - 1]);
            inputManager.waitForEnter();

            displayManager.displayEmptyScreen();
            System.out.println(outputs.FINALS[userChoice - 1]);
        } else if (!isSeedlingsFounded && (userChoice >= 1 && userChoice <= 3)){
            System.out.println(outputs.PLANT_OPTIONS[userChoice]);
            inputManager.waitForEnter();

            displayManager.displayEmptyScreen();
            System.out.println(outputs.FINALS[userChoice]);
        }

        endScene();
    }

    public void sceneAreYouSure(){
        inputManager.collectUserChoice(1, 2);

        if (userChoice == 1) {
            currentScene = 4;
        } else if (userChoice == 2) {
            currentScene = 22;
        }
    }

    public void endScene() {
        gameStarted = false;
        System.exit(0);
    }
}
