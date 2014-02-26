/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

import environment.ApplicationStarter;
import image.ResourceTools;

/**
 *
 * @author Adriaan D
 */
public class SnakeGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {                                                 
        start();
    }

    private static void start() {
        ApplicationStarter.run("Game", new SnakeEnvironment());
    }
    
}
