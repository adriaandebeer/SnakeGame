/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Adriaan D
 */
public class Snake {

    public void move() {
        Point newHead = new Point();

        switch (direction) {
            case UP:
                newHead.x = getHead().x;
                newHead.y = getHead().y - 1;
                break;

            case DOWN:
                newHead.x = getHead().x;
                newHead.y = getHead().y + 1;
                break;

            case LEFT:
                newHead.x = getHead().x - 1;
                newHead.y = getHead().y;
                break;

            case RIGHT:
                newHead.x = getHead().x + 1;
                newHead.y = getHead().y;
        }
        body.add(0, newHead);
        body.remove(body.size()- 1);
    }

    public Point getHead() {
        return body.get(0);
    }

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private ArrayList<Point> body = new ArrayList<Point>();
    private Direction direction = Direction.LEFT;
    private int growthCount = 0;

    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the growthCount
     */
    public int getGrowthCount() {
        return growthCount;
    }

    /**
     * @param growthCount the growthCount to set
     */
    public void setGrowthCount(int growthCount) {
        this.growthCount = growthCount;
    }
//</editor-fold>

}
