package Flygame;

public class Bullet extends FlyingObject {
    private int speed = 3;  //移动的速度
    /*初始化数据 */
    public Bullet(int x,int y){
        this.x = x;
        this.y = y;
        image = ShootGame.bullet0;
    }

    /*移动 */
    @Override
    public void step(){   
        y-=speed;
    }

    /*越界处理 */
    public boolean outOfBounds() {
        return y<-height;
    }
   
}

