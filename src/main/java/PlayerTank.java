import Object.Direction;
import java.awt.*;
import java.util.Random;

public class PlayerTank extends Tank implements SuperFire{
    private final int MAX_HP=3;
    public PlayerTank(int x, int y, Direction direction, Image[] image) {
        super(x, y, direction, image);
        hp=3;
    }

    public void superFire() {

        for(Direction direction: Direction.values()){
            Bullet bullet=new Bullet(x+(width-GameClient.bullectImage[0].getWidth(null))/2,
                    y+(height-GameClient.bullectImage[0].getHeight(null))/2, direction,enemy,
                    GameClient.bullectImage);
           // TankGame.getGameClient().addGameObject(bullet);
            String audioFile=new Random().nextBoolean() ?"supershoot.aiff" : "supershoot.wav";
            Tools.playAudio(audioFile);
        }
    }
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.white);
        g.drawRect(x,y-12,width,10);

        g.setColor(Color.red);
        g.fillRect(x+1,y-11,(width/MAX_HP)*hp+1,9);
    }
}
