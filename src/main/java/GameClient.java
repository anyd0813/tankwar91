import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends JComponent {
    private int screenWidth;
    private int screenHeight;
    private boolean stop;

    //玩家坦克
    public Tank playerTank;
    private List<Tank> enemyTanks=new ArrayList<Tank>();
    private List<Wall> walls=new ArrayList<Wall>();
    public GameClient() {
        this(800, 600);
    }


    public GameClient(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        init();
        new Thread(new Runnable() {
            public void run() {
                while (!stop) {
                    GameClient.this.repaint();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void init() {

        playerTank=new Tank(470,100,Direction.DOWN);
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                enemyTanks.add(new Tank(350+j*80,350+80*i,Direction.UP,true));
            }
        }
        walls.add(new Wall(250,150,true,15));
        walls.add(new Wall(150,200,false,15));
        walls.add(new Wall(800,200,false,15));
    }

    @Override
    protected void paintComponent(Graphics g) {

        playerTank.draw(g);
        for(Tank tank:enemyTanks){
            tank.draw(g);
        }
        for(Wall wall:walls){
            wall.draw(g);
        }
    }

    public void keyPressed(KeyEvent e) {
        boolean[] dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs[0] = true;
                break;
            case KeyEvent.VK_DOWN:
                dirs[1] = true;
                break;
            case KeyEvent.VK_LEFT:
                dirs[2] = true;
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3] = true;
                break;


        }

    }

    public void keyRelease(KeyEvent e) {
        boolean[] dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs[0] = false;
                break;
            case KeyEvent.VK_DOWN:
                dirs[1] = false;
                break;
            case KeyEvent.VK_LEFT:
                dirs[2] = false;
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3] = false;
                break;

        }
    }
}
