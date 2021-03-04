import Object.Direction;
import Object.GameObject;

import java.awt.*;

public class Tank extends GameObject {
    protected Direction direction;
   protected int speed;
   protected boolean[] dirs=new boolean[4];
   protected boolean enemy;
   protected int hp;


   public Tank(int x, int y, Direction direction, Image[] image) {
       this(x,y,direction,false,image);
    }

    public Tank(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x,y,image);
        this.x = x;
        this.y = y;
        this.direction = direction;
        speed=15;
        hp=1;
        this.enemy=enemy;
    }
    public void getHurt(int hp){
        this.hp-=hp;
        if(this.hp<=0){
            alive=false;
        }

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

    public boolean isEnemy() {
        return enemy;
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
    public boolean collisionBound() {
        boolean isCollision = false;
        if (x < 0) {
            x = 0;
            isCollision = true;
        } else if (x > TankGame.getGameClient().getScreenWidth() - width) {
            x = TankGame.getGameClient().getScreenWidth() - width;
            isCollision = true;
        }

        if (y < 0) {
            y = 0;
            isCollision = true;
        } else if (y > TankGame.getGameClient().getScreenHeight() - height) {
            y = TankGame.getGameClient().getScreenHeight() - height;
            isCollision = true;
        }

        return isCollision;
    }
    public boolean collisionObject() {
        for (GameObject object : TankGame.getGameClient().getObjects()) {
            if (object != this && !(object instanceof Bullet) && getRectangle().intersects(object.getRectangle())) {
                //System.out.println("hit");
                return true;
            }
        }
        return false;
    }
    public boolean collision() {
        boolean isCollision = collisionBound();
        if (!isCollision) {
            isCollision = collisionObject();
        }

        if (isCollision) {
            x = oldX;
            y = oldY;
        }

        return isCollision;
    }

   public void fire(){
       TankGame.gameClient.addGameObject(
               new Bullet(x+width/2-GameClient.bullectImage[0].getWidth(null)/2,
               y+height/2-GameClient.bullectImage[0].getHeight(null)/2,direction,enemy,GameClient.bullectImage));
                       if (!enemy){
                           Tools.playAudio("shoot.wav");
                       }

   }

   public void determineDirection(){
        if(dirs[0]&&dirs[2]&&!dirs[1]&&!dirs[3])direction= Direction.UP_LEFT;
        else if(dirs[0]&&dirs[3]&&!dirs[2]&&!dirs[1])direction= Direction.UP_RIGHT;
        else if(dirs[1]&&dirs[2]&&!dirs[0]&&!dirs[3])direction= Direction.DOWN_LEFT;
        else if(dirs[1]&&dirs[3]&&!dirs[0]&&!dirs[2])direction= Direction.DOWN_RIGHT;
        else if(dirs[0]&&!dirs[3]&&!dirs[1]&&!dirs[2])direction= Direction.UP;
        else if(dirs[1]&&!dirs[3]&&!dirs[0]&&!dirs[2])direction= Direction.DOWN;
        else if(dirs[2]&&!dirs[3]&&!dirs[0]&&!dirs[1])direction= Direction.LEFT;
        else if(dirs[3]&&!dirs[1]&&!dirs[0]&&!dirs[2])direction= Direction.RIGHT;


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
