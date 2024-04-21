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

        
            //Version 1: Easy mode, only red fruit and slower speed
            //Version 2: Medium mode, red fruit + shrink fruit, normal speed of 150
            //Version 3: Hard mode, all fruits (death) and increased speed
            //Color Change: Randomize color change without letting user know
            SnakeGame snakeGame = new SnakeGame(boardwidth, boardheight,4);
            
            

            frame.add(snakeGame);
            frame.pack();
            snakeGame.requestFocus();
        
        
        }
        
    }

