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

        
        
        //JFrame frame2 = new JFrame("test menu");
        Menu TestMenu = new Menu(boardwidth,boardheight);
        frame.add(TestMenu);
        frame.pack();
        TestMenu.requestFocus();
        
        
        if(TestMenu.getPicked() != -1){
        SnakeGame snakeGame = new SnakeGame(boardwidth, boardheight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
        }
        
        }
        
    }

