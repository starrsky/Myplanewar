package Flygame;

import java.util.Random;

/**
 * 敌飞机: 是飞行物，也是敌人
 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3 ;  //移动步骤
    
    /** 初始化数据 */
    public Airplane(){
    	this.move(0);
        width = image.getWidth();
        height = image.getHeight();
        y = -height;          
        Random rand = new Random();
        x = rand.nextInt(ShootGame.WIDTH - width);
    }
    
    /** 获取分数 */
    @Override
    public int getScore() {  
        return 10;
    }

    /** //越界处理 */
    @Override
    public  boolean outOfBounds() {   
        return y>ShootGame.HEIGHT;
    }

    /** 移动 */
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
