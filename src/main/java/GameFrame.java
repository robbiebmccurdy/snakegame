import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

    GameFrame(){

        JButton button = new JButton("Button");
        button.setBounds(200,100,100,50);

        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.add(button);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
