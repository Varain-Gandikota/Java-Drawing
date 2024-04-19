import javax.swing.*;

public class WumpusFrame extends JFrame {
    public WumpusFrame(){
        super("Wumpus Game");
        setLayout(null);
        setSize(800, 900);
        WumpusPanel wp = new WumpusPanel();
        add(wp);
        setVisible(true);
    }

}
