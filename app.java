import javax.swin.*;

public class APP{
    public static void main(string[] args) throws Exception{
        int boardwidth = 600;
        int boardheight = boardwidth;

        JFrame frame = new Jframe("snake");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeto(null);
        frame.setResizeable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
