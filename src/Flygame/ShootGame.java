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
	 * 主函数
	 * */
	public static void main(String[] args){
	
        JFrame frame = new JFrame("飞机大战");
        ShootGame game = new ShootGame(); 								//面板对象
        frame.add(game); 												//将面板添加到JFrame中
        frame.setSize(WIDTH, HEIGHT); 									//设置窗体大小
        frame.setAlwaysOnTop(true); 									//设置其总在最上层
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 			//默认关闭操作
        frame.setIconImage(hero0);                                      //设置窗体的图标
        frame.setLocationRelativeTo(null); 								// 设置窗体初始位置
        frame.setVisible(true); 										//不可调整窗体大小
        game.action();													//启动执行
    }
	
	
    public static final int WIDTH = 400; 								// 面板宽
    public static final int HEIGHT = 600; 								// 面板高
    
    /*
     * 游戏的当前状态: START RUNNING PAUSE GAME_OVER 
     * */
    
    private int state;
    private static final int START = 0;                                 //开始状态
    private static final int RUNNING = 1;								//运行状态
    private static final int PAUSE = 2;									//暂停状态
    private static final int GAME_OVER = 3;								//游戏结束状态

    
    private int score = 0; 												// 得分
    private Timer timer; 												// 定时器
    private int intervel = 10; 											// 每次刷新页面的时间间隔(毫秒)
    

    public static BufferedImage background;                             //背景图片
    public static BufferedImage start;									//开始按钮图片
    public static BufferedImage airplane;								//普通敌机图片1
    public static BufferedImage airplane1;								//普通敌机图片2
    public static BufferedImage bee;									//奖励敌机1
    public static BufferedImage bee1;									//奖励敌机2
    public static BufferedImage bullet0;								//子弹图片1
    public static BufferedImage bullet1;								//子弹图片2
    public static BufferedImage hero0;									//英雄机初始状态图片	
    public static BufferedImage hero1;									//英雄机升级图片1
    public static BufferedImage hero2;									//英雄机升级图片2
    public static BufferedImage pause;									//暂停背景
    public static BufferedImage gameover;								//游戏结束图片
    public static BufferedImage biaoti;									//飞机大战标题图片
    public static BufferedImage baozha;									//敌机爆炸图片
    public static BufferedImage gameover1;								//game over图片
    
    
    
    private sky sky=new sky();											//背景图片类
    private Airplane airplane2=new Airplane();							//普通敌机数组
    private FlyingObject[] flyings = {}; 								//所有特殊敌机数组
    private Bullet[] bullets = {}; 										// 子弹数组
    private Hero hero = new Hero(); 									// 英雄机

    static { 															// 静态代码块，初始化图片资源
        try {
            background = ImageIO.read(ShootGame.class.getResource("/img/背景1.jpg"));
            start = ImageIO.read(ShootGame.class.getResource("/img/开始按钮.png"));
            airplane = ImageIO.read(ShootGame.class.getResource("/img/敌机.png"));
            airplane1 = ImageIO.read(ShootGame.class.getResource("/img/敌机3.png"));
            bee = ImageIO.read(ShootGame.class.getResource("/img/敌机1.png"));
            bee1 = ImageIO.read(ShootGame.class.getResource("/img/敌机2.png"));
            bullet0 = ImageIO.read(ShootGame.class.getResource("/img/子弹.png"));
            bullet1 = ImageIO.read(ShootGame.class.getResource("/img/子弹2.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("/img/飞机.png"));
            hero1 = ImageIO.read(ShootGame.class.getResource("/img/飞机1.png"));
            hero2 = ImageIO.read(ShootGame.class.getResource("/img/飞机2.png"));
            biaoti = ImageIO.read(ShootGame.class.getResource("/img/标题.png"));
            gameover = ImageIO.read(ShootGame.class.getResource("/img/gameover.jpg"));
            baozha= ImageIO.read(ShootGame.class.getResource("/img/爆炸.png"));
          
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** 画 */
    @Override
    public void paint(Graphics g) {
        sky.paint(g); 													// 画背景图
        paintHero(g); 													// 画英雄机
        paintBullets(g); 												// 画子弹
        paintFlyingObjects(g); 											// 画飞行物
        paintScore(g); 													// 画分数
        paintState(g); 													// 画游戏状态
        if(state==RUNNING){
        	sky.move(score);
        	airplane2.move(0);
        }
    }

    /** 画英雄机 */
    public void paintHero(Graphics g) {
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
    }

    /** 画子弹 */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),6,15,null);
        }
    }

    /** 画飞行物 */
    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            g.drawImage(f.getImage(), f.getX(), f.getY(), null);
        }
    }

    /** 画分数 */
    public void paintScore(Graphics g) {
        int x = 10; 													//x坐标
        int y = 25; 													//y坐标
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20); 			//字体
        g.setColor(Color.red);											//设置字的颜色
        g.setFont(font); 												//设置字体
        g.drawString("SCORE:" + score, x, y); 							//画分数
        y += 20; 														//y坐标增20
        g.drawString("LIFE:" + hero.getLife(), x, y); 					// 画命
    }

    /** 画游戏状态 */
    public void paintState(Graphics g) {
        switch (state) {
        case START: 													// 启动状态
        	g.drawImage(biaoti, -5, 80, 400,200, null);                 // 画标题
            g.drawImage(start, 135, 270, null); 						// 画开始按钮
            break;
        case PAUSE: 													// 暂停状态
            break;
        case GAME_OVER: 				                                // 游戏终止状态
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


    /** 启动执行代码 */
    public void action() {
        															   // 鼠标监听事件
        MouseAdapter l = new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) { 						// 鼠标移动
                if (state == RUNNING) { 								// 运行状态下移动英雄机--随鼠标位置
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveTo(x, y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { 					// 鼠标进入
            	/*
                if (state == PAUSE) { 									// 暂停状态下运行
                    state = RUNNING;
                }
                */
            }

            @Override
            public void mouseExited(MouseEvent e) { 					// 鼠标退出
               /* if (state != GAME_OVER&&state!=START) { 				// 游戏未结束，则设置其为暂停
                    state = PAUSE;
                }
                */
            }

            @Override
            public void mouseClicked(MouseEvent e) { 					// 鼠标点击
                switch (state) {
                case RUNNING:                                           //运行状态变暂停
                	state = PAUSE; 
                	break;
                case PAUSE:                                             //暂停状态变运行
                	state = RUNNING;
                	break;
                case START:
                	if (e.getX()>=140&&e.getY()>=275&&e.getX()<=265&&e.getY()<=300) {
                		 state = RUNNING; 								// 启动状态下运行
					}
                    break;
                case GAME_OVER: 										// 游戏结束，清理现场
                	if (e.getX()>=130&&e.getY()>=465&&e.getX()<=255&&e.getY()<=500) {
                		flyings = new FlyingObject[0]; 						// 清空飞行物
                        bullets = new Bullet[0]; 							// 清空子弹
                        hero = new Hero(); 									// 重新创建英雄机
                        score = 0; 											// 清空成绩
                        sky.move(score);
                        state = START; 										// 状态设置为启动
					}
                    break;
                }
            }
        };
        this.addMouseListener(l); 										// 处理鼠标点击操作
        this.addMouseMotionListener(l); 								// 处理鼠标滑动操作

        timer = new Timer(); 											// 主流程控制
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) { 								// 运行状态
                    enterAction(); 										// 飞行物入场
                    stepAction(); 										// 走一步
                    shootAction();										// 英雄机射击
                    OfBoundsAction1();                                  // 飞机爆炸效果添加
                    bangAction(); 										// 子弹打飞行物
                    outOfBoundsAction(); 								// 删除越界飞行物及子弹
                    checkGameOverAction(); 								// 检查游戏结束
                }
                repaint(); 												// 重绘，调用paint()方法
            }

        }, intervel, intervel);
    }

    int flyEnteredIndex = 0; 											// 飞行物入场计数

    /** 飞行物入场 */
    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) { 								// 400毫秒生成一个飞行物--10*40
            FlyingObject obj = nextOne(); 								// 随机生成一个飞行物
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }
    }

    /** 走一步 */
    public void stepAction() {
        for (int i = 0; i < flyings.length; i++) { 						// 飞行物走一步
            FlyingObject f = flyings[i];
            f.step();
        }

        for (int i = 0; i < bullets.length; i++) { 						// 子弹走一步
            Bullet b = bullets[i];
            b.step();
        }
        hero.step(); 													// 英雄机走一步
    }

    /** 飞行物走一步 */
    public void flyingStepAction() {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            f.step();
        }
    }

    int shootIndex = 0; 												// 射击计数

    /** 射击 */
    public void shootAction() {
        shootIndex++;
        if (shootIndex % 35 == 0) { 									// 300毫秒发一颗
            Bullet[] bs = hero.shoot(); 								// 英雄打出子弹
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length,bs.length); // 追加数组
        }
    }
    
    public void OfBoundsAction1() {
        shootIndex++;
        if (shootIndex % 30==0 ) {
        	OfBoundsAction();
		}
    }

    /** 子弹与飞行物碰撞检测 */
    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) { 						// 遍历所有子弹
        	int bu=0;
            Bullet b = bullets[i];
            bang(b); 													// 子弹和飞行物之间的碰撞检查
            if (bu==1) {
            	bullets[i] = null;
			}
        }
    }

    /** 删除越界飞行物及子弹 */
    public void outOfBoundsAction() {
        int index = 0; 													// 索引
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 活着的飞行物
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (!f.outOfBounds()) {
                flyingLives[index++] = f; 								// 不越界的留着
            }
        }
        flyings = Arrays.copyOf(flyingLives, index); 					// 将不越界的飞行物都留着
        
        index = 0; 														// 索引重置为0
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outOfBounds()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index); 					// 将不越界的子弹留着
    }
    /** 删除被击杀飞行物 */
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

    /** 检查游戏结束 */
    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAME_OVER; 											// 改变状态
        }
    }

    /** 检查游戏是否结束 */
    public boolean isGameOver() {
        for (int i = 0; i < flyings.length; i++) {
            int index = -1;
            FlyingObject obj = flyings[i];
            if (hero.hit(obj)) { 										// 检查英雄机与飞行物是否碰撞
                hero.subtractLife(); 									// 减命
                hero.setDoubleFire(0); 									// 双倍火力解除
                hero.image=ShootGame.hero1;
                index = i; 												// 记录碰上的飞行物索引
            }
            if (index != -1) {
                FlyingObject t = flyings[index];
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = t; 						// 碰上的与最后一个飞行物交换

                flyings = Arrays.copyOf(flyings, flyings.length - 1); 	// 删除碰上的飞行物
            }
        }
        
        return hero.getLife() <= 0;
    }

    /** 子弹和飞行物之间的碰撞检查 */
    public void bang(Bullet bullet) {
        int index = -1; // 击中的飞行物索引
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject obj = flyings[i];
            if (obj.shootBy(bullet)){ 									// 判断是否击中
                index = i; 												// 记录被击中的飞行物的索引
                flyings[i].diji=1;
                break;
            }
        }
 
        if (index != -1){ 												// 有击中的飞行物
        	FlyingObject one = flyings[index]; 							// 记录被击中的飞行物
        	FlyingObject temp = flyings[index]; 						// 被击中的飞行物与最后一个飞行物交换
        	flyings[index] = flyings[flyings.length - 1];
        	flyings[flyings.length - 1] = temp;
        	flyings[flyings.length - 1].diji=1;
        	

        	// 检查one的类型(敌人加分，奖励获取)
        	if (one instanceof Enemy) { 								// 检查类型，是敌人，则加分
        		Enemy e = (Enemy) one; 									// 强制类型转换
        		score += e.getScore(); 									// 加分
        	} 
        	else if (one instanceof Award) { 							// 若为奖励，设置奖励
        		Award a = (Award) one;
        		int type = a.getType(); 								// 获取奖励类型
        		switch (type) {
        		case Award.DOUBLE_FIRE:
        			hero.addDoubleFire(); 								// 设置双倍火力
        			break;
        		case Award.LIFE:
        			hero.addLife(); 									// 设置加命
        			break;
        		}
        	}
        }
    }
    /*
        随机生成飞行物
    @return飞行物对象
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