
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class Menu extends JPanel implements ActionListener,KeyListener{
    //Taken from SnakeGame.java    
    private class Tile{
            int x;
            int y;

            Tile(int x, int y){
                this.x = x;
                this.y = y;
            }
        }
    int boardWidth;
    int boardHeight;
    int Size = 25;

    //Mouse Can be changed (green tile)
    Tile mouse;
    
    //Tile on where the menu option will be at (white tile)
    Tile startOption;
    Tile quitOption;
    Tile diffOption;

    //Inside diff Option
    Tile colorChange;
    Tile hardMode;
    Tile easyMode;
    
    //Extra Choice, if 1 then color change, if 2 then hard more, if 3 then easy mode, otherwise no change
    //If -1 then it does not load game
    int pick;
    //Menu choosing
    int velocityX;
    int velocityY;
    boolean quit = false;
    boolean start = false;
    boolean diffMenu = false;
    Timer keep;

    Menu(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        mouse = new Tile(3,3);
        startOption = new Tile(boardWidth/2-65,boardHeight/3 -18);
        diffOption = new Tile(boardWidth/2-93, boardHeight/2 - 18);
        quitOption = new Tile(boardWidth/2-63, 4*boardHeight/6 - 18);

        colorChange = new Tile(boardWidth, boardHeight);
        hardMode = new Tile(boardWidth, boardHeight);
        easyMode = new Tile(boardWidth, boardHeight);

        velocityX = 0;
        velocityY =0;

        keep = new Timer(100, this);
        keep.start();
    }
    //Taken from SnakeGame.java
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawMenu(g);
    }
    public void MenuExpansion(){
        colorChange.x = boardWidth/2 - 40;
        colorChange.y = boardHeight/2;
        hardMode.x = boardWidth/2 -40;
        hardMode.y = boardWidth/3;
        easyMode.x = boardWidth/2 - 40;
        easyMode.y = 2 * boardWidth/3;
    }
    //Drawing menu 
    public void drawMenu(Graphics g){
        //grid from SnakeGame.java also
        for (int i = 0 ; i <boardWidth/Size; i++){
            //(x1,y1,x2,y2)
            g.drawLine(i*Size,0, i*Size,boardHeight);
            g.drawLine(0,i*Size,boardWidth,i*Size);
        }
        //Mouse
        g.setColor(Color.green);
        g.fillRect(mouse.x*Size,mouse.y*Size,Size ,Size);
        
        //Menu Pick options
        g.setColor(Color.red);
        g.fillRect(boardWidth/2-65,boardHeight/3 -18, Size, Size);
        g.setColor(Color.blue);
        g.fillRect(boardWidth/2-93, boardHeight/2 - 18, Size, Size);
        g.setColor(Color.orange);
        g.fillRect(boardWidth/2-63, 4*boardHeight/6 - 18, Size, Size);
        

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Snake Game",boardWidth/2  - 42, Size);
        //g.setFont(new Font("Arial", Font.PLAIN, 15))
        g.drawString("The green square is you the \"the player\"",boardWidth/3 - 15, Size+30);
        g.drawString("The red square is food to grow",boardWidth/3 + 10, Size +48);
        g.drawString("The blue square will shrink you",boardWidth/3 + 10, Size + 66);
        g.drawString("The orange square will kill you",boardWidth/3 + 10, Size + 84 );
        if(quit){
            g.drawString("Bye Bye!", boardWidth/2, boardHeight/2);
        }
        else if (diffMenu){
            MenuExpansion();
            g.fillRect(colorChange.x*Size, colorChange.y*Size, Size, Size);
            g.fillRect(hardMode.x*Size, hardMode.y*Size, Size, Size);
            g.fillRect(easyMode.x*Size, easyMode.y*Size, Size, Size);
            g.drawString("Any picks will start the game automatically", boardWidth/2, Size);
            g.drawString("Random color change", boardWidth/2, boardHeight/2);
            g.drawString("Hard mode (increased speed)" , boardWidth/2, boardHeight/3);
            g.drawString("Easy mode (no blue fruits)" , boardWidth/2, boardHeight/4);
        }
        else{
            g.setColor(Color.white);
            g.drawString("Start" , boardWidth/2-20, boardHeight/3);
            g.drawString("Extra Options" , boardWidth/2-48, boardHeight/2);
            g.drawString("Quit" , boardWidth/2-18, 4*boardHeight/6);
        }
    }
    
    //Collision logic borrowed from SnakeGame.java
    //Change collision
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x > tile2.x-1 && tile1.x < tile2.x+Size;
    }
    public int menuChosen(){
        if(collision(mouse,colorChange)){
            start = true;
            return 1;
        }
        else if (collision(mouse, hardMode)){
            start = true;
            return 2;
        }
        else if (collision(mouse,easyMode)){
            start = true;
            return 3;
        }
        else    
            return 4;
    }
    public void move(){
        //Picks
        if(collision(mouse, quitOption)){
            quit = true;
            pick = -1;
        }
        if(collision(mouse, startOption)){
            start = true;
        }
        if(collision(mouse, diffOption)){
            diffMenu = true;
        }

        //Extra picks
        if(diffMenu){
           pick = menuChosen();
        }

        mouse.x += velocityX;
        mouse.y += velocityY;

    }
    boolean menuDies(){
        if(quit || start)
            return true;
        else
            return false;
    }
    public int getPicked(){
        return pick;
    }
    //SnakeGame.java
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if (menuDies()){
            keep.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP){
            synchronized(mouse){
                velocityY = -1;
                velocityX = 0;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            synchronized(mouse){
                velocityY = 1;
                velocityX = 0;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT ){
            synchronized(mouse){
                velocityX = 1;
                velocityY = 0;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT ){
            synchronized(mouse){
                velocityX =-1;
                velocityY = 0;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){
            synchronized(mouse){
                velocityY = 0;
                velocityX = 0;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            synchronized(mouse){
                velocityY = 0;
                velocityX = 0;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT ){
            synchronized(mouse){
                velocityX = 0;
                velocityY = 0;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT ){
            synchronized(mouse){
                velocityX = 0;
                velocityY = 0;
            }
        }
    }
}
