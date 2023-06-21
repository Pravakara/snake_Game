import javax.swing.*;

public class Frame extends JFrame {
    //between frame their is panel
    Frame(){
        this.setTitle("Snake");
        this.add(new panel());
        this.setVisible(true);
        this.setResizable(false);//by default it is true so we make our coding false bcz we want fix size
        this.pack();
    }
}

