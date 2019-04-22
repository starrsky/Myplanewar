package Flygame;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel {
	
	private static final long serialVersionUID = 1L;
	/*
	 * ������
	 * */
	public static void main(String[] args){
	
        JFrame frame = new JFrame("�ɻ���ս");
        ShootGame game = new ShootGame(); 								//������
        frame.add(game); 												//�������ӵ�JFrame��
        frame.setSize(WIDTH, HEIGHT); 									//���ô����С
        frame.setAlwaysOnTop(true); 									//�������������ϲ�
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 			//Ĭ�Ϲرղ���
        frame.setIconImage(hero0);                                      //���ô����ͼ��
        frame.setLocationRelativeTo(null); 								// ���ô����ʼλ��
        frame.setVisible(true); 										//���ɵ��������С
        game.action();													//����ִ��
    }
	
	
    public static final int WIDTH = 400; 								// ����
    public static final int HEIGHT = 600; 								// ����
    
    /*
     * ��Ϸ�ĵ�ǰ״̬: START RUNNING PAUSE GAME_OVER 
     * */
    
    private int state;
    private static final int START = 0;                                 //��ʼ״̬
    private static final int RUNNING = 1;								//����״̬
    private static final int PAUSE = 2;									//��ͣ״̬
    private static final int GAME_OVER = 3;								//��Ϸ����״̬

    
    private int score = 0; 												// �÷�
    private Timer timer; 												// ��ʱ��
    private int intervel = 10; 											// ÿ��ˢ��ҳ���ʱ����(����)
    

    public static BufferedImage background;                             //����ͼƬ
    public static BufferedImage start;									//��ʼ��ťͼƬ
    public static BufferedImage airplane;								//��ͨ�л�ͼƬ1
    public static BufferedImage airplane1;								//��ͨ�л�ͼƬ2
    public static BufferedImage bee;									//�����л�1
    public static BufferedImage bee1;									//�����л�2
    public static BufferedImage bullet0;								//�ӵ�ͼƬ1
    public static BufferedImage bullet1;								//�ӵ�ͼƬ2
    public static BufferedImage hero0;									//Ӣ�ۻ���ʼ״̬ͼƬ	
    public static BufferedImage hero1;									//Ӣ�ۻ�����ͼƬ1
    public static BufferedImage hero2;									//Ӣ�ۻ�����ͼƬ2
    public static BufferedImage pause;									//��ͣ����
    public static BufferedImage gameover;								//��Ϸ����ͼƬ
    public static BufferedImage biaoti;									//�ɻ���ս����ͼƬ
    public static BufferedImage baozha;									//�л���ըͼƬ
    public static BufferedImage gameover1;								//game overͼƬ
    
    
    
    private sky sky=new sky();											//����ͼƬ��
    private Airplane airplane2=new Airplane();							//��ͨ�л�����
    private FlyingObject[] flyings = {}; 								//��������л�����
    private Bullet[] bullets = {}; 										// �ӵ�����
    private Hero hero = new Hero(); 									// Ӣ�ۻ�

    static { 															// ��̬����飬��ʼ��ͼƬ��Դ
        try {
            background = ImageIO.read(ShootGame.class.getResource("/img/����1.jpg"));
            start = ImageIO.read(ShootGame.class.getResource("/img/��ʼ��ť.png"));
            airplane = ImageIO.read(ShootGame.class.getResource("/img/�л�.png"));
            airplane1 = ImageIO.read(ShootGame.class.getResource("/img/�л�3.png"));
            bee = ImageIO.read(ShootGame.class.getResource("/img/�л�1.png"));
            bee1 = ImageIO.read(ShootGame.class.getResource("/img/�л�2.png"));
            bullet0 = ImageIO.read(ShootGame.class.getResource("/img/�ӵ�.png"));
            bullet1 = ImageIO.read(ShootGame.class.getResource("/img/�ӵ�2.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("/img/�ɻ�.png"));
            hero1 = ImageIO.read(ShootGame.class.getResource("/img/�ɻ�1.png"));
            hero2 = ImageIO.read(ShootGame.class.getResource("/img/�ɻ�2.png"));
            biaoti = ImageIO.read(ShootGame.class.getResource("/img/����.png"));
            gameover = ImageIO.read(ShootGame.class.getResource("/img/gameover.jpg"));
            baozha= ImageIO.read(ShootGame.class.getResource("/img/��ը.png"));
          
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** �� */
    @Override
    public void paint(Graphics g) {
        sky.paint(g); 													// ������ͼ
        paintHero(g); 													// ��Ӣ�ۻ�
        paintBullets(g); 												// ���ӵ�
        paintFlyingObjects(g); 											// ��������
        paintScore(g); 													// ������
        paintState(g); 													// ����Ϸ״̬
        if(state==RUNNING){
        	sky.move(score);
        	airplane2.move(0);
        }
    }

    /** ��Ӣ�ۻ� */
    public void paintHero(Graphics g) {
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
    }

    /** ���ӵ� */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),6,15,null);
        }
    }

    /** �������� */
    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            g.drawImage(f.getImage(), f.getX(), f.getY(), null);
        }
    }

    /** ������ */
    public void paintScore(Graphics g) {
        int x = 10; 													//x����
        int y = 25; 													//y����
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20); 			//����
        g.setColor(Color.red);											//�����ֵ���ɫ
        g.setFont(font); 												//��������
        g.drawString("SCORE:" + score, x, y); 							//������
        y += 20; 														//y������20
        g.drawString("LIFE:" + hero.getLife(), x, y); 					// ����
    }

    /** ����Ϸ״̬ */
    public void paintState(Graphics g) {
        switch (state) {
        case START: 													// ����״̬
        	g.drawImage(biaoti, -5, 80, 400,200, null);                 // ������
            g.drawImage(start, 135, 270, null); 						// ����ʼ��ť
            break;
        case PAUSE: 													// ��ͣ״̬
            break;
        case GAME_OVER: 				                                // ��Ϸ��ֹ״̬
            g.drawImage(gameover, 0, 0,null);
            int x = 100; 													
            int y = 250; 													
            Font font = new Font(Font.DIALOG, Font.HANGING_BASELINE, 45); 	
            g.setColor(Color.yellow);									
            g.setFont(font); 												
            g.drawString("SCORE", x, y); 							      
            y += 120; 														
            g.setColor(Color.red);	
            g.drawString(""+score, x, y); 					    
            break;
        }
    }


    /** ����ִ�д��� */
    public void action() {
        															   // �������¼�
        MouseAdapter l = new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) { 						// ����ƶ�
                if (state == RUNNING) { 								// ����״̬���ƶ�Ӣ�ۻ�--�����λ��
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveTo(x, y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { 					// ������
            	/*
                if (state == PAUSE) { 									// ��ͣ״̬������
                    state = RUNNING;
                }
                */
            }

            @Override
            public void mouseExited(MouseEvent e) { 					// ����˳�
               /* if (state != GAME_OVER&&state!=START) { 				// ��Ϸδ��������������Ϊ��ͣ
                    state = PAUSE;
                }
                */
            }

            @Override
            public void mouseClicked(MouseEvent e) { 					// �����
                switch (state) {
                case RUNNING:                                           //����״̬����ͣ
                	state = PAUSE; 
                	break;
                case PAUSE:                                             //��ͣ״̬������
                	state = RUNNING;
                	break;
                case START:
                	if (e.getX()>=140&&e.getY()>=275&&e.getX()<=265&&e.getY()<=300) {
                		 state = RUNNING; 								// ����״̬������
					}
                    break;
                case GAME_OVER: 										// ��Ϸ�����������ֳ�
                	if (e.getX()>=130&&e.getY()>=465&&e.getX()<=255&&e.getY()<=500) {
                		flyings = new FlyingObject[0]; 						// ��շ�����
                        bullets = new Bullet[0]; 							// ����ӵ�
                        hero = new Hero(); 									// ���´���Ӣ�ۻ�
                        score = 0; 											// ��ճɼ�
                        sky.move(score);
                        state = START; 										// ״̬����Ϊ����
					}
                    break;
                }
            }
        };
        this.addMouseListener(l); 										// �������������
        this.addMouseMotionListener(l); 								// ������껬������

        timer = new Timer(); 											// �����̿���
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) { 								// ����״̬
                    enterAction(); 										// �������볡
                    stepAction(); 										// ��һ��
                    shootAction();										// Ӣ�ۻ����
                    OfBoundsAction1();                                  // �ɻ���ըЧ�����
                    bangAction(); 										// �ӵ��������
                    outOfBoundsAction(); 								// ɾ��Խ������Ｐ�ӵ�
                    checkGameOverAction(); 								// �����Ϸ����
                }
                repaint(); 												// �ػ棬����paint()����
            }

        }, intervel, intervel);
    }

    int flyEnteredIndex = 0; 											// �������볡����

    /** �������볡 */
    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) { 								// 400��������һ��������--10*40
            FlyingObject obj = nextOne(); 								// �������һ��������
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }
    }

    /** ��һ�� */
    public void stepAction() {
        for (int i = 0; i < flyings.length; i++) { 						// ��������һ��
            FlyingObject f = flyings[i];
            f.step();
        }

        for (int i = 0; i < bullets.length; i++) { 						// �ӵ���һ��
            Bullet b = bullets[i];
            b.step();
        }
        hero.step(); 													// Ӣ�ۻ���һ��
    }

    /** ��������һ�� */
    public void flyingStepAction() {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            f.step();
        }
    }

    int shootIndex = 0; 												// �������

    /** ��� */
    public void shootAction() {
        shootIndex++;
        if (shootIndex % 35 == 0) { 									// 300���뷢һ��
            Bullet[] bs = hero.shoot(); 								// Ӣ�۴���ӵ�
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // ����
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length,bs.length); // ׷������
        }
    }
    
    public void OfBoundsAction1() {
        shootIndex++;
        if (shootIndex % 30==0 ) {
        	OfBoundsAction();
		}
    }

    /** �ӵ����������ײ��� */
    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) { 						// ���������ӵ�
        	int bu=0;
            Bullet b = bullets[i];
            bang(b); 													// �ӵ��ͷ�����֮�����ײ���
            if (bu==1) {
            	bullets[i] = null;
			}
        }
    }

    /** ɾ��Խ������Ｐ�ӵ� */
    public void outOfBoundsAction() {
        int index = 0; 													// ����
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // ���ŵķ�����
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (!f.outOfBounds()) {
                flyingLives[index++] = f; 								// ��Խ�������
            }
        }
        flyings = Arrays.copyOf(flyingLives, index); 					// ����Խ��ķ����ﶼ����
        
        index = 0; 														// ��������Ϊ0
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outOfBounds()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index); 					// ����Խ����ӵ�����
    }
    /** ɾ������ɱ������ */
    public void OfBoundsAction() {
        int index = 0; 													
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; 
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (f.diji==0) {
                flyingLives[index++] = f; 								
            }
        }
        flyings = Arrays.copyOf(flyingLives, index); 				
        
    }

    /** �����Ϸ���� */
    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAME_OVER; 											// �ı�״̬
        }
    }

    /** �����Ϸ�Ƿ���� */
    public boolean isGameOver() {
        for (int i = 0; i < flyings.length; i++) {
            int index = -1;
            FlyingObject obj = flyings[i];
            if (hero.hit(obj)) { 										// ���Ӣ�ۻ���������Ƿ���ײ
                hero.subtractLife(); 									// ����
                hero.setDoubleFire(0); 									// ˫���������
                hero.image=ShootGame.hero1;
                index = i; 												// ��¼���ϵķ���������
            }
            if (index != -1) {
                FlyingObject t = flyings[index];
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = t; 						// ���ϵ������һ�������ｻ��

                flyings = Arrays.copyOf(flyings, flyings.length - 1); 	// ɾ�����ϵķ�����
            }
        }
        
        return hero.getLife() <= 0;
    }

    /** �ӵ��ͷ�����֮�����ײ��� */
    public void bang(Bullet bullet) {
        int index = -1; // ���еķ���������
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject obj = flyings[i];
            if (obj.shootBy(bullet)){ 									// �ж��Ƿ����
                index = i; 												// ��¼�����еķ����������
                flyings[i].diji=1;
                break;
            }
        }
 
        if (index != -1){ 												// �л��еķ�����
        	FlyingObject one = flyings[index]; 							// ��¼�����еķ�����
        	FlyingObject temp = flyings[index]; 						// �����еķ����������һ�������ｻ��
        	flyings[index] = flyings[flyings.length - 1];
        	flyings[flyings.length - 1] = temp;
        	flyings[flyings.length - 1].diji=1;
        	

        	// ���one������(���˼ӷ֣�������ȡ)
        	if (one instanceof Enemy) { 								// ������ͣ��ǵ��ˣ���ӷ�
        		Enemy e = (Enemy) one; 									// ǿ������ת��
        		score += e.getScore(); 									// �ӷ�
        	} 
        	else if (one instanceof Award) { 							// ��Ϊ���������ý���
        		Award a = (Award) one;
        		int type = a.getType(); 								// ��ȡ��������
        		switch (type) {
        		case Award.DOUBLE_FIRE:
        			hero.addDoubleFire(); 								// ����˫������
        			break;
        		case Award.LIFE:
        			hero.addLife(); 									// ���ü���
        			break;
        		}
        	}
        }
    }
    /*
        ������ɷ�����
    @return���������
    */
    public static FlyingObject nextOne(){
        Random random = new Random();
        int type = random.nextInt(20); // [0,20)
        if (type == 0){
            return new Bee();
        } 
        else{
            return new Airplane();
        }
    }
    
}