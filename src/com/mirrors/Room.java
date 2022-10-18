package com.mirrors;

public class Room {
    protected int xPos;
    protected int yPos;

    public Room(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // Inputs direction of entry
    // Outputs direction of exit
    // ENCODING:
    //  TOP = 0, RIGHT = 1, BOTTOM = 2, LEFT = 3
    public int enter(int direction) {
        try {
            switch (direction) {
                case 0:
                    return 2;
                case 1:
                    return 3;
                case 2:
                    return 0;
                case 3:
                    return 1;
                default:
                    throw new Exception("Error parsing direction code: " + direction);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
