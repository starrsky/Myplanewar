package Flygame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class sky{
	private static BufferedImage bgimg1;
	private static BufferedImage bgimg2;
	private static BufferedImage bgimg3;
	private static BufferedImage bgimg4;
	private static BufferedImage bgimg5;
	private static BufferedImage bgimg6;
	private int speed=0;
	private int height=0;
	private int y1=0;
	private int y2=0;
	static{
		try {
			bgimg1= ImageIO.read(ShootGame.class.getResourceAsStream("/img/±³¾°1.jpg/"));
			bgimg2= ImageIO.read(ShootGame.class.getResourceAsStream("/img/±³¾°1.jpg/"));
			bgimg3= ImageIO.read(ShootGame.class.getResourceAsStream("/img/±³¾°2.jpg/"));
			bgimg4= ImageIO.read(ShootGame.class.getResourceAsStream("/img/±³¾°3.jpg/"));
			bgimg5= ImageIO.read(ShootGame.class.getResourceAsStream("/img/±³¾°4.jpg/"));
			bgimg6= ImageIO.read(ShootGame.class.getResourceAsStream("/img/±³¾°1.jpg/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public sky(){
		height=600;
		speed=2;
		y1=0;
		y2=-height;
	}
	public void paint(Graphics g){
			g.drawImage(bgimg1, 0, y1,400,600, null);
			g.drawImage(bgimg2, 0, y2,400,600, null);
	}
	//Ìì¿ÕÒÆ¶¯
	public void move(int score) {
		int i=score/400;
		if(i%4==1){
			bgimg1=bgimg3;
			bgimg2=bgimg3;
		}
		if(i%4==2){
			bgimg1=bgimg4;
			bgimg2=bgimg4;
		}
		if(i%4==3) {
			bgimg1=bgimg5;
			bgimg2=bgimg5;
		}
		if (i%4==0) {
			bgimg1=bgimg6;
			bgimg2=bgimg6;
		}
		y1+=speed;
		y2+=speed;
		if (y1>=height) {                 //±³¾°ÒÆ¶¯
			y1=-height+2;
		}
		if (y2>=height) {
			y2=-height+2;
		}
	}

}
