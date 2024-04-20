import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The type Main.
 */
public class Main {
    private static Board gameBoard;
    static final int DMAX = 1000; //Limit variables.
    static final int DMIN = 4;
    static final int MNMIN = 1;
    static final int NMAX = 16;
    static final int MMAX = 200;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(new File("input.txt")); //Scanner s.
        PrintStream fileOut = new PrintStream("./output.txt"); //output file.
        System.setOut(fileOut); //Output all to the output file.

        try {
            int d = s.nextInt();
            if (d < DMIN || d > DMAX) {
                throw new InvalidBoardSizeException();
            }
            int n = s.nextInt();
            if (n < MNMIN || n > NMAX) {
                throw new InvalidNumberOfInsectsException();
            }
            int m = s.nextInt();
            if (m < MNMIN || m > MMAX) {
                throw new InvalidNumberOfFoodPoints();
            }
            gameBoard = new Board(d);
            ArrayList<Insect> insects = new ArrayList<>(); //ArrayList for insects on the board.
            s.nextLine();
            for (int i = 0; i < n; i++) { //Adding new insects.
                String[] entity = s.nextLine().split(" ");
                InsectColor color = InsectColor.toColor(entity[0]);
                if (entity[1].equals("Grasshopper")) {
                    Grasshopper newInsect = new Grasshopper(new EntityPosition(Integer.parseInt(entity[2 + 1]),
                            Integer.parseInt(entity[2])), color);
                    gameBoard.addEntity(newInsect);
                    insects.add(newInsect);
                } else if (entity[1].equals("Butterfly")) {
                    Butterfly newInsect = new Butterfly(new EntityPosition(Integer.parseInt(entity[2 + 1]),
                            Integer.parseInt(entity[2])), color);
                    gameBoard.addEntity(newInsect);
                    insects.add(newInsect);
                } else if (entity[1].equals("Ant")) {
                    Ant newInsect = new Ant(new EntityPosition(Integer.parseInt(entity[2 + 1]),
                            Integer.parseInt(entity[2])), color);
                    gameBoard.addEntity(newInsect);
                    insects.add(newInsect);
                } else if (entity[1].equals("Spider")) {
                    Spider newInsect = new Spider(new EntityPosition(Integer.parseInt(entity[2 + 1]),
                            Integer.parseInt(entity[2])), color);
                    gameBoard.addEntity(newInsect);
                    insects.add(newInsect);
                } else {
                    throw new InvalidInsectTypeException();
                }
            }
            for (int i = 0; i < m; i++) { //Adding new food points.
                int value = s.nextInt();
                int y = s.nextInt();
                int x = s.nextInt();
                BoardEntity newFoodPoint = new FoodPoint(new EntityPosition(x, y), value);
                gameBoard.addEntity(newFoodPoint);
            }

            //Moving insects
            for (int i = 0; i < n; i++) {
                if (insects.get(i).color.equals(InsectColor.RED)) {
                    System.out.print("Red ");
                } else if (insects.get(i).color.equals(InsectColor.GREEN)) {
                    System.out.print("Green ");
                } else if (insects.get(i).color.equals(InsectColor.BLUE)) {
                    System.out.print("Blue ");
                } else if (insects.get(i).color.equals(InsectColor.YELLOW)) {
                    System.out.print("Yellow ");
                }
                if (insects.get(i) instanceof Grasshopper) {
                    System.out.print("Grasshopper ");
                } else if (insects.get(i) instanceof Butterfly) {
                    System.out.print("Butterfly ");
                } else if (insects.get(i) instanceof Ant) {
                    System.out.print("Ant ");
                } else {
                    System.out.print("Spider ");
                }
                System.out.print(gameBoard.getDirection(insects.get(i)).getTextRepresentation() + " ");
                System.out.print(gameBoard.getDirectionSum(insects.get(i)));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/**
 * The exception "Invalid board size exception" that informs if board size is invalid.
 */
class InvalidBoardSizeException extends Exception {
    /**
     * Instantiates a new Invalid board size exception.
     */
    public InvalidBoardSizeException() {
        super("Invalid board size");
    }
}

/**
 * The exception "Invalid number of insects exception" that informs if number of insects is invalid.
 */
class InvalidNumberOfInsectsException extends Exception {
    /**
     * Instantiates a new Invalid number of insects exception.
     */
    public InvalidNumberOfInsectsException() {
        super("Invalid number of insects");
    }
}

/**
 * The exception "Invalid number of food points exception"  that informs if number of food points is invalid.
 */
class InvalidNumberOfFoodPoints extends Exception {
    /**
     * Instantiates a new Invalid number of food points.
     */
    public InvalidNumberOfFoodPoints() {
        super("Invalid number of food points");
    }
}

/**
 * The exception "Invalid insect color exception" that informs if printed insect color is invalid.
 */
class InvalidInsectColorException extends Exception {
    /**
     * Instantiates a new Invalid insect color exception.
     */
    public InvalidInsectColorException() {
        super("Invalid insect color");
    }
}


/**
 * The exception "Invalid insect type exception" that informs if printed insect type is invalid.
 */
class InvalidInsectTypeException extends Exception {
    /**
     * Instantiates a new Invalid insect type exception.
     */
    public InvalidInsectTypeException() {
        super("Invalid insect type");
    }
}

/**
 * The exception "Invalid entity position exception" that informs if entity position is invalid.
 */
class InvalidEntityPositionException extends Exception {
    /**
     * Instantiates a new Invalid entity position exception.
     */
    public InvalidEntityPositionException() {
        super("Invalid entity position");
    }
}

/**
 * The exception "Duplicate insect exception" that informs if the same insect already exists.
 */
class DuplicateInsectException extends Exception {
    /**
     * Instantiates a new Duplicate insect exception.
     */
    public DuplicateInsectException() {
        super("Duplicate insects");
    }
}

/**
 * The exception "Two entities on same position exception" that informs if insect in the same position already exists.
 */
class TwoEntitiesOnSamePositionException extends Exception {
    /**
     * Instantiates a new Two entities on same position exception.
     */
    public TwoEntitiesOnSamePositionException() {
        super("Two entities in the same position");
    }
}

/**
 * The class Entity position.
 */
class EntityPosition {
    private int x;
    private int y;

    /**
     * Instantiates a new Entity position.
     *
     * @param x the x
     * @param y the y
     */
    public EntityPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }
}

/**
 * The class Food point.
 */
class FoodPoint extends BoardEntity {
    /**
     * The Value of Food point.
     */
    protected int value;

    /**
     * Instantiates a new Food point.
     *
     * @param position the position
     * @param value    the value
     */
    public FoodPoint(EntityPosition position, int value) {
        this.value = value;
        this.entityPosition = position;
    }
}

/**
 * The type Insect that is a parent class for Grasshopper, Butterfly, Ant, and Spider classes.
 */
abstract class Insect extends BoardEntity {
    /**
     * The Color.
     */
    protected InsectColor color;

    /**
     * Instantiates a new Insect.
     *
     * @param position the position
     * @param color    the color
     */
    public Insect(EntityPosition position, InsectColor color) {
        this.entityPosition = position;
        this.color = color;
    }

    /**
     * Gets best direction.
     *
     * @param boardData the board data
     * @param boardSize the board size
     * @return the best direction
     */
    public abstract Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize);

    /**
     * Travel direction int.
     *
     * @param dir       the dir
     * @param boardData the board data
     * @param boardSize the board size
     * @return the int
     */
    public abstract int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize);
}

/**
 * The type Grasshopper.
 */
class Grasshopper extends Insect {
    /**
     * Instantiates a new Grasshopper.
     *
     * @param position the position
     * @param color    the color
     */
    public Grasshopper(EntityPosition position, InsectColor color) {
        super(position, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int maxs = 0;
        Direction maxDirection = Direction.N;

        int sum = 0;
        for (int y = entityPosition.getY(); y > 0; y -= 2) {
            String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
            if (boardData.containsKey(key)) {
                BoardEntity smth = boardData.get(key);
                if (smth instanceof FoodPoint) {
                    sum += ((FoodPoint) smth).value;
                }
            }
        }
        if (maxs < sum) {
            maxDirection = Direction.N;
            maxs = sum;
        }

        sum = 0;
        for (int x = entityPosition.getX(); x <= boardSize; x += 2) {
            String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
            if (boardData.containsKey(key)) {
                BoardEntity smth = boardData.get(key);
                if (smth instanceof FoodPoint) {
                    sum += ((FoodPoint) smth).value;
                }
            }
        }
        if (maxs < sum) {
            maxDirection = Direction.E;
            maxs = sum;
        }

        sum = 0;
        for (int y = entityPosition.getY(); y <= boardSize; y += 2) {
            String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
            if (boardData.containsKey(key)) {
                BoardEntity smth = boardData.get(key);
                if (smth instanceof FoodPoint) {
                    sum += ((FoodPoint) smth).value;
                }
            }
        }
        if (maxs < sum) {
            maxDirection = Direction.S;
            maxs = sum;
        }

        sum = 0;
        for (int x = entityPosition.getX(); x > 0; x -= 2) {
            String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
            if (boardData.containsKey(key)) {
                BoardEntity smth = boardData.get(key);
                if (smth instanceof FoodPoint) {
                    sum += ((FoodPoint) smth).value;
                }
            }
        }
        if (maxs < sum) {
            maxDirection = Direction.W;
            maxs = sum;
        }
        return maxDirection;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;
        if (dir.equals(Direction.N)) {
            for (int y = entityPosition.getY(); y > 0; y -= 2) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.E)) {
            for (int x = entityPosition.getX(); x <= boardSize; x += 2) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.S)) {
            for (int y = entityPosition.getY(); y <= boardSize; y += 2) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.W)) {
            for (int x = entityPosition.getX(); x > 0; x -= 2) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }
        boardData.remove(Utils.positionToString(entityPosition));
        return sum;
    }
}

/**
 * The type Butterfly.
 */
class Butterfly extends Insect implements OrthogonalMoving {
    /**
     * Instantiates a new Butterfly.
     *
     * @param position the position
     * @param color    the color
     */
    public Butterfly(EntityPosition position, InsectColor color) {
        super(position, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int maxs = 0;
        Direction maxDirection = Direction.N;

        int sum = getOrthogonalDirectionVisibleValue(Direction.N, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.N;
            maxs = sum;
        }

        sum = getOrthogonalDirectionVisibleValue(Direction.E, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.E;
            maxs = sum;
        }

        sum = getOrthogonalDirectionVisibleValue(Direction.S, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.S;
            maxs = sum;
        }

        sum = getOrthogonalDirectionVisibleValue(Direction.W, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.W;
        }
        return maxDirection;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelOrthogonally(dir, entityPosition, color, boardData, boardSize);
    }

    @Override
    public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                  Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;

        if (dir.equals(Direction.N)) {
            for (int y = entityPosition.getY(); y > 0; y--) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.E)) {
            for (int x = entityPosition.getX(); x <= boardSize; x++) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.S)) {
            for (int y = entityPosition.getY(); y <= boardSize; y++) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.W)) {
            for (int x = entityPosition.getX(); x > 0; x--) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        }
        return sum;
    }

    @Override
    public int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                  Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;
        if (dir.equals(Direction.N)) {
            for (int y = entityPosition.getY(); y > 0; y--) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.E)) {
            for (int x = entityPosition.getX(); x <= boardSize; x++) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.S)) {
            for (int y = entityPosition.getY(); y <= boardSize; y++) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.W)) {
            for (int x = entityPosition.getX(); x > 0; x--) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }
        boardData.remove(Utils.positionToString(entityPosition));
        return sum;
    }
}

/**
 * The type Ant.
 */
class Ant extends Insect implements OrthogonalMoving, DiagonalMoving {
    /**
     * Instantiates a new Ant.
     *
     * @param entityPosition the entity position
     * @param color          the color
     */
    public Ant(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int maxs = 0;
        Direction maxDirection = Direction.N;

        int sum = getOrthogonalDirectionVisibleValue(Direction.N, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.N;
            maxs = sum;
        }

        sum = getOrthogonalDirectionVisibleValue(Direction.E, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.E;
            maxs = sum;
        }

        sum = getOrthogonalDirectionVisibleValue(Direction.S, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.S;
            maxs = sum;
        }

        sum = getOrthogonalDirectionVisibleValue(Direction.W, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.W;
            maxs = sum;
        }

        sum = getDiagonalDirectionVisibleValue(Direction.NE, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.NE;
            maxs = sum;
        }

        sum = getDiagonalDirectionVisibleValue(Direction.SE, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.SE;
            maxs = sum;
        }

        sum = getDiagonalDirectionVisibleValue(Direction.SW, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.SW;
            maxs = sum;
        }

        sum = getDiagonalDirectionVisibleValue(Direction.NW, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.NW;
        }
        return maxDirection;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        if (dir.equals(Direction.N) || dir.equals(Direction.S) || dir.equals(Direction.W) || dir.equals(Direction.E)) {
            return travelOrthogonally(dir, entityPosition, color, boardData, boardSize);
        } else {
            return travelDiagonally(dir, entityPosition, color, boardData, boardSize);
        }
    }

    @Override
    public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                  Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;

        if (dir.equals(Direction.N)) {
            for (int y = entityPosition.getY(); y > 0; y--) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.E)) {
            for (int x = entityPosition.getX(); x <= boardSize; x++) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.S)) {
            for (int y = entityPosition.getY(); y <= boardSize; y++) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.W)) {
            for (int x = entityPosition.getX(); x > 0; x--) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        }
        return sum;
    }

    @Override
    public int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                  Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;
        if (dir.equals(Direction.N)) {
            for (int y = entityPosition.getY(); y > 0; y--) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.E)) {
            for (int x = entityPosition.getX(); x <= boardSize; x++) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.S)) {
            for (int y = entityPosition.getY(); y <= boardSize; y++) {
                String key = Utils.positionToString(new EntityPosition(entityPosition.getX(), y));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.W)) {
            for (int x = entityPosition.getX(); x > 0; x--) {
                String key = Utils.positionToString(new EntityPosition(x, entityPosition.getY()));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color.equals(color))) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }
        boardData.remove(Utils.positionToString(entityPosition));
        return sum;
    }

    @Override
    public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;
        int y = entityPosition.getY();
        int x = entityPosition.getX();
        if (dir.equals(Direction.NE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.SE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.SW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.NW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        }
        return sum;
    }

    @Override
    public int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;
        int x = entityPosition.getX();
        int y = entityPosition.getY();

        if (dir.equals(Direction.NE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.SE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.SW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.NW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }
        boardData.remove(Utils.positionToString(entityPosition));
        return sum;
    }
}

/**
 * The type Spider.
 */
class Spider extends Insect implements DiagonalMoving {
    /**
     * Instantiates a new Spider.
     *
     * @param entityPosition the entity position
     * @param color          the color
     */
    public Spider(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int maxs = 0;
        Direction maxDirection = Direction.NE;

        int sum = getDiagonalDirectionVisibleValue(Direction.NE, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.NE;
            maxs = sum;
        }

        sum = getDiagonalDirectionVisibleValue(Direction.SE, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.SE;
            maxs = sum;
        }

        sum = getDiagonalDirectionVisibleValue(Direction.SW, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.SW;
            maxs = sum;
        }

        sum = getDiagonalDirectionVisibleValue(Direction.NW, entityPosition, boardData, boardSize);
        if (maxs < sum) {
            maxDirection = Direction.NW;
        }
        return maxDirection;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelDiagonally(dir, entityPosition, color, boardData, boardSize);
    }

    @Override
    public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;
        int y = entityPosition.getY();
        int x = entityPosition.getX();
        if (dir.equals(Direction.NE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.SE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.SW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        } else if (dir.equals(Direction.NW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                    }
                }
            }
        }
        return sum;
    }

    @Override
    public int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                Map<String, BoardEntity> boardData, int boardSize) {
        int sum = 0;
        int x = entityPosition.getX();
        int y = entityPosition.getY();

        if (dir.equals(Direction.NE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.SE)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x + i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.SW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y + i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }

        if (dir.equals(Direction.NW)) {
            for (int i = 0; i <= boardSize; i++) {
                String key = Utils.positionToString(new EntityPosition(x - i, y - i));
                if (boardData.containsKey(key)) {
                    BoardEntity smth = boardData.get(key);
                    if (smth instanceof FoodPoint) {
                        sum += ((FoodPoint) smth).value;
                        boardData.remove(key);
                    } else if (smth instanceof Insect) {
                        if (!(((Insect) smth).color).equals(color)) {
                            boardData.remove(Utils.positionToString(entityPosition));
                            break;
                        }
                    }
                }
            }
        }
        boardData.remove(Utils.positionToString(entityPosition));
        return sum;
    }
}

/**
 * The enum Insect color.
 */
enum InsectColor {
    /**
     * Red insect color.
     */
    RED,
    /**
     * Green insect color.
     */
    GREEN,
    /**
     * Blue insect color.
     */
    BLUE,
    /**
     * Yellow insect color.
     */
    YELLOW;

    /**
     * To color insect color.
     *
     * @param s the s
     * @return the insect color
     * @throws Exception the exception
     */
    public static InsectColor toColor(String s) throws Exception {
        if (s.equals("Red")) {
            return RED;
        } else if (s.equals("Green")) {
            return GREEN;
        } else if (s.equals("Blue")) {
            return BLUE;
        } else if (s.equals("Yellow")) {
            return YELLOW;
        } else {
            throw new InvalidInsectColorException();
        }
    }
}

/**
 * The type Board.
 */
class Board {
    private Map<String, BoardEntity> boardData = new HashMap<>();
    private int size;

    /**
     * Instantiates a new Board.
     *
     * @param boardSize the board size
     */
    public Board(int boardSize) {
        this.size = boardSize;
    }

    /**
     * Add entity.
     *
     * @param entity the entity
     * @throws Exception the exception
     */
    void addEntity(BoardEntity entity) throws Exception {
        String key = Utils.positionToString(entity.entityPosition);
        if (entity instanceof Insect) {
            InsectColor color = ((Insect) entity).color;
            Iterator<Map.Entry<String, BoardEntity>> itr = boardData.entrySet().iterator();

            while (itr.hasNext()) {
                BoardEntity nextEntity = itr.next().getValue();
                if ((nextEntity instanceof Grasshopper) && (entity instanceof Grasshopper)) {
                    if (((Grasshopper) nextEntity).color.equals(color)) {
                        throw new DuplicateInsectException();
                    }
                } else if ((nextEntity instanceof Butterfly) && (entity instanceof Butterfly)) {
                    if (((Butterfly) nextEntity).color.equals(color)) {
                        throw new DuplicateInsectException();
                    }
                } else if ((nextEntity instanceof Spider) && (entity instanceof Spider)) {
                    if (((Spider) nextEntity).color.equals(color)) {
                        throw new DuplicateInsectException();
                    }
                } else if ((nextEntity instanceof Ant) && (entity instanceof Ant)) {
                    if (((Ant) nextEntity).color.equals(color)) {
                        throw new DuplicateInsectException();
                    }
                }
            }
        }
        if (boardData.containsKey(key)) {
            throw new TwoEntitiesOnSamePositionException();
        } else if (entity.entityPosition.getX() > size || entity.entityPosition.getY() > size
                || entity.entityPosition.getX() <= 0 || entity.entityPosition.getY() <= 0) {
            throw new InvalidEntityPositionException();
        } else {
            boardData.put(key, entity);
        }
    }

    /**
     * Get board entity.
     *
     * @param position the position
     * @return the board entity
     */
    BoardEntity getEntity(EntityPosition position) {
        String key = Utils.positionToString(position);
        return boardData.get(key);
    }

    /**
     * Get direction.
     *
     * @param insect the insect
     * @return the direction
     */
    Direction getDirection(Insect insect) {
        Direction direction = insect.getBestDirection(boardData, size);
        return direction;
    }

    /**
     * Get direction sum.
     *
     * @param insect the insect
     * @return the int
     */
    int getDirectionSum(Insect insect) {
        int directionSum = insect.travelDirection(getDirection(insect), boardData, size);
        return directionSum;
    }
}

/**
 * The type Board entity.
 */
abstract class BoardEntity {
    /**
     * The Entity position.
     */
    protected EntityPosition entityPosition;
}

/**
 * The enum Direction.
 */
enum Direction {
    /**
     * North direction.
     */
    N("North"),
    /**
     * East direction.
     */
    E("East"),
    /**
     * South direction.
     */
    S("South"),
    /**
     * West direction.
     */
    W("West"),
    /**
     * North-East direction.
     */
    NE("North-East"),
    /**
     * South-East direction.
     */
    SE("South-East"),
    /**
     * South-West direction.
     */
    SW("South-West"),
    /**
     * North-West direction.
     */
    NW("North-West");
    private String textRepresentation;

    private Direction(String text) {
        this.textRepresentation = text;
    }

    /**
     * Gets text representation of directions.
     *
     * @return the text representation
     */
    public String getTextRepresentation() {
        return textRepresentation;
    }
}

/**
 * The interface Orthogonal moving.
 */
interface OrthogonalMoving {
    /**
     * Gets sum of food point values on inputted orthogonal direction.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the orthogonal direction visible value
     */
    public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                  Map<String, BoardEntity> boardData, int boardSize);

    /**
     * Travel orthogonally.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param color          the color
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the int
     */
    public int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                  Map<String, BoardEntity> boardData, int boardSize);
}

/**
 * The interface Diagonal moving.
 */
interface DiagonalMoving {
    /**
     * Gets sum of food point values on inputted diagonal direction.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the diagonal direction visible value
     */
    public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                Map<String, BoardEntity> boardData, int boardSize);

    /**
     * Travel diagonally int.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param color          the color
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the int
     */
    public int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                Map<String, BoardEntity> boardData, int boardSize);
}

/**
 * The type Utils.
 */
class Utils {
    /**
     * Converts position to string.
     *
     * @param entityPosition the entity position
     * @return the string
     */
    public static String positionToString(EntityPosition entityPosition) {
        return entityPosition.getX() + ", " + entityPosition.getY();
    }
}