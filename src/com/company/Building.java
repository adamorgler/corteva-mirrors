package com.company;

import java.util.ArrayList;

public class Building {

    private int xSize;
    private int ySize;
    Room[][] rooms;

    public Building() {
    }

    public void setDimensions(int x, int y) {
        setXSize(x);
        setYSize(y);
        resetBuilding();
    }

    public void addMirror(int x, int y, char lean, char reflectiveSide) {
        MirrorRoom mr = new MirrorRoom(x, y, lean, reflectiveSide);
        try {
            rooms[x][y] = mr;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addMirror(MirrorRoom mr) {
        try {
            rooms[mr.xPos][mr.yPos] = mr;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== //
    // GETTER AND SETTERS //
    // ================== //

    public void setXSize(int x) {
        this.xSize = x;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    // ======= //
    // HELPERS //
    // ======= //


    private void resetBuilding() {
        rooms = new Room[xSize][ySize];
        for(int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                rooms[i][j] = new Room(i, j);
            }
        }
    }
}
