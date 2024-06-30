import javax.swing.*;

public class Frame extends JFrame {
    Frame() {

        this.add(new Panel());
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("SNAKE GAME");

        this.pack();
        this.setLocationRelativeTo(null);

    }

}
