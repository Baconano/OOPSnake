import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener,KeyListener,MouseListener{
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x =x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;
    //Verion Tiles;
    Tile Easy;
    Tile Medium;
    Tile Hard;
    //bad food
    Tile badfood;
    Tile instantfood;
    Tile color;
    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver=false;

    int Version;
    boolean v1 = false;
    boolean v2 = false;
    boolean v3 = false;
    int delay = 100;
    Color randomColor = Color.green;
    Color randomColorKeep = Color.white;
    int r = 0;
    int g = 0;
    int b = 0;
    SnakeGame(int boardWidth, int boardHeight, int version) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);

        Version = version;
        checkVersion(version);
        if(v1)
            randomize();
        //Game objects
        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10,100);
        badfood = new Tile(10,101);
        instantfood = new Tile(10,102);
        //Tools
        random = new Random();
        //Options
        Easy = new Tile(10,5);
        Medium = new Tile(10,10);
        Hard = new Tile(10,15);
        color = new Tile(20,5);
        
        
        //Speed taken
        if(v2)
            delay = 50;
        if(v3)
            delay = 200;
        velocityX = 0;
        velocityY =0;
        
    }
    //Random Color Given, Optimized in mouse clicks
    public void randomize(){
        r = (int)(Math.random()*155)+100;
        g = (int)(Math.random()*155)+100;
        b = (int)(Math.random()*155)+100;
        randomColor = new Color(r,g,b);
        
        if(r == 0){
            //System.out.println("Orange chosen");
            randomColor = Color.orange;
        }
        if(r == 1){
            randomColor = Color.pink;
            //System.out.println("Pink chosen");

        }
        if(r == 2){
            //System.out.println("Cyan chosen");
            randomColor = Color.cyan;
        }
        
    }
    //Frame paint (Game)
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //grid
        for (int i = 0 ; i <boardWidth/tileSize; i++){
            //(x1,y1,x2,y2)
            g.drawLine(i*tileSize,0, i*tileSize,boardHeight);
            g.drawLine(0,i*tileSize,boardWidth,i*tileSize);
        }
        //food
        g.setColor(Color.red);
        g.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);

        //bad food
        g.setColor(Color.blue);
        g.fillRect(badfood.x*tileSize,badfood.y*tileSize,tileSize,tileSize);
        //Instant dead food
        g.setColor(Color.orange);
        g.fillRect(instantfood.x*tileSize,instantfood.y*tileSize,tileSize,tileSize);
        //snake head
        g.setColor(randomColor);
        g.fillRect(snakeHead.x*tileSize,snakeHead.y*tileSize,tileSize ,tileSize);

        //snake body
        for (int i=0; i< snakeBody.size(); i++){
            //g.setColor(Color.green);
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x *tileSize, snakePart.y*tileSize,tileSize,tileSize);
        }
        //Score
        g.setFont(new Font("Arial",Font.PLAIN,10));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " +String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        }
        else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        }
        //Filling in the rectangle colors
        g.setColor(Color.red);
        g.fillRect(Easy.x*tileSize,Easy.y*tileSize, tileSize, tileSize);
        g.setColor(Color.blue);
        g.fillRect(Medium.x*tileSize, Medium.y*tileSize, tileSize, tileSize);
        g.setColor(Color.orange);
        g.fillRect(Hard.x*tileSize, Hard.y*tileSize, tileSize, tileSize);
        g.setColor(randomColorKeep);
        g.fillRect(color.x*tileSize,color.y*tileSize,tileSize,tileSize);
    
    
            //Score
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,15));
            //Adding text till the game mode is chosen
            if(!v1 && !v2 && !v3){
                //info
                g.drawString("Snake Game",boardWidth/2  - 42, tileSize);
                g.drawString("The green square is you the \"the player\"",boardWidth/3 - 15, tileSize+30);
                g.drawString("The red square is food to grow",boardWidth/3 + 10, tileSize +48);
                g.drawString("The blue square will shrink you",boardWidth/3 + 10, tileSize + 66);
                g.drawString("The orange square will kill you",boardWidth/3 + 10, tileSize + 84 );
                //Position of text to reference the buttons
                g.drawString("Easy Mode", 250+45,125+18);
                g.drawString("Medium Mode", 250+45, 250+18);
                g.drawString("Hard Mode", 250+45, 375+18);
                g.drawString("Random Color", 470, 168);
                g.drawString("Change", 485,186);
            }
        }
    //Food random
    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardWidth/tileSize);
    }
    //Shrinking Food
    public void placeBadFood(){
        badfood.x=random.nextInt(boardWidth/tileSize);
        badfood.y=random.nextInt(boardWidth/tileSize);
    }
    //Death Food
    public void placeInstantFood(){
        instantfood.x=random.nextInt(boardWidth/tileSize);
        instantfood.y=random.nextInt(boardWidth/tileSize);
    }
    //Colliding
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    //Snake movement
    public void move(){
        //get Food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
            //If medium
            if(v2 == true){
                placeBadFood();
            }
            //If hard
            if(v3 == true){
                placeBadFood();
                placeInstantFood();
            }
        }
        //Shrink food detection
        if(collision(snakeHead, badfood)){
            if (snakeBody.size() == 0) {
                // Game over if snake's length is 1
                gameOver = true;
            } else {
                snakeBody.remove(0); // Subtract from the snake's length
            }
            placeBadFood();
        }
        //Death food detection
        if(collision(snakeHead,instantfood)){
            gameOver=true;
        }
        //Snake Body
        for (int i= snakeBody.size()-1;i >=0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i ==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //SnakeHead
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (int i = 0 ; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //collide with snake head
            if(collision(snakeHead, snakePart)){
                gameOver =true;
            }
        }
        //Checking for borders
        if (snakeHead.x*tileSize<0|| snakeHead.x*tileSize >boardWidth||
            snakeHead.y*tileSize <0 || snakeHead.y*tileSize >boardHeight){
            gameOver =true;
        }
    }
    public void checkVersion(int v){
        if(v == 1)
            v1 = true;
        if(v ==2)
            v2= true;
        if(v==3)
            v3=true;
    }
    //Game running
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if (gameOver){
            gameLoop.stop();
        }
    }
    //Snake movement
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY !=-1){
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX !=-1){
            velocityX = 1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX =-1;
            velocityY =0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_1){
            badfood = new Tile(10,10);
            instantfood = new Tile(20,20);
        }
        else if(e.getKeyCode() == KeyEvent.VK_2){
            r = (int)(Math.random()*155)+100;
            g = (int)(Math.random()*155)+100;
            b = (int)(Math.random()*155)+100;
            randomColor = new Color(r,g,b);
        }
    }
    //Picking gamemode
    @Override
    public void mouseClicked(MouseEvent e)
    {
    int mouseX=e.getX();
    int mouseY=e.getY();
    //Pick decision mouse
    //First 4 lines of each if repositions used assets outside out of mouse click range
    //Next lines respective to difficulty, game starts
    if (mouseX >= Easy.x*tileSize && mouseX <(Easy.x+1)*tileSize && mouseY >= Easy.y*tileSize && mouseY < (Easy.y+1) *tileSize){
        Easy = new Tile(100,100);
        Medium = new Tile (100, 100);
        Hard = new Tile (100, 100);
        color = new Tile(100,100);
        delay = 150;
        v1 =true ;
        v2 = false;
        v3 = false;
        gameLoop = new Timer(delay, this);
        gameLoop.start();
        placeFood();
    }else if (mouseX >= Medium.x*tileSize && mouseX <(Medium.x+1)*tileSize && mouseY >= Medium.y*tileSize && mouseY < (Medium.y+1) *tileSize){
        Easy = new Tile(100,100);
        Medium = new Tile (100, 100);
        Hard = new Tile (100, 100);
        color = new Tile(100,100);
        v1 = false;
        v2= true ;
        v3 = false;
        gameLoop = new Timer(delay, this);
        gameLoop.start();
        placeFood();
        placeBadFood();
    }else if(mouseX >= Hard.x*tileSize && mouseX <(Hard.x+1)*tileSize && mouseY >= Hard.y*tileSize && mouseY < (Hard.y+1) *tileSize){
        Easy = new Tile(100,100);
        Medium = new Tile (100, 100);
        Hard = new Tile (100, 100);
        color = new Tile(100,100);
        delay= 50;
        v3= true;
        gameLoop = new Timer(delay, this);
        gameLoop.start();
        placeFood();
        placeBadFood();
        placeInstantFood();
    }else if(mouseX >= color.x*tileSize && mouseX <(color.x+1)*tileSize && mouseY >= color.y*tileSize && mouseY < (color.y+1) *tileSize){
        r = (int)(Math.random()*155)+100;
        g = (int)(Math.random()*155)+100;
        b = (int)(Math.random()*155)+100;
        randomColor = new Color(r,g,b);
        randomColorKeep = new Color(r,g,b);
    }
}    
    //NOT NEEDED, DO NOT MODIFY
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseReleased(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){
    }
    @Override
    public void mouseExited(MouseEvent e){
    }
}