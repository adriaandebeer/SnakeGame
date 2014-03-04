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
import image.ResourceTools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Jim the pixel donut
 */
class SnakeEnvironment extends Environment {

    Grid grid;
    private Snake snake;
    private Point cellLocation;
    private ArrayList<Point> apples;
    private ArrayList<Point> poison;
    private int defaultDelay = 1;
    private int delay = defaultDelay;
    private int score = 0;
    private final int APPLE_COUNT = 4;
    private GameState gameState = GameState.PAUSED;
    private int HEALTH = 100;

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/thecastle.jpg"));

        this.grid = new Grid();
        this.grid.getPosition().x = 50;
        this.grid.getPosition().y = 50;
        this.grid.setCellHeight(30);
        this.grid.setCellWidth(30);

        this.grid.setColor(Color.black);
//        this.grid.setCellHeight(15);
//        this.grid.setCellWidth(15);
        this.grid.setColumns(40);
        this.grid.setRows(21);

        this.apples = new ArrayList<Point>();
        for (int i = 0; i < APPLE_COUNT; i++) {
            this.apples.add(getRandomGridLocation());

            this.poison = new ArrayList<Point>();
            this.poison.add(getRandomGridLocation());
            this.poison.add(getRandomGridLocation());
            this.poison.add(getRandomGridLocation());
            this.poison.add(getRandomGridLocation());
            this.poison.add(getRandomGridLocation());
            this.poison.add(getRandomGridLocation());
            this.poison.add(getRandomGridLocation());
            this.poison.add(getRandomGridLocation());
        }

        this.snake = new Snake();
        this.snake.setDirection(Direction.RIGHT);
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
                    if ((snake.selfHitTest())) {
                        this.gameState = GameState.ENDED;
                    } else {
                    }

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

    private void drawHelmet(Graphics graphics, Color color, Point position, Dimension size) {
//        graphics.setColor(Color.WHITE);
//        graphics.fillRect(position.x, position.y, size.width, size.height);

        //draw first part of faceplate
        graphics.setColor(color);
        graphics.fillRect(position.x + (size.width * 3 / 20), position.y + (size.height * 1 / 20), (size.width * 14 / 20), (size.height * 14 / 20));
        //bottom chin guard
        int[] xPoints = {position.x + (size.width * 3 / 20), position.x + (size.width * 10 / 20), position.x + (size.width * 17 / 20)};
        int[] yPoints = {position.y + (size.height * 15 / 20), position.y + (size.height * 20 / 20), position.y + (size.height * 15 / 20)};
        graphics.fillPolygon(xPoints, yPoints, 3);

        graphics.setColor(Color.white);
        graphics.fillRect(position.x + (size.width * 8 / 20), position.y + (size.height * 1 / 20), size.width * 4 / 20, size.height * 11 / 20);
        graphics.fillRect(position.x + (size.width * 3 / 20), position.y + (size.height * 11 / 20), size.width * 14 / 20, size.height * 4 / 20);

        graphics.setColor(color);
        int[] xPointsFH = {position.x + (size.width * 8 / 20), position.x + (size.width * 10 / 20), position.x + (size.width * 12 / 20)};
        int[] yPointsFH = {position.y + (size.height * 1 / 20), position.y + (size.height * 3 / 20), position.y + (size.height * 1 / 20)};
        graphics.fillPolygon(xPointsFH, yPointsFH, 3);

        int[] xPointsLC = {position.x + (size.width * 3 / 20), position.x + (size.width * 3 / 20), position.x + (size.width * 5 / 20)};
        int[] yPointsLC = {position.y + (size.height * 11 / 20), position.y + (size.height * 15 / 20), position.y + (size.height * 13 / 20)};
        graphics.fillPolygon(xPointsLC, yPointsLC, 3);

        //left cheek triangle
        int[] xPointsRC = {position.x + (size.width * 17 / 20), position.x + (size.width * 17 / 20), position.x + (size.width * 15 / 20)};
        int[] yPointsRC = {position.y + (size.height * 11 / 20), position.y + (size.height * 15 / 20), position.y + (size.height * 13 / 20)};
        graphics.fillPolygon(xPointsRC, yPointsRC, 3);

        graphics.setColor(Color.black);
        int[] xPointsLE = {position.x + (size.width * 5 / 20), position.x + (size.width * 9 / 20), position.x + (size.width * 9 / 20)};
        int[] yPointsLE = {position.y + (size.height * 12 / 20), position.y + (size.height * 12 / 20), position.y + (size.height * 14 / 20)};
        graphics.fillPolygon(xPointsLE, yPointsLE, 3);

        int[] xPointsRE = {position.x + (size.width * 15 / 20), position.x + (size.width * 11 / 20), position.x + (size.width * 11 / 20)};
        int[] yPointsRE = {position.y + (size.height * 12 / 20), position.y + (size.height * 12 / 20), position.y + (size.height * 14 / 20)};
        graphics.fillPolygon(xPointsRE, yPointsRE, 3);

    }

    @Override
    public void paintEnvironment(Graphics graphics) {

        graphics.drawString(gameState.toString(), 100, 100);

        if (grid != null) {

            grid.paintComponent(graphics);
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Impact", Font.ITALIC, 50));
            graphics.drawString("SCORE: " + this.getScore(), 20, 40);
            if (gameState == GameState.ENDED) {
          
                
            }

            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
//                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());
                    cellLocation = grid.getCellPosition(this.apples.get(i));
                    drawHelmet(graphics, Color.gray, cellLocation, new Dimension(this.grid.getCellWidth(), this.grid.getCellHeight()));
                }
                if (this.gameState == GameState.ENDED) {
                    graphics.drawString("GAME OVER NOOB", 300, 350);
                }
            }

            Point cellLocation;

            //graphics.setColor(Color.blue);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    drawHelmet(graphics, Color.BLUE, cellLocation, new Dimension(this.grid.getCellWidth(), this.grid.getCellHeight()));
                }
            }

            if (this.poison != null) {
                for (int i = 0; i < this.poison.size(); i++) {
                    cellLocation = grid.getCellPosition(this.poison.get(i));
                    drawHelmet(graphics, Color.RED, cellLocation, new Dimension(this.grid.getCellWidth(), this.grid.getCellHeight()));
                }
                if (this.gameState == gameState.PAUSED) {
                    graphics.drawString("Press SPACE to Start", 300, 200);
                }
                if (this.gameState == gameState.WON){
                    graphics.drawString("YOU ARE VICTORIOUS", 300, 200);
                    
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
                snake.grow(1);
                AudioPlayer.play("/resources/shotgun.wav");
            }
        }

        for (int i = 0; i < this.poison.size(); i++) {
            if (snake.getHead().equals(this.poison.get(i))) {
                // move poison
                this.poison.get(i).setLocation(getRandomGridLocation());
                this.addScore(-20);
                snake.shrink(1);
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
