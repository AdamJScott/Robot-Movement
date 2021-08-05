import java.util.Scanner;


enum Direction
{
    NORTH, EAST, SOUTH, WEST
}

class ToyRobot
{

    private Direction Face;
    private int Xpos;// West 0, East 4
    private int Ypos;// South 0, North 4
    private boolean placed = false;

    ToyRobot()
    {

    }

    ToyRobot(int x_pos, int y_pos, Direction face)
    {
        Xpos = x_pos;
        Ypos = y_pos;
        Face = face;
        placed = true;
    }

    public void report()
    {
        if (Face != null){
            System.out.println(String.format("%s,%s,%s", Xpos, Ypos, Face));
        }
    }

    public void setXPos(int x_pos)
    {
        Xpos = x_pos;
    }

    public void setYPos(int y_pos)
    {
        Ypos = y_pos;
    }

    public void setDirection(Direction direction)
    {
        Face = direction;
    }

    public int getXPos()
    {
        return Xpos;
    }

    public int getYPos()
    {
        return Ypos;
    }

    public void setPlace(boolean placeStatus)
    {
        placed = placeStatus;
    }

    public boolean getPlaced()
    {
        return placed;
    }

    public char getDirectionChar()
    {

        switch (Face)
        {
            case NORTH:
                return '^';
            case EAST:
                return '>';
            case SOUTH:
                return 'v';
            case WEST:
                return '<';
            default:
                return 'æ';
        }
    }

    public Direction getDirection()
    {
        return Face;
    }

}

class TableTop
{
    private int Width;
    private int Height;
    private boolean errOutput;
    //private char[][] playSpace;

    private ToyRobot robot;

    TableTop()
    {
        robot = new ToyRobot();
    }

    TableTop(int width, int height, boolean errorOutput)
    {
        Width = width;
        Height = height;
        errOutput = errorOutput;
        // playSpace = new char[height][width];

        robot = new ToyRobot();
    }

    // Checks if the command issued is a legal move
    // E.g. If a move command is issued and the robot is facing east and is at 4,4,
    // it is not a legal command
    // As the robot will fall off the play space.
    private boolean isCommandLegal(int xPosProposal, int yPosProposal, String movement)
    {

        boolean xBool = (xPosProposal >= 0 && xPosProposal < Width);
        boolean yBool = (yPosProposal >= 0 && yPosProposal < Height);

        if (xBool && yBool)
        {
            return true;
        }

        if (errOutput){
            System.out.println(String.format("Cannot %s the robot to position:\n\tx: %d \n\ty: %d\n", movement,
            xPosProposal, yPosProposal));
        }
        
        return false;
    }

    public void placeRobot(int x_pos, int y_pos, Direction face)
    {
        if (isCommandLegal(x_pos, y_pos, "place"))
        {
            robot = new ToyRobot(x_pos, y_pos, face);
        }
    }

    public void leftRobot()
    {
        if (robot.getPlaced())
        {
            switch (robot.getDirection())
            {
                case NORTH:
                    robot.setDirection(Direction.WEST);
                    break;
                case WEST:
                    robot.setDirection(Direction.SOUTH);
                    break;
                case SOUTH:
                    robot.setDirection(Direction.EAST);
                    break;
                case EAST:
                    robot.setDirection(Direction.NORTH);
                    break;
            }
        }
        else
        {
            if (errOutput){
                System.out.println("You need to place the robot before you can rotate it\n");
            }
        }
    }

    public void rightRobot()
    {
        if (robot.getPlaced())
        {
            switch (robot.getDirection())
            {
                case NORTH:
                    robot.setDirection(Direction.EAST);
                    break;
                case EAST:
                    robot.setDirection(Direction.SOUTH);
                    break;
                case SOUTH:
                    robot.setDirection(Direction.WEST);
                    break;
                case WEST:
                    robot.setDirection(Direction.NORTH);
                    break;
            }
        }
        else
        {
            if (errOutput){
                System.out.println("You need to place the robot before you can rotate it\n");
            }
        }
    }

    public void moveRobot()
    {
        if (robot.getPlaced())
        {
            switch (robot.getDirection())
            {
                case NORTH:
                    if (isCommandLegal(robot.getXPos(), robot.getYPos() + 1, "move"))
                    {
                        robot.setYPos(robot.getYPos() + 1);
                    }
                    break;
                case SOUTH:
                    if (isCommandLegal(robot.getXPos(), robot.getYPos() - 1, "move"))
                    {
                        robot.setYPos(robot.getYPos() - 1);
                    }
                    break;
                case WEST:
                    if (isCommandLegal(robot.getXPos() - 1, robot.getYPos(), "move"))
                    {
                        robot.setXPos(robot.getXPos() - 1);
                    }

                    break;
                case EAST:
                    if (isCommandLegal(robot.getXPos() + 1, robot.getYPos(), "move"))
                    {
                        robot.setXPos(robot.getXPos() + 1);
                    }
                    break;
            }
        }
        else
        {
            if (errOutput){
                System.out.println("You need to place the robot before you can move it\n");
            }
        }

    }

    public void reportRobot()
    {
        robot.report();
    }

    public void drawBoardStatus()
    {

        System.out.println("-------");// Border of play space

        for (int i = Height - 1; i >= 0; i--)
        {
            System.out.print("|");// Border of play space

            for (int j = 0; j < Width; j++)
            {
                if (robot.getPlaced())
                {
                    if (i == robot.getYPos() && j == robot.getXPos()){
                        System.out.print(robot.getDirectionChar());
                    }
                    else
                    {   
                        System.out.print('¤');
                    }
                }
                else if (!robot.getPlaced())
                {
                    System.out.print('¤');
                }
            }
            System.out.print("|\n");// Border of play space
        }

        System.out.println("-------");// Border of play space

        if (robot.getPlaced())
        {
            System.out.println(String.format("%s - Robot where the point is it's direction", robot.getDirectionChar()));
        }
    }
}

public class RobotMovement
{

    public static boolean stringToIntCheck(String input)
    {
        try
        {
            int num = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException NFE)
        {
            return false;
        }
    }

    public static boolean stringToEnumCheck(String input)
    {
        for (Direction d : Direction.values())
        {
            if (input.equals(d.toString()))
            {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args)
    {

        String gameTitle = " _____                ______         _             _    ______  _                           \n"
                + "|_   _|               | ___ \\       | |           | |   | ___ \\| |                          \n"
                + "  | |    ___   _   _  | |_/ /  ___  | |__    ___  | |_  | |_/ /| |  __ _  _   _   ___  _ __ \n"
                + "  | |   / _ \\ | | | | |    /  / _ \\ | '_ \\  / _ \\ | __| |  __/ | | / _` || | | | / _ \\| '__|\n"
                + "  | |  | (_) || |_| | | |\\ \\ | (_) || |_) || (_) || |_  | |    | || (_| || |_| ||  __/| |   \n"
                + "  \\_/   \\___/  \\__, | \\_| \\_| \\___/ |_.__/  \\___/  \\__| \\_|    |_| \\__,_| \\__, | \\___||_|   \n"
                + "                __/ |                                                      __/ |            \n"
                + "               |___/                                                      |___/             ";

        String userInput;
        Scanner lineIn = new Scanner(System.in);

        boolean inGame = false;
        boolean menu = true;
        boolean errOutput = true;
        boolean initialEntry = false;
        boolean applicationStillRunning = true;

        System.out.println(gameTitle);
        System.out.println("--------------------------------------------------------------------------------------------");
        TableTop table = new TableTop();

        while (applicationStillRunning)
        {
            // Handles the menu

            System.out.println("Enter a command to begin:\n" + "START - Starts the game\n"
                    + "NOMENUSTART - Starts the game with the menu disabled - Note, will not disable error outputs\n"
                    + "NOERRSTART - Starts with same parameters as NOMENUSTART, but will disable error outputs\n"
                    + "EXIT - Exits the application\n" + "=>");

            userInput = lineIn.nextLine();

            switch (userInput.toUpperCase())
            {
                case "START":
                    inGame = true;
                    table = new TableTop(5, 5, errOutput);
                    break;
                case "NOMENUSTART":
                    inGame = true;
                    menu = false;
                    table = new TableTop(5, 5, errOutput);
                    break;
                case "NOERRSTART":
                    inGame = true;
                    menu = false;
                    errOutput = false;
                    table = new TableTop(5, 5, errOutput);
                    break;
                case "EXIT":
                    applicationStillRunning = false;
                    break;
                default:
                    System.out.println(String.format("Unknown command %s, please enter in START or EXIT\n", userInput));
            }

            if (inGame)
            {
                do
                {
                    // Menu
                    if (!menu && initialEntry)
                    {
                        System.out.println("---------------------\n");
                        initialEntry = false;
                    }
                    if (menu)
                    {
                        System.out.println("---------------------\n" + "Enter in a command:\n"
                                + "\tPLACE X,Y,DIRECTION - Places the robot on the table\n"
                                + "\tLEFT - Rotates the robot left 90º\n " + "\tRIGHT - Rotates the robot right 90º\n"
                                + "\tMOVE - Moves the robot forward one space\n"
                                + "\tREPORT - Reports where the robot is\n"
                                + "\tMAP - Show an ASCII representation of the table \n"
                                + "\tEXIT - Exits back to the main menu\n" + "=>");
                    }
                    userInput = lineIn.nextLine();// Get user input

                    String[] commands = userInput.toUpperCase().split(" ");// Split the string, returns single string if
                    // no space

                    switch (commands[0])
                    {
                        case "PLACE":
                            // Main handles the error checking on user input

                            String[] parameters = commands[1].split(",");

                            if (parameters.length == 3)
                            {

                                boolean xPosValid = stringToIntCheck(parameters[0]);
                                boolean yPosValid = stringToIntCheck(parameters[1]);
                                boolean directionValid = stringToEnumCheck(parameters[2]);

                                if (xPosValid && yPosValid && directionValid)
                                {
                                    table.placeRobot(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]),
                                            Direction.valueOf(parameters[2]));
                                }
                                else
                                {
                                    if (errOutput){
                                        System.out.println(String.format(
                                            "%s does not contain valid values\n" + "\txPOS valid: %s\n"
                                                    + "\tyPOS valid: %s\n" + "\tDirection valid: %s\n",
                                            commands[1], xPosValid, yPosValid, directionValid));
                                    }

                                }
                            }
                            else
                            {
                                if (errOutput){
                                    System.out.println(String.format(
                                        "Your output: %s is not a valid input\n"
                                                + "The parameters of the PLACE command cannot have spaces",
                                        userInput.toUpperCase()));
                                }
                            }
                            break;
                        case "LEFT":
                            table.leftRobot();
                            break;
                        case "RIGHT":
                            table.rightRobot();
                            break;
                        case "MOVE":
                            table.moveRobot();
                            break;
                        case "REPORT":
                            table.reportRobot();
                            break;
                        case "MAP":
                            table.drawBoardStatus();
                            break;
                        case "EXIT":
                            inGame = false;
                            break;
                        default:
                            if (errOutput){
                                System.out.println(String.format("%s is an unknown command\n"
                                    + "Please use the following commands\n" + "\tPLACE X,Y,DIRECTION\n" + "\tLEFT\n"
                                    + "\tRIGHT\n" + "\tMOVE\n" + "\tREPORT\n" + "\tEXIT\n", commands[0]));
                            }
                    }
                } while (inGame);
            }
        }
        
        lineIn.close();
    }
}