import Object.GameObject;
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
    private List<GameObject> objects=new ArrayList<GameObject>();
    public GameClient() {
        this(800, 600);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
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
        Image[] brickImage={Tools.getImage("brick.png")};
        Image[] iTankImage=new Image[8];
        Image[] eTankImage=new Image[8];

        String[] sub={"U.png","D.png","L.png","R.png","LU.png","RU.png","LD.png","RD.png"};
        for(int i=0;i<iTankImage.length;i++){
            iTankImage[i]=Tools.getImage("itank"+sub[i]);
            eTankImage[i]=Tools.getImage("etank"+sub[i]);
        }

        playerTank=new Tank(470,100,Direction.DOWN,iTankImage);
        objects.add(playerTank);

        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                objects.add(new Tank(350+j*80,350+80*i,Direction.UP,true,eTankImage));
            }
        }
        objects.add(new Wall(250,150,true,15,brickImage));
        objects.add(new Wall(150,200,false,15,brickImage));
        objects.add(new Wall(800,200,false,15,brickImage));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getScreenWidth(),getScreenHeight());
        for(GameObject object:objects){
            object.draw(g);
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
