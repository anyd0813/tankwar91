import java.awt.*;
import Object.Direction;
import Object.GameObject;

public class Bullet extends Tank {
    public Bullet(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, direction,enemy, image);
        speed=30;
    }
    public void draw(Graphics g){
        if(!alive){
            return;
        }
        move();
        g.drawImage(image[direction.ordinal()],x,y,null);

    }
    public boolean collisionObject() {

      for(GameObject object:TankGame.getGameClient().getObjects()){

          if (object == this || object instanceof Bullet || object instanceof Explosion) {
              continue;
          }
          //是否是本身的子彈
          if (object instanceof Tank && isEnemy() == ((Tank) object).isEnemy()) {
              continue;
          }

          if(object!=this&&getRectangle().intersects(object.getRectangle())){
              alive=false;
              //敵方坦克消失
              if (object instanceof Tank) {
                  Tank tank = (Tank) object;
                  tank.getHurt(1);
                  if (!tank.isAlive() && tank.isEnemy()) {
                      TankGame.gameClient.addScore(100);
                  }


              }
              TankGame.gameClient.addGameObject(new Explosion(x+(GameClient.explosionImage[0].getHeight(null)-width)/2,
                      y+(GameClient.explosionImage[0].getHeight(null)-height)/2,GameClient.explosionImage));
              return true;
          }
      }
      return false;
    }

    @Override
    public boolean collision() {

        boolean isCollision = collisionBound();

        if (!isCollision) {
            isCollision = collisionObject();
        }

        if (isCollision) {
            alive = false;
        }

        return isCollision;
    }
}
