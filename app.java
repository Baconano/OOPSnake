import javax.swing.*;

public class app {
    public static void main(String[] args) throws Exception{
        int boardwidth = 600;
        int boardheight = boardwidth;

        JFrame frame = new JFrame("snake");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 
        Menu TestMenu = new Menu(boardwidth,boardheight);
        frame.add(TestMenu);
        frame.pack();
        TestMenu.requestFocus();
        
        if(TestMenu.getPicked() == -1){
            frame.remove(TestMenu);
            
        }
        */
        //if(TestMenu.getPicked() != 0 && TestMenu.getPicked() != -1){
            //frame.remove(TestMenu);
            //Version 1: Random Color Change
            //Version 2: Hard, increased speed
            //Version 3: Easy, no shrink fruit
            //Version 5: How it is right now
            SnakeGame snakeGame = new SnakeGame(boardwidth, boardheight,2);
            
            

            frame.add(snakeGame);
            frame.pack();
            snakeGame.requestFocus();
        //}
        
        
        }
        
    }

