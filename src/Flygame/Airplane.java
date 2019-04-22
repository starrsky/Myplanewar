package Flygame;

import java.util.Random;

/**
 * �зɻ�: �Ƿ����Ҳ�ǵ���
 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3 ;  //�ƶ�����
    
    /** ��ʼ������ */
    public Airplane(){
    	this.move(0);
        width = image.getWidth();
        height = image.getHeight();
        y = -height;          
        Random rand = new Random();
        x = rand.nextInt(ShootGame.WIDTH - width);
    }
    
    /** ��ȡ���� */
    @Override
    public int getScore() {  
        return 10;
    }

    /** //Խ�紦�� */
    @Override
    public  boolean outOfBounds() {   
        return y>ShootGame.HEIGHT;
    }

    /** �ƶ� */
    @Override
    public void step() {   
        y += speed;
    }
    public void move(int diji1) {
    	Random iRandom=new Random();
    	int i= iRandom.nextInt(2);
    	if(i==1&&diji1==0){
    		this.image = ShootGame.airplane;
    	}
        else if (diji1==0){
        	this.image = ShootGame.airplane1;
		}
    	else if (diji1==1){
			this.image =ShootGame.baozha;
		}
	}
}
