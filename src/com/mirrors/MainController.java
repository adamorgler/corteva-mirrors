package com.mirrors;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainController {

    private static MainController instance;
    private Building building;
    Position entrance;
    private ArrayList<Position> path;
    private boolean enteredLoop;

    private MainController(){
        resetRoom();
    }

    public static MainController getInstance(){
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public void parseFile(String fileName) {
        File f = new File(fileName);
        try {
            Scanner scanner = new Scanner(f);
            // dimensions
            String dimensions = scanner.nextLine();
            parseDimensions(dimensions);
            // check for -1 end
            int negUno = Integer.parseInt(scanner.nextLine());
            if (negUno != -1 ) {
                throw new NumberFormatException("Expected '-1' on line 2");
            }
            // mirrors
            while(true) {
                String mirror = scanner.nextLine();
                if (mirror.equals("-1")) {
                    break;
                }
                parseMirror(mirror);
            }
            //entrance
            String entrance = scanner.nextLine();
            parseEntrance(entrance);
            // check for -1 end
            negUno = Integer.parseInt(scanner.nextLine());
            if (negUno != -1 ) {
                throw new NumberFormatException("Expected '-1' on line 2");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void parseDimensions(String line) {
        Scanner sc = new Scanner(line);
        sc.useDelimiter(",");
        try {
            int x = sc.nextInt();
            int y = sc.nextInt();
            building.setDimensions(x, y);
            if (sc.hasNext()) {
                throw new NumberFormatException("Please use format 'x,y' for dimensions.\nex. 5,4");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void parseMirror(String line) {
        Scanner sc = new Scanner(line);
        sc.useDelimiter("");
        try {
            String numParse = "";
            while(sc.hasNextInt()) {
                numParse += sc.next();
            }
            int x = Integer.parseInt(numParse);
            String comma = sc.next();
            numParse = "";
            while(sc.hasNextInt()) {
                numParse += sc.next();
            }
            int y = Integer.parseInt(numParse);
            char lean = sc.next().charAt(0);
            char reflectiveSide;
            if (sc.hasNext()) {
                reflectiveSide = sc.next().charAt(0);
            } else {
                reflectiveSide = 'B';
            }
            if (lean != 'R' && lean != 'L' && reflectiveSide != 'L' && reflectiveSide != 'R' && reflectiveSide != 'B') {
                throw new Exception("Mirror formatting error! ex. 1,2RR or 3,2L");
            }
            MirrorRoom mr = new MirrorRoom(x,y,lean,reflectiveSide);
            building.addMirror(mr);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void parseEntrance(String line) {
        Scanner sc = new Scanner(line);
        sc.useDelimiter("");
        try {
            String numParse = "";
            while(sc.hasNextInt()) {
                numParse += sc.next();
            }
            int posX = Integer.parseInt(numParse);
            String comma = sc.next();
            numParse = "";
            while(sc.hasNextInt()) {
                numParse += sc.next();
            }
            int posY = Integer.parseInt(numParse);
            int dir = sc.next().charAt(0);
            switch (dir) {
                case 'V':
                    if (posY == 0) {
                        dir = 0;
                        break;
                    } else if (posY == building.getYSize() - 1) {
                        dir = 2;
                        break;
                    } else {
                        throw new Exception("Laser must start on the edge of the room");
                    }
                case 'H':
                    if (posX == 0) {
                        dir = 1;
                        break;
                    } else if (posX == building.getXSize() - 1) {
                        dir = 3;
                        break;
                    } else {
                        throw new Exception("Laser must start on the edge of the room");
                    }
                default:
                    throw new Exception("Entrance must either be Vertical or Horizontal (V or H)");
            }
            entrance = new Position(posX, posY, dir);
            if (sc.hasNext()) {
                throw new NumberFormatException("Please use format 'x,yV|H' for entrance.\nex. 0,1V");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void generateResult() {
        enteredLoop = false;
        this.path = new ArrayList<>();
        int posX = entrance.xPos;
        int posY = entrance.yPos;
        int dir = entrance.dir;
        Room next = building.rooms[posX][posY];
        Position nextPos = entrance;
        path.add(nextPos);
        while(true) {
            dir = normalizeExit(dir); // fix direction to correctly enter next room
            dir = next.enter(dir); // enter next room
            switch(dir){
                case 0:
                    posY += 1;
                    break;
                case 1:
                    posX += 1;
                    break;
                case 2:
                    posY -= 1;
                    break;
                case 3:
                    posX -= 1;
                    break;
                default:
                    return;
            }
            // if next position has left the building, break
            if (posY < 0 || posX < 0 || posY >= building.getYSize() || posX >= building.getXSize()) {
                break;
            }
            nextPos = new Position(posX, posY, dir);
            next = building.rooms[posX][posY];
            if (checkPositions(nextPos)) {
                path.add(nextPos);
            } else {
                enteredLoop = true;
                break;
            }
        }
    }

    public int normalizeExit(int dir) {
        if (dir >= 0 && dir <= 3) {
            dir += 2;
            if (dir > 3) {
                dir -= 4;
            }
        }
        return dir;
    }

    public void printResult() {
        if (enteredLoop) {
            System.out.println("ENTERED A LOOP!");
        } else {
            System.out.println("1. " + building.getXSize() + "," + building.getYSize());
            if (entrance.dir == 1 || entrance.dir == 3) {
                System.out.println("2. " + entrance.xPos + "," + entrance.yPos + "H");
            } else {
                System.out.println("2. " + entrance.xPos + "," + entrance.yPos + "V");
            }
            if (path.get(path.size() - 1).dir == 1 || path.get(path.size() - 1).dir == 3) {
                System.out.println("3. " + path.get(path.size() - 1).xPos + "," + path.get(path.size() - 1).yPos + "H");
            } else {
                System.out.println("3. " + path.get(path.size() - 1).xPos + "," + path.get(path.size() - 1).yPos + "V");
            }
        }
    }

    public void resetRoom() {
        this.building = new Building();
    }

    private boolean checkPositions(Position p) {
        for (Position o : path) {
            if (o.xPos == p.xPos && o.yPos == p.yPos && o.dir == p.dir) {
                return false;
            }
        }
        return true;
    }
}
