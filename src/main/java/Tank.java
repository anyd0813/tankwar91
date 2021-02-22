import javax.swing.*;
import java.awt.*;

public class Tank {
   private int x;
   private int y;
   private Direction direction;
   private int speed;
   private boolean[] dirs=new boolean[4];

    public Tank(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        speed=15;
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

    public Image getImage(){
        if(direction==Direction.UP)
            return new ImageIcon("assets/images/itankU.png").getImage();
       if(direction==Direction.DOWN)
           return new ImageIcon("assets/images/itankD.png").getImage();
       if(direction==Direction.LEFT)
           return new ImageIcon("assets/images/itankL.png").getImage();
       if(direction==Direction.RIGHT)
           return new ImageIcon("assets/images/itankR.png").getImage();
        if(direction==Direction.UP_RIGHT)
            return new ImageIcon("assets/images/itankRU.png").getImage();
        if(direction==Direction.UP_LEFT)
            return new ImageIcon("assets/images/itankLU.png").getImage();
        if(direction==Direction.DOWN_RIGHT)
            return new ImageIcon("assets/images/itankRD.png").getImage();
        if(direction==Direction.DOWN_LEFT)
            return new ImageIcon("assets/images/itankLD.png").getImage();

       return null;

   }
   public void move(){
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
        g.drawImage(getImage(),x,y,null);
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
