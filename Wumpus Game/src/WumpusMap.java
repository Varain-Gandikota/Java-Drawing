import java.awt.*;
public class WumpusMap {
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_PITS = 10;

    private WumpusSquare[][] grid;
    private int ladderColumn;
    private int ladderRow;
    public WumpusMap(){
        createMap();
    }
    public void createMap(){
        //creates grid
        grid = new WumpusSquare[WumpusMap.NUM_ROWS][WumpusMap.NUM_COLUMNS];
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                grid[i][j] = new WumpusSquare();
            }
        }

        //sets Wumpus location
        int row = generateRandomNumber(0, WumpusMap.NUM_ROWS-1);
        int col = generateRandomNumber(0, WumpusMap.NUM_COLUMNS-1);
        grid[row][col].setWumpus(true);


        //generates pits
        for (int count = 0; count < 10; count++){
            do {
                row = generateRandomNumber(0, WumpusMap.NUM_ROWS-1);
                col = generateRandomNumber(0, WumpusMap.NUM_COLUMNS-1);
            }while (!grid[row][col].isNormalSquare());
            grid[row][col].setPit(true);

        }
        for (row = 0; row < grid.length; row++){
            for (col = 0; col < grid[row].length; col++){
                if (grid[row][col].isPit()){
                    if (row + 1 < grid.length && !grid[row+1][col].isWumpus() && !grid[row+1][col].isPit()){
                        grid[row+1][col].setBreeze(true);
                    }
                    if (row - 1 >= 0 && !grid[row-1][col].isWumpus() && !grid[row-1][col].isPit()){
                        grid[row-1][col].setBreeze(true);
                    }
                    if (col + 1 < grid[row].length && !grid[row][col+1].isWumpus() && !grid[row][col+1].isPit()){
                        grid[row][col+1].setBreeze(true);
                    }
                    if (col - 1 >= 0 && !grid[row][col-1].isWumpus() && !grid[row][col-1].isPit()){
                        grid[row][col-1].setBreeze(true);
                    }
                }
                if (grid[row][col].isWumpus()){
                    if (row + 1 < grid.length && !grid[row+1][col].isWumpus() && !grid[row+1][col].isPit()){
                        grid[row+1][col].setStench(true);
                    }
                    if (row - 1 >= 0 && !grid[row-1][col].isWumpus() && !grid[row-1][col].isPit()){
                        grid[row-1][col].setStench(true);
                    }
                    if (col + 1 < grid[row].length && !grid[row][col+1].isWumpus() && !grid[row][col+1].isPit()){
                        grid[row][col+1].setStench(true);
                    }
                    if (col - 1 >= 0 && !grid[row][col-1].isWumpus() && !grid[row][col-1].isPit()){
                        grid[row][col-1].setStench(true);
                    }
                }
            }
        }

        //generates ladder
        do {
            row = generateRandomNumber(0, WumpusMap.NUM_ROWS-1);
            col = generateRandomNumber(0, WumpusMap.NUM_COLUMNS-1);
        }while (!grid[row][col].isNormalSquare());
        ladderColumn = col;
        ladderRow = row;
        grid[row][col].setLadder(true);

        //generates gold
        do {
            row = generateRandomNumber(0, WumpusMap.NUM_ROWS-1);
            col = generateRandomNumber(0, WumpusMap.NUM_COLUMNS-1);
        }while (grid[row][col].isPit() || grid[row][col].isLadder() );

        grid[row][col].setGold(true);
        System.out.println("gold print: "+ grid[row][col].toString());



    }
    private int generateRandomNumber(int minInclusive, int maxInclusive){
        return (int)((Math.random() * (maxInclusive-minInclusive+1))+minInclusive);
    }

    public WumpusSquare[][] getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid[row].length; col++){
                s += grid[row][col].toString();
            }
            s+="\n";
        }
        return s;
    }

    //copied from depth first search from coding rooms
    public boolean isBeatable(){
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        MyStack<Point> stack = new MyStack<Point>();
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                if (grid[row][col].isLadder())
                {
                    stack.push(new Point(col, row));
                }
            }
        }
        Point endPos = new Point(0, 0);
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                if (grid[row][col].isGold())
                {
                    endPos = new Point(col, row);
                }
            }
        }
        System.out.println("Locaton of start: \nrow " + (int)stack.peek().getY() + "\ncol " + (int)stack.peek().getX());
        System.out.println("Locaton of end: \nrow " + (int)endPos.getY() + "\ncol " + (int)endPos.getX());
        while (!stack.isEmpty())
        {
            Point location = stack.pop();
            visited[(int)location.getY()][(int)location.getX()] = true;
            if ((int)location.getX() == (int)endPos.getX() && (int)location.getY() == (int)endPos.getY())
            {return true;}
            int locX = (int)location.getX();
            int locY = (int)location.getY();
            if ((locX+1) < grid[0].length)
            {
                if (!visited[locY][locX+1] && !grid[locY][locX+1].isPit())
                {stack.push(new Point(locX+1, locY));}
            }
            if (locX-1 >= 0)
            {
                if (!visited[locY][locX-1] && !grid[locY][locX-1].isPit()) {stack.push(new Point(locX-1, locY));}
            }
            if (locY+1 < grid.length)
            {
                if (!visited[locY+1][locX] && !grid[locY+1][locX].isPit()) {stack.push(new Point(locX, locY+1));}
            }
            if (locY-1 >= 0)
            {
                if (!visited[locY-1][locX] && !grid[locY-1][locX].isPit()) {stack.push(new Point(locX, locY-1));}
            }

        }
        return false;
    }

    public int getLadderColumn() {
        return ladderColumn;
    }

    public int getLadderRow() {
        return ladderRow;
    }
}
