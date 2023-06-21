import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;


public class panel extends JPanel implements ActionListener{
    //1st set the size of the panel

    static final int width =1200;//final bcz we want our panel size
    static final int height =600;
    int unit =50;
    boolean flag =false;
    Random random;
    int score;
    int fx,fy;
    char dir ='R';
    Timer timer;
    // snake size
    int length =3;

    int xsnake[]=new int[288];
    int ysnake[]=new int [288];


    panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        random =new Random();
        this.setFocusable(true); //for array key action
        this.addKeyListener(new key());


        gamestart();
    }
    public void  gamestart(){
        spawnfood();
        flag=true;
        timer =new Timer(160,this);
        timer.start();


    }
    //to asssign random x and y cordinate to the food particles
    public void spawnfood(){
        fx =random.nextInt(width / unit)*50;
        fy =random.nextInt(height/unit)*50;

    }
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);

    }
    public void draw(Graphics graphic){
        //when the game is running

        if(flag){
            //drawing the food particles

            graphic.setColor(Color.yellow);
            graphic.fillOval(fx,fy,unit,unit);
           // to draw the snake

           for(int i =0;i<length;i++){
               graphic.setColor(Color.blue);
               graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
           }
           //for drawing the score element

            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("COMIC SANS MS",Font.BOLD,40));
            FontMetrics fme =getFontMetrics(graphic.getFont());
            graphic.drawString("Score : "+score,(width - fme.stringWidth("Score : "+score))/2,graphic.getFont().getSize());
        }
        else{

            //when the game is stop

            //to display final score

            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("COMIC SANS MS",Font.BOLD,40));
            FontMetrics fme =getFontMetrics(graphic.getFont());
            graphic.drawString("Score : "+score,(width - fme.stringWidth("Score : "+score))/2,graphic.getFont().getSize());

            //to display game over

            graphic.setColor(Color.RED);
            graphic.setFont(new Font("COMIC SANS MS",Font.BOLD,80));
             fme =getFontMetrics(graphic.getFont());
            graphic.drawString("Game Over!",(width - fme.stringWidth("Game Over!"))/2,height/2);

            // to display replay prompt

            graphic.setColor(Color.GREEN);
            graphic.setFont(new Font("COMIC SANS MS",Font.BOLD,40));
             fme =getFontMetrics(graphic.getFont());
            graphic.drawString("Press R to replay",(width - fme.stringWidth("Press R to replay"))/2,height/2 +150);

        }
    }
    // give the direction
    public void move(){
        for(int i =length;i>0;i--){
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        switch(dir){
            case 'R' :xsnake[0] =xsnake[0]+unit;
                break;
            case 'L' :xsnake[0] =xsnake[0]-unit;
                break;
            case 'U' :ysnake[0] =ysnake[0]-unit;
                break;
            case 'D' :ysnake[0] =ysnake[0]+unit;
                break;

        }
    }
    // scoring area
    public void  foodeaten(){
        //when the food and snake coinside then snake eaten the food
        if((xsnake[0]==fx)&& ysnake[0]==fy){
            length++;
            score++;
            spawnfood();
        }
    }


    // set boundries for snake
    public void checkhit(){
        //if snake hit the boundries and hit to own body then the condition will be false or  we can say game over

            if (ysnake[0] < 0) {
                 flag =false;
            }

            if (ysnake[0] > 600) {
                 flag =false;
            }

            if (xsnake[0] < 0) {
                 flag =false;
            }

            if (ysnake[0] > 1200) {
                 flag =false;
            }


//    snake hit own body
        for(int i =length;i>0;i--) {
            if (xsnake[0] == xsnake[i] && ysnake[0] == ysnake[i]) {
                flag = false;
            }
        }
        if(flag==false){
            timer.stop();
        }

    }

    //key direction
    public class key extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:

                    if (dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:

                    if (dir != 'L') {
                        dir = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:

                    if (dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:

                    if (dir != 'U') {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_R:

                    score = 0;
                    length = 3;
                    dir = 'R';
                    Arrays.fill(xsnake, 0);
                    Arrays.fill(ysnake, 0);
                    gamestart();
            }

        }
    }

// action perform
    public void actionPerformed(ActionEvent evt){
        if(flag){
            move();
            foodeaten();
            checkhit();
        }
        repaint();
    }
}


