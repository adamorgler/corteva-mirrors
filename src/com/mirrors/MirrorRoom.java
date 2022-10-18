package com.company;

public class MirrorRoom extends Room{

    // use in this dir, add extra security with private if needed later
    protected char lean; // indicates weather the mirror is leaning right or left (encoded as R or L)
    protected char reflectiveSide; // indicates which side of the mirror is reflective (encoded as R or L. B if both sides)

    public MirrorRoom(int xPos, int yPos, char lean, char reflectiveSide) {
        super(xPos, yPos);
        this.lean = lean;
        this.reflectiveSide = reflectiveSide;
    }

    // hard encoding all possible options, probably a smarter solution but I cannot think of it
    @Override
    public int enter(int direction) {
        try {
            if (reflectiveSide == 'B' && lean == 'L') {
                switch (direction) {
                    case 0:
                        return 1;
                    case 1:
                        return 0;
                    case 2:
                        return 3;
                    case 3:
                        return 2;
                    default:
                        throw new Exception("Error handling laser movement in room " + xPos + "," + yPos);
                }
            }
            else if (reflectiveSide == 'B' && lean == 'R') {
                switch (direction) {
                    case 0:
                        return 3;
                    case 1:
                        return 2;
                    case 2:
                        return 1;
                    case 3:
                        return 0;
                    default:
                        throw new Exception("Error handling laser movement in room " + xPos + "," + yPos);
                }
            }
            else if (reflectiveSide == 'R' && lean == 'L') {
                switch (direction) {
                    case 0:
                    case 3:
                        return 1;
                    case 1:
                    case 2:
                        return 0;
                    default:
                        throw new Exception("Error handling laser movement in room " + xPos + "," + yPos);
                }
            }
            else if (reflectiveSide == 'R' && lean == 'R') {
                switch (direction) {
                    case 0:
                    case 1:
                        return 2;
                    case 2:
                    case 3:
                        return 1;
                    default:
                        throw new Exception("Error handling laser movement in room " + xPos + "," + yPos);
                }
            }
            else if (reflectiveSide == 'L' && lean == 'L') {
                switch (direction) {
                    case 0:
                    case 3:
                        return 2;
                    case 2:
                    case 1:
                        return 3;
                    default:
                        throw new Exception("Error handling laser movement in room " + xPos + "," + yPos);
                }
            }
            else if (reflectiveSide == 'L' && lean == 'R') {
                switch (direction) {
                    case 0:
                    case 1:
                        return 3;
                    case 2:
                    case 3:
                        return 0;
                    default:
                        throw new Exception("Error handling laser movement in room " + xPos + "," + yPos);
                }
            } else {
                throw new Exception("Error handling laser movement in room " + xPos + "," + yPos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
