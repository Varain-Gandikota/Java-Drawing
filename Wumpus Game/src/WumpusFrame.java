import javax.swing.*;

public class WumpusFrame extends JFrame {
    public WumpusFrame(){
        super("Wumpus Game");
        setLayout(null);
        setSize(800, 900);
        WumpusPanel wp = new WumpusPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(wp);
        setVisible(true);
    }

}
