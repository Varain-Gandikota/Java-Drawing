
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
        int s = generateRandomNumber(0, 9);

    }
    public int generateRandomNumber(int minInclusive, int maxInclusive){
        return (int)((Math.random() * (maxInclusive-minInclusive+1))+minInclusive);
    }
}
