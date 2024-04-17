public class WumpusPlayer {
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;

    private int direction;
    private boolean arrow;
    private boolean gold;
    private int colPosition;
    private int rowPosition;
    public WumpusPlayer(){
        direction = WumpusPlayer.UP;
        arrow = true;
        gold = false;

    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean hasArrow() {
        return arrow;
    }

    public void setHasArrow(boolean arrow) {
        this.arrow = arrow;
    }

    public boolean hasGold() {
        return gold;
    }

    public void setHasGold(boolean gold) {
        this.gold = gold;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }
}
