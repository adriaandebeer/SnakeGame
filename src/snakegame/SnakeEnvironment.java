/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import audio.AudioPlayer;
import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Adriaan D
 */
class SnakeEnvironment extends Environment {

    Grid grid;
    private Snake snake;
    private Point cellLocation;
    private ArrayList<Point> apples;
    private ArrayList<Point> poison;
    private int defaultDelay = 2;
    private int delay = defaultDelay;
    private int score = 0;
    private final int APPLE_COUNT = 4;
    private GameState gameState = GameState.PAUSED;
    private int HEALTH = 100;

    @Override
    public void initializeEnvironment() {
        this.grid = new Grid();
        this.grid.getPosition().x = 50;
        this.grid.getPosition().y = 100;

        this.grid.setColor(Color.black);
        this.grid.setCellHeight(15);
        this.grid.setCellWidth(15);
        this.grid.setColumns(40);
        this.grid.setRows(40);

        this.apples = new ArrayList<Point>();
        for (int i = 0; i < APPLE_COUNT; i++) {
            this.apples.add(getRandomGridLocation());
        }
        
        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(4, 5));
        this.snake.getBody().add(new Point(4, 4));

    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()),
                         (int) (Math.random() * this.grid.getRows()));
    }

    @Override
    public void timerTaskHandler() {
        if (this.gameState == GameState.RUNNING) {

            if (this.snake != null) {
                if (delay <= 0) {
                    snake.move();
                    delay = defaultDelay;
                    checkSnakeIntersection();
                } else {
                    delay--;
                }
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            this.snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.snake.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            this.snake.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            this.snake.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.ENDED;
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (grid != null) {
            grid.paintComponent(graphics);
            graphics.drawString("SCORE: " + this.getScore(), 20, 20);
            if (gameState == GameState.ENDED) {

            }

            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());
                }
            }

            Point celLocation;

            graphics.setColor(Color.blue);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, this.grid.getCellWidth(), this.grid.getCellHeight());

                    if (this.poison != null) {
                        for (int i0 = 0; i0 < this.poison.size(); i++) {
                            this.poison.get(i);

                        }

                    }

                }
            }
        }
    }

    private void checkSnakeIntersection() {
        // if the snake location is the same as an apple's location
        // then grow the snake and remove the apple
        // later, move the apple and make a sound and increase the score

        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                // move apple
                this.apples.get(i).setLocation(getRandomGridLocation());
                this.addScore(10);
                AudioPlayer.play("/resources/shotgun.wav");
            }
        }
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * @param score the score to set
     */
    public void addScore(int score) {
        this.score += score;
    }

}
