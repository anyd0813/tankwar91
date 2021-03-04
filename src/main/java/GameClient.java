import Object.Direction;
import Object.GameObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static Object.Direction.UP;

public class GameClient extends JComponent {
    private int screenWidth;
    private int screenHeight;
    private boolean stop;
    public boolean gameOver;
    public int gameScore;

    //玩家坦克
    public PlayerTank playerTank;

    private CopyOnWriteArrayList<GameObject> objects=new CopyOnWriteArrayList<GameObject>();
    public static Image[] bullectImage=new Image[8];
    public static Image[] eTankImage=new Image[8];
    public static Image[] explosionImage=new Image[10];
    public static Image[] iTankImage=new Image[8];
    public static Image[] brickImage=new Image[1];

    public GameClient() {
        this(1024, 800);
    }
    public GameClient(int screenWidth, int screenHeight) {
        com.sun.javafx.application.PlatformImpl.startup(new Runnable() {
            public void run() {
            }
        });
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

    public void addScore(int score) {
        gameScore += score;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }


    public void addGameObject(GameObject object){
        objects.add(object);
    }

    public List<GameObject> getObjects() {
        return objects;
    }



    public void init() {
      //  objects=new CopyOnWriteArrayList<GameObject>();
        brickImage[0]=Tools.getImage("brick.png");
        String[] sub={"U.png","D.png","L.png","R.png","LU.png","RU.png","LD.png","RD.png"};
        for(int i=0;i<iTankImage.length;i++){
            iTankImage[i]=Tools.getImage("itank"+sub[i]);
            eTankImage[i]=Tools.getImage("etank"+sub[i]);
            bullectImage[i]=Tools.getImage("missile"+sub[i]);
        }
        for(int i=0;i<explosionImage.length;i++){
            explosionImage[i]=Tools.getImage(i+".png");
        }
        initGame();
    }
    public void initGame(){
        gameOver=false;
        objects.clear();

        playerTank = new PlayerTank(500, 50, Direction.DOWN,iTankImage);
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){

                objects.add(new EnemyTank(320+j*100,450+100*i, UP,true,eTankImage));
            }
        }

        objects.add(new Wall(250,100,true,15,brickImage));
        objects.add(new Wall(150,200,false,15,brickImage));
        objects.add(new Wall(800,200,false,15,brickImage));

        objects.add(playerTank);

    }
    public void checkGameStatus(){
        if(gameOver){
            return;
        }
        if(!playerTank.isAlive()){
            gameOver=true;
            return;
        }
        boolean gameWin=true;
        for(GameObject object:objects){
            if(object instanceof EnemyTank){
                gameWin=false;
            }
        }
        if(gameWin){
            for(int i=0;i<3;i++){
                for(int j=0;j<4;j++){
                    addGameObject(new EnemyTank(350+j*80,350+80*i, UP,true,eTankImage));
                }
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getScreenWidth(),getScreenHeight());
        for(GameObject object:objects){
            object.draw(g);
        }
    for(GameObject object:objects){
         if(!object.isAlive()){
        objects.remove(object);
         }
    }
        checkGameStatus();
        if (gameOver) {
            g.setFont(new Font(null, Font.BOLD, 100));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 150, 300);

            g.setFont(new Font(null, Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("PRESS F2 TO RESTART", 150, 500);
        }

        g.setFont(new Font(null, Font.PLAIN, 24));
        g.setColor(Color.WHITE);
        g.drawString("分數:" + gameScore, 10, 48);
        g.drawString("Object:" + objects.size(), 10, 80);

        //System.out.println(objects.size());
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
                case KeyEvent.VK_CONTROL:
                    playerTank.fire();
                    break;
            case KeyEvent.VK_A:
                playerTank.superFire();
                break;
            case KeyEvent.VK_F2:
                initGame();
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
