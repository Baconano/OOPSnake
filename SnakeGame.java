import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
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
    
    //bad food
    Tile badfood;

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
    int randomNum = 0;
    SnakeGame(int boardWidth, int boardHeight, int version) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        Version = version;
        checkVersion(version);
        if(v1)
            randomize();

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10,10);
        badfood = new Tile(10,10);
        random = new Random();
        placeFood();

        if(v2)
            delay = 50;
        if(v3)
            delay = 200;
        velocityX = 0;
        velocityY =0;
        gameLoop = new Timer(delay, this);
        gameLoop.start();
    }
    public void randomize(){
        randomNum = random.nextInt(3);
    }

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

        //snake head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize,snakeHead.y*tileSize,tileSize ,tileSize);

        //snake body
        for (int i=0; i< snakeBody.size(); i++){
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
    }
    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardWidth/tileSize);
    }

    public void placeBadFood(){
        badfood.x=random.nextInt(boardWidth/tileSize);
        badfood.y=random.nextInt(boardWidth/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    public void move(){
        //get Food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        if(collision(snakeHead, badfood)){
            if (snakeBody.size() == 0) {
                // Game over if snake's length is 1
                gameOver = true;
            } else {
                snakeBody.remove(0); // Subtract from the snake's length
            }
            placeBadFood();
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
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if (gameOver){
            gameLoop.stop();
        }
    }
    
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
    }
    //Do Not Need
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}