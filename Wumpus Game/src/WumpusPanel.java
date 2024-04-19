import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class WumpusPanel extends JPanel implements KeyListener {
    public static final int PLAYING = 0;
    public static final int DEAD = 1;
    public static final int WON = 2;

    private int status;
    private WumpusPlayer player;
    private WumpusMap map;

    private BufferedImage floor;
    private BufferedImage arrow;
    private BufferedImage fog;
    private BufferedImage gold;
    private BufferedImage ladder;
    private BufferedImage pit;
    private BufferedImage breeze;
    private BufferedImage wumpus;
    private BufferedImage deadWumpus;
    private BufferedImage stench;
    private BufferedImage playerUp;
    private BufferedImage playerDown;
    private BufferedImage playerLeft;
    private BufferedImage playerRight;
    private BufferedImage buffer;

    private String message;


    public WumpusPanel(){
        super();
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        setLayout(null);
        setBounds(0,0, 800, 900);
        try{
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            floor = ImageIO.read((new File("Wumpus World Images\\Floor.gif")));
            arrow = ImageIO.read(new File("Wumpus World Images/arrow.gif"));
            fog = ImageIO.read(new File("Wumpus World Images/black.GIF"));
            gold = ImageIO.read(new File("Wumpus World Images/gold.gif"));
            ladder = ImageIO.read(new File("Wumpus World Images/ladder.gif"));
            pit = ImageIO.read(new File("Wumpus World Images/pit.gif"));
            breeze = ImageIO.read(new File("Wumpus World Images/breeze.gif"));
            wumpus = ImageIO.read(new File("Wumpus World Images/wumpus.gif"));
            deadWumpus = ImageIO.read(new File("Wumpus World Images/deadwumpus.GIF"));
            stench = ImageIO.read(new File("Wumpus World Images/stench.gif"));
            playerUp = ImageIO.read(new File("Wumpus World Images/playerUp.png"));
            playerDown = ImageIO.read(new File("Wumpus World Images/playerDown.png"));
            playerLeft = ImageIO.read(new File("Wumpus World Images/playerLeft.png"));
            playerRight = ImageIO.read(new File("Wumpus World Images/playerRight.png"));

        }catch (Exception e){
            e.printStackTrace();
        }
        reset();
    }
    public void reset(){
        status = WumpusPanel.PLAYING;
        message = "you bump into a ladder";
        do{
            map = new WumpusMap();
            System.out.println(map + "\n"+ map.isBeatable());
        }while(!map.isBeatable());
        map.getGrid()[map.getLadderRow()][map.getLadderColumn()].setVisited(true);
        player = new WumpusPlayer();
        player.setRowPosition(map.getLadderRow());
        player.setColPosition(map.getLadderColumn());

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics g1 = buffer.getGraphics();
        g1.setColor(Color.GRAY);
        g1.fillRect(0, 0, getWidth(), getHeight());
        for (int row = 0; row < map.getGrid().length; row++){
            for (int col = 0; col < map.getGrid()[row].length; col++){
                drawWumpusSquare(g1, floor, row, col);

                if (map.getGrid()[row][col].isLadder())
                    drawWumpusSquare(g1, ladder, row, col);

                if (map.getGrid()[row][col].isGold())
                    drawWumpusSquare(g1, gold, row, col);

                if (map.getGrid()[row][col].isPit())
                    drawWumpusSquare(g1, pit, row, col);

                if (map.getGrid()[row][col].isWumpus())
                    drawWumpusSquare(g1, wumpus, row, col);

                if (map.getGrid()[row][col].isBreeze())
                    drawWumpusSquare(g1, breeze, row, col);

                if (map.getGrid()[row][col].isStench())
                    drawWumpusSquare(g1, stench, row, col);

                if (map.getGrid()[row][col].isDeadWumpus())
                    drawWumpusSquare(g1, deadWumpus, row, col);

                if (!map.getGrid()[row][col].isVisited())
                    drawWumpusSquare(g1, fog, row, col);

                if (player.getRowPosition() == row && player.getColPosition() == col){
                    switch (player.getDirection()){
                        case WumpusPlayer.UP -> {
                            drawWumpusSquare(g1, playerUp, row, col);
                        }
                        case WumpusPlayer.DOWN -> {
                            drawWumpusSquare(g1, playerDown, row, col);
                        }
                        case WumpusPlayer.LEFT -> {
                            drawWumpusSquare(g1, playerLeft, row, col);
                        }
                        case WumpusPlayer.RIGHT -> {
                            drawWumpusSquare(g1, playerRight, row, col);
                        }
                    }
                }

            }
        }
        g1.setColor(Color.BLACK);
        g1.fillRect(70, 675, 325, 150);

        g1.setColor(Color.BLACK);
        g1.fillRect(405, 675, 315, 150);

        g1.setColor(new Color(139, 0, 0));
        g1.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g1.drawString("Messages:", 410, 700);

        g1.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        g1.drawString(message, 410, 750);

        g1.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g1.drawString("Inventory:", 75, 700);






        g.drawImage(buffer, 0, 0, null);
        if (player.hasGold())
            g.drawImage(gold, 220, 735, 80, 80, null);
        if (player.hasArrow())
            g.drawImage(arrow, 85, 735, 80, 80, null);

    }
    public void drawWumpusSquare(Graphics g1, BufferedImage img, int row, int col){
        g1.drawImage(img, col*65+70,row*65+10, 65, 65, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {



    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        System.out.println(key);
        switch (key){
            case 'w':
                if (status == WumpusPanel.PLAYING && player.getRowPosition()-1 >= 0)
                {
                    player.setRowPosition(player.getRowPosition()-1);
                    player.setDirection(WumpusPlayer.UP);
                    map.getGrid()[player.getRowPosition()][player.getColPosition()].setVisited(true);
                    repaint();
                }
                break;
            case 's':
                if (status == WumpusPanel.PLAYING && player.getRowPosition()+1 < map.getGrid().length)
                {
                    player.setRowPosition(player.getRowPosition()+1);
                    player.setDirection(WumpusPlayer.DOWN);
                    map.getGrid()[player.getRowPosition()][player.getColPosition()].setVisited(true);
                    repaint();
                }
                break;
            case 'a':
                if (status == WumpusPanel.PLAYING && player.getColPosition()-1 >= 0)
                {
                    player.setColPosition(player.getColPosition()-1);
                    player.setDirection(WumpusPlayer.LEFT);
                    map.getGrid()[player.getRowPosition()][player.getColPosition()].setVisited(true);
                    repaint();
                }
                break;
            case 'd':
                if (status == WumpusPanel.PLAYING && player.getColPosition()+1 < map.getGrid()[0].length)
                {
                    player.setColPosition(player.getColPosition()+1);
                    player.setDirection(WumpusPlayer.RIGHT);
                    map.getGrid()[player.getRowPosition()][player.getColPosition()].setVisited(true);
                    repaint();
                }
                break;
            case 'r':
                if (status != WumpusPanel.PLAYING)
                {
                    reset();
                    repaint();
                }
                break;
            case 'e':
                if (status == WumpusPanel.PLAYING && map.getGrid()[player.getRowPosition()][player.getColPosition()].isGold())
                {
                    player.setHasGold(true);
                    map.getGrid()[player.getRowPosition()][player.getColPosition()].setGold(false);
                    repaint();
                }
                break;
            case 'n':
                if (status != WumpusPanel.PLAYING)
                {
                    reset();
                    repaint();
                }
                break;
            case 'i':
                if (player.hasArrow() && player.getRowPosition()-1 >= 0 && map.getGrid()[player.getRowPosition()-1][player.getColPosition()].isWumpus()){
                    map.getGrid()[player.getRowPosition()-1][player.getColPosition()].setWumpus(false);
                    map.getGrid()[player.getRowPosition()-1][player.getColPosition()].setDeadWumpus(true);
                    message = "You hear a scream";
                    player.setHasArrow(false);
                    repaint();
                    return;
                }
                player.setHasArrow(false);
                repaint();
                break;
            case 'k':
                if (player.hasArrow() && player.getRowPosition()+1 < map.getGrid().length && map.getGrid()[player.getRowPosition()+1][player.getColPosition()].isWumpus()){
                    map.getGrid()[player.getRowPosition()+1][player.getColPosition()].setWumpus(false);
                    map.getGrid()[player.getRowPosition()+1][player.getColPosition()].setDeadWumpus(true);
                    message = "You hear a scream";
                    player.setHasArrow(false);
                    repaint();
                    return;
                }
                player.setHasArrow(false);
                repaint();
                break;
            case 'j':
                if (player.hasArrow() && player.getColPosition()-1 >= 0 && map.getGrid()[player.getRowPosition()][player.getColPosition()-1].isWumpus()){
                    map.getGrid()[player.getRowPosition()][player.getColPosition()-1].setWumpus(false);
                    map.getGrid()[player.getRowPosition()][player.getColPosition()-1].setDeadWumpus(true);
                    message = "You hear a scream";
                    player.setHasArrow(false);
                    repaint();
                    return;
                }
                player.setHasArrow(false);
                repaint();
                break;
            case 'l':
                if (player.hasArrow() && player.getColPosition()+1 < map.getGrid()[0].length && map.getGrid()[player.getRowPosition()][player.getColPosition()+1].isWumpus()){
                    map.getGrid()[player.getRowPosition()][player.getColPosition()+1].setWumpus(false);
                    map.getGrid()[player.getRowPosition()][player.getColPosition()+1].setDeadWumpus(true);
                    message = "You hear a scream";
                    player.setHasArrow(false);
                    repaint();
                    return;
                }
                player.setHasArrow(false);
                repaint();
                break;

        }
        if (map.getGrid()[player.getRowPosition()][player.getColPosition()].isNormalSquare() && status == WumpusPanel.PLAYING)
            message = "";
        if (map.getGrid()[player.getRowPosition()][player.getColPosition()].isBreeze() && status == WumpusPanel.PLAYING)
            message = "You feel a breeze";
        if ((map.getGrid()[player.getRowPosition()][player.getColPosition()].isStench() ||
                (map.getGrid()[player.getRowPosition()][player.getColPosition()].isDeadWumpus()) && status == WumpusPanel.PLAYING))
            message = "You smell a putrid stench";
        if (map.getGrid()[player.getRowPosition()][player.getColPosition()].isLadder() && status == WumpusPanel.PLAYING)
            message = "You bump into a ladder";
        if (map.getGrid()[player.getRowPosition()][player.getColPosition()].isGold() && status == WumpusPanel.PLAYING)
            message = "You see a glimmer. Press [E] to pick it up";
        if (map.getGrid()[player.getRowPosition()][player.getColPosition()].isWumpus() && status == WumpusPanel.PLAYING)
        {
            message = "You are eaten by the Wumpus.\nPress [R] to restart";
            status = WumpusPanel.DEAD;
        }
        if (map.getGrid()[player.getRowPosition()][player.getColPosition()].isPit() && status == WumpusPanel.PLAYING)
        {
            message = "You fell down a pit to your death. Press [R] to restart";
            status = WumpusPanel.DEAD;
            repaint();
        }
        if (map.getGrid()[player.getRowPosition()][player.getColPosition()].isLadder() && player.hasGold() && status == WumpusPanel.PLAYING)
        {
            message = "You won. Press [N] for new game";
            status = WumpusPanel.WON;
            repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
