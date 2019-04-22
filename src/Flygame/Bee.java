package Flygame;
import java.util.Random;


public class Bee extends FlyingObject implements Award{
    private int xSpeed = 4;   //x�����ƶ��ٶ�
    private int ySpeed = 5;   //y�����ƶ��ٶ�
    private int awardType;    //��������
    
    /*��ʼ������ */
    public Bee(){
    	Random rand = new Random();
    	awardType = rand.nextInt(2);   //��ʼ��ʱ������
    	if (awardType==1) {
    		this.image = ShootGame.bee;
		}
    	else {
    		this.image = ShootGame.bee1;
		}
        width = image.getWidth();
        height = image.getHeight();
        y = -height;
        x = rand.nextInt(ShootGame.WIDTH - width);
    }
    
    /*��ý������� */
    public int getType(){
        return awardType;
    }

    /*Խ�紦�� */
    @Override
    public boolean outOfBounds() {
        return y>ShootGame.HEIGHT;
    }

    /*�ƶ�����б�ŷ� */
    @Override
    public void step() {      
        x += xSpeed;
        y += ySpeed;
        if(x > ShootGame.WIDTH-width){  
            xSpeed = -1;
        }
        if(x < 0){
            xSpeed = 1;
        }
    }
}
