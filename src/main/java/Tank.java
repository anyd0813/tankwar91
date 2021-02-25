import Object.GameObject;
import javax.swing.*;
import java.awt.*;

public class Tank extends GameObject {
   private int x;
   private int y;
   private Direction direction;
   private int speed;
   private boolean[] dirs=new boolean[4];
   private boolean enemy;

   public Tank(int x, int y, Direction direction,Image[] image) {
       this(x,y,direction,false,image);
    }

    public Tank(int x, int y, Direction direction,boolean enemy,Image[] image) {
        super(x,y,image);
        this.x = x;
        this.y = y;
        this.direction = direction;
        speed=15;
        this.enemy=enemy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean[] getDirs() {
        return dirs;
    }


   public void move(){
       oldX=x;
       oldY=y;
        switch (direction){
            case UP:
                y-=speed;
                break;
            case DOWN:
                y+=speed;
                break;
            case LEFT:
                x-=speed;
                break;
            case RIGHT:
                x+=speed;
                break;
            case UP_LEFT:
                y-=speed;
                x-=speed;
                break;
            case UP_RIGHT:
                y-=speed;
                x+=speed;
                break;
            case DOWN_LEFT:
                y+=speed;
                x-=speed;
                break;
            case DOWN_RIGHT:
                y+=speed;
                x+=speed;
                break;

        }
      collision();
   }
   public void collision(){
       if(x<0){
           x=0;

       }else if(x>TankGame.getGameClient().getScreenWidth()-width){
           x=TankGame.gameClient.getScreenWidth()-width;
       }
       if(y<0){
           y=0;

       }else if(y>TankGame.gameClient.getScreenHeight()-height){
           y=TankGame.gameClient.getScreenHeight()-height;
       }
       for(Wall wall:TankGame.gameClient.getWalls()){
           if(getRectangle().intersects(wall.getRectangle())){
              // System.out.println("hit");
               x=oldX;
               y=oldY;
               return;
           }
       }
       for(Tank tank:TankGame.getGameClient().getEnemyTanks()){
           if(getRectangle().intersects(tank.getRectangle())){
               x=oldX;
               y=oldY;
               return;
           }
       }
   }
   public void determineDirection(){
        if(dirs[0]&&dirs[2]&&!dirs[1]&&!dirs[3])direction=Direction.UP_LEFT;
        else if(dirs[0]&&dirs[3]&&!dirs[2]&&!dirs[1])direction=Direction.UP_RIGHT;
        else if(dirs[1]&&dirs[2]&&!dirs[0]&&!dirs[3])direction=Direction.DOWN_LEFT;
        else if(dirs[1]&&dirs[3]&&!dirs[0]&&!dirs[2])direction=Direction.DOWN_RIGHT;
        else if(dirs[0]&&!dirs[3]&&!dirs[1]&&!dirs[2])direction=Direction.UP;
        else if(dirs[1]&&!dirs[3]&&!dirs[0]&&!dirs[2])direction=Direction.DOWN;
        else if(dirs[2]&&!dirs[3]&&!dirs[0]&&!dirs[1])direction=Direction.LEFT;
        else if(dirs[3]&&!dirs[1]&&!dirs[0]&&!dirs[2])direction=Direction.RIGHT;


   }
   public void draw(Graphics g){
        if(!isStop()) {
            determineDirection();
            move();
        }
        g.drawImage(image[direction.ordinal()],x,y,null);
   }
   public boolean isStop(){
        for(int i=0;i<dirs.length;i++){
            if(dirs[i]){
                return false;
            }
        }
        return true;
   }
}
