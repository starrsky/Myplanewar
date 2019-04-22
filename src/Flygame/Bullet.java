package Flygame;

public class Bullet extends FlyingObject {
    private int speed = 3;  //�ƶ����ٶ�
    /*��ʼ������ */
    public Bullet(int x,int y){
        this.x = x;
        this.y = y;
        image = ShootGame.bullet0;
    }

    /*�ƶ� */
    @Override
    public void step(){   
        y-=speed;
    }

    /*Խ�紦�� */
    public boolean outOfBounds() {
        return y<-height;
    }
   
}

