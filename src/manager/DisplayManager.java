/// Author: Nastia Kotliar
/// Finishing date: 5.13.2026

package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DisplayManager {
    GameManager game;

    public DisplayManager(GameManager gameManager) {
        this.game = gameManager;
    }

    public void displayEmptyScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayScene(int sceneNumber) {
        displayEmptyScreen();

        String scenePath = "resources/scenes/" + sceneNumber + ".txt";

            try (BufferedReader textReader = new BufferedReader(new FileReader(scenePath))) {
                String line;

                int i = 0;

                while ((line = textReader.readLine()) != null) {
                    if (sceneNumber == 3 && i == 1) {
                        line = line.concat(", " + game.userName + "!"); // I hope it counts as an array :)
                    }

                    if (sceneNumber == 6 && !game.isSeedlingsFounded) {
                        switch (i){
                            case 14 -> line = "";
                            case 15 -> line = "1. Fast-growing vegetables.";
                            case 16 -> line = "2. Flowers.";
                            case 17 -> line = "3. Everything.";
                        }
                    }

                    System.out.println(line);
                    i++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
