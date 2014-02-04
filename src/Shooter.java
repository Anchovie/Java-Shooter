import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;


public class Shooter extends JFrame implements KeyListener {
    private final int APPLET_WIDTH=800;
    private final int APPLET_HEIGHT=500;
    
    Player player1;
    Player player2;
    
    CopyOnWriteArrayList<Bullet> bullets;
    CopyOnWriteArrayList<Bullet> player1Bullets;
    CopyOnWriteArrayList<Bullet> player2Bullets;
    
    CopyOnWriteArrayList<Star> stars;
    
    String player1History;
    String player2History;
    
    boolean inGame, titleMenu,optionsMenu, paused;
    boolean enableStars;
    
    int titleChoice;
    
    Image image;
    Graphics graphics;
    
    Image img1;
    Image weaponChoice;
    Image title;

    public Shooter(boolean enableStars){
        this.enableStars=enableStars;
        System.out.println("in Shooter() enableStars="+this.enableStars);
        System.out.println("Starting initing the player1");
        this.player1=new Player(10,this.APPLET_HEIGHT/2-30,60,50,true,"ship2",1);
        System.out.println("INITIALIZING OF PLAYER 1 DONE. Starting initing the player1");
        this.player2=new Player(this.APPLET_WIDTH-10-60,this.APPLET_HEIGHT/2-30,60,50,false,"ship3",2);
        System.out.println("INITIALIZING OF PLAYER 2 DONE. Starting initing shields");
        this.player1.setEnemy(this.player2);
        this.player2.setEnemy(this.player1);
        
        this.player1.initShield();
        this.player2.initShield();
        
        this.bullets = new CopyOnWriteArrayList<Bullet>();
        this.player1Bullets = new CopyOnWriteArrayList<Bullet>();
        this.player2Bullets = new CopyOnWriteArrayList<Bullet>();
        
        this.stars=new CopyOnWriteArrayList<Star>();
        
        this.weaponChoice=this.player1.getImage("weapons");
        this.title=this.player1.getImage("title");
        
        addKeyListener(this);
        this.setTitle("Space Shooter!");
        this.setResizable(false);
        
        this.setSize(this.APPLET_WIDTH,this.APPLET_HEIGHT);
        this.setBackground(Color.BLACK);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
//        this.inGame=true;
        this.titleMenu=true;
        this.titleChoice=0;
        this.setVisible(true);
    }
    
    //////////////////////////      DRAWING     ////////////////////////////////////
    
    
    public void paint(Graphics g){
        this.image = createImage(this.getWidth(),this.getHeight());
        this.graphics=this.image.getGraphics();
        
        
        
        paintScreen(this.graphics);
        paintComponents(this.graphics);
        
        
        //if (this.graphics==null)
            //System.out.println("GRAPHICS IS NULLLLL");
        //if (this.player1==null)
           // System.out.println("player1 IS NULLLLL");
        
        
//        if (this.player1!=null)this.player1.draw(this.graphics);
//        if (this.player1!=null)this.player2.draw(this.graphics);
        
//        g.setColor(Color.WHITE);
//        g.drawString("JAPEEE",100,100);
//        g.drawImage(this.img1, 200, 200, null);
        g.clearRect(0,0,this.APPLET_WIDTH, this.APPLET_HEIGHT);
        g.drawImage(this.image, 0, 0, null);
        repaint();
    }
    public void update(Graphics g){
        paint(g);
    }
    
    public void paintComponents(Graphics g){
        if (this.inGame){
//            wait(1);
            if (this.enableStars){
                for (Star star : this.stars){
                    star.draw(g);
                }
            }
            this.player1.draw(g);
            this.player2.draw(g);
            for (Bullet bullet : this.player1Bullets){
                    bullet.draw(g);
                    if (bullet.move() || bullet.getX()>799){
                        this.player1Bullets.remove(bullet);
                    }
            }
            for (Bullet bullet : this.player2Bullets){
                    bullet.draw(g);
                    if (bullet.move() || bullet.getX()<0){
                        this.player2Bullets.remove(bullet);
                    }
            }
            this.player1.move();
            this.player2.move();
            if (this.enableStars)
                manageBackground();
        }
    }
    
    public void paintScreen(Graphics g){
        if (this.inGame){
            g.setColor(Color.GREEN);
    //        if (this.player1!=null)
            for (int i=0; i<this.player1.getHistory().length;i++){
                g.drawString(this.player1.getHistory()[i], 8, 40+(i*12));
            }
            for (int i=0; i<this.player2.getHistory().length;i++){
                g.drawString(this.player2.getHistory()[i], 792 - getFontMetrics(getFont()).stringWidth(this.player2.getHistory()[i]), 40+(i*12));
            }
            g.setColor(Color.green.darker());
            g.drawRect(5,27,290,65);
            g.drawRect(505,27,290,65);
            
            drawWeaponPalette(g);
            drawStatusBars(g);
            
        }
        if (this.titleMenu){
            g.drawImage(this.title, 0, 0, null);
            g.setColor(Color.yellow);
            g.drawRect(325, 296+this.titleChoice*54, 257, 48);    //350
            g.setColor(Color.yellow.darker());
            g.drawRect(324, 295+this.titleChoice*54, 259, 50);
        }
        
    }
    public void drawWeaponPalette(Graphics g){
        g.drawImage(this.weaponChoice, 20, 435, this);
        g.setColor(Color.YELLOW);
        if (this.player1.getCurrentWeapon().equals("Normal")){
            g.drawRect(35, 449, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(34,448,17,17);
        }
        if (this.player1.getCurrentWeapon().equals("Mega Shell")){
            g.drawRect(35, 464, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(34,463,17,17);
        }
        if (this.player1.getCurrentWeapon().equals("Laser")){
            g.drawRect(35, 434, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(34,433,17,17);
        }
        if (this.player1.getCurrentWeapon().equals("Corona Blast")){
            g.drawRect(20, 449, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(19,448,17,17);
        }
        if (this.player1.getCurrentWeapon().equals("Ion Charge")){
            g.drawRect(50, 449, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(49,448,17,17);
        }
        g.drawImage(this.weaponChoice, 735, 435, this);
        g.setColor(Color.YELLOW);
        if (this.player2.getCurrentWeapon().equals("Normal")){
            g.drawRect(750, 449, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(749,448,17,17);
        }
        if (this.player2.getCurrentWeapon().equals("Mega Shell")){
            g.drawRect(750, 464, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(749,463,17,17);
        }
        if (this.player2.getCurrentWeapon().equals("Laser")){
            g.drawRect(750, 434, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(749,433,17,17);
        }
        if (this.player2.getCurrentWeapon().equals("Corona Blast")){
            g.drawRect(735, 449, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(734,448,17,17);
        }
        if (this.player2.getCurrentWeapon().equals("Ion Charge")){
            g.drawRect(765, 449, 15, 15);
            g.setColor(Color.YELLOW.brighter());
            g.drawRect(764,448,17,17);
        }
    }
    public void drawStatusBars(Graphics g){
        g.setColor(Color.GREEN.brighter());
        g.drawRect(10, 100, 101, 12);
        g.drawRect(690, 100, 101, 12);
        g.setColor(Color.RED.darker().darker());
        g.fillRect(11, 101, 100, 11);
        g.fillRect(691, 101, 100, 11);
        g.setColor(Color.green.darker());
        g.fillRect(11, 101,(int)this.player1.getHp(), 11);
        g.fillRect(691+(int)this.player2.getHpMax()-(int)this.player2.getHp(), 101, (int) this.player2.getHp(), 11);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString((int)this.player1.getHp())+"/"+Integer.toString((int)this.player1.getHpMax()), 40, 111);
        g.drawString(Integer.toString((int)this.player2.getHp())+"/"+Integer.toString((int)this.player2.getHpMax()), 720, 111);
        
        g.setColor(Color.BLUE);
        g.drawRect(10, 115, 101, 12);
        g.drawRect(690, 115, 101, 12);
        g.setColor(Color.BLUE.darker().darker().darker());
        g.fillRect(11, 116, 100, 11);
        g.fillRect(691, 116, 100, 11);
        g.setColor(Color.CYAN.darker());
        g.fillRect(11, 116, (int) this.player1.getEnergy(), 11);
        g.fillRect(691+(int)this.player2.getEnergyMax()-(int)this.player2.getEnergy(), 116, (int)this.player2.getEnergy(), 11);
        g.setColor(Color.white);
        g.drawString(Integer.toString((int)this.player1.getEnergy())+"/"+Integer.toString((int)this.player1.getEnergyMax()), 40, 126);
        g.drawString(Integer.toString((int)this.player2.getEnergy())+"/"+Integer.toString((int)this.player2.getEnergyMax()), 720, 126);
        
    }
    
    
    
    
    
    
    ////////////////////////        KEYEVENTS       ///////////////////////////////
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_A){
            if (this.player1.isChangingWeapons()){
                this.player1.changeWeapon(2);
                System.out.println("current Weapon: "+this.player1.getCurrentWeapon()+" ID: "+this.player1.getCurrentWeaponId());
                this.player1.changingWeapons(false);
            } else{
//                this.player1.setMoveFlag(true);
            }
        }else if (e.getKeyCode()==KeyEvent.VK_D){
            if (this.player1.isChangingWeapons()){
                this.player1.changeWeapon(4);
                System.out.println("current Weapon: "+this.player1.getCurrentWeapon()+"  ID: "+this.player1.getCurrentWeaponId());
                this.player1.changingWeapons(false);
            } else{
//                this.player1.setMoveFlag(false);
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_W){
            if (this.titleMenu){
                this.titleChoice--;
                if (this.titleChoice<0) this.titleChoice=0;
            }
            if (this.player1.isChangingWeapons()){
                this.player1.changeWeapon(1);
                System.out.println("current Weapon: "+this.player1.getCurrentWeapon()+"  ID: "+this.player1.getCurrentWeaponId());
                this.player1.changingWeapons(false);
            } else{
                this.player1.setMoveFlag(true);
            }
        }
        else if (e.getKeyCode()==KeyEvent.VK_S){
            if (this.titleMenu){
                this.titleChoice++;
                if (this.titleChoice>2) this.titleChoice=2;
            }
            if (this.player1.isChangingWeapons()){
                this.player1.changeWeapon(5);
                System.out.println("current Weapon: "+this.player1.getCurrentWeapon()+"  ID: "+this.player1.getCurrentWeaponId());
                this.player1.changingWeapons(false);
            } else{
                this.player1.setMoveFlag(false);
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_UP){
            if (this.titleMenu){
                this.titleChoice--;
                if (this.titleChoice<0) this.titleChoice=0;
            }
            if (this.player2.isChangingWeapons()){
                this.player2.changeWeapon(1);
                System.out.println("current Weapon: "+this.player2.getCurrentWeapon()+"  ID: "+this.player2.getCurrentWeaponId());
                this.player2.changingWeapons(false);
            } else{
                this.player2.setMoveFlag(true);
            }
        }
        else if (e.getKeyCode()==KeyEvent.VK_DOWN){
            if (this.titleMenu){
                this.titleChoice++;
                if (this.titleChoice>2) this.titleChoice=2;
            }
            if (this.player2.isChangingWeapons()){
                this.player2.changeWeapon(5);
                System.out.println("current Weapon: "+this.player2.getCurrentWeapon()+"  ID: "+this.player2.getCurrentWeaponId());
                this.player2.changingWeapons(false);
            } else{
                this.player2.setMoveFlag(false);
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_J){
            if (this.titleMenu){
                performMenuTask(false);
            }
            if (this.player1.getBurstCounter()<this.player1.getMaxBurst()){
                this.player1Bullets.add(this.player1.shoot(this.player2));
                this.player1.incrementBurstCounter();
                System.out.println(this.player1.getBurstCounter());
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_K){
            this.player1.changingWeapons(true);
        }
        if (e.getKeyCode()==KeyEvent.VK_L){
            if (this.titleMenu){
                performMenuTask(true);
            }
            this.player1.defend();
        }
        
        if (e.getKeyCode()==KeyEvent.VK_NUMPAD1){
            if (this.titleMenu){
                performMenuTask(false);
            }
            this.player2Bullets.add(this.player2.shoot(this.player1));
        }
        if (e.getKeyCode()==KeyEvent.VK_NUMPAD2){
            this.player2.changingWeapons(true);
        }
        if (e.getKeyCode()==KeyEvent.VK_NUMPAD3){
            if (this.titleMenu){
                performMenuTask(true);
            }
            this.player2.defend();
        }
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            if (this.titleMenu){
                performMenuTask(false);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_S) this.player1.cancelMove();
        if (e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN) this.player2.cancelMove();
        if (e.getKeyCode()==KeyEvent.VK_L){
            this.player1.noDefend();
        }
        if (e.getKeyCode()==KeyEvent.VK_NUMPAD3){
            this.player2.noDefend();
        }
        if (e.getKeyCode()==KeyEvent.VK_K){
            if (this.player1.isChangingWeapons()){
                this.player1.changeWeapon(3);
            }
            this.player1.changingWeapons(false);
        }
        if (e.getKeyCode()==KeyEvent.VK_NUMPAD2){
            if (this.player2.isChangingWeapons()){
                this.player2.changeWeapon(3);
            }
            this.player2.changingWeapons(false);
        }
        if (e.getKeyCode()==KeyEvent.VK_J){
            this.player1.initBurstCounter();
        }
    }

    public void keyTyped(KeyEvent e) {
//        if (e.getKeyCode()==KeyEvent.VK_K){
//            if (this.player1.getCurrentWeaponId()!=3){
//                this.player1.changeWeapon(3);
//            }
//        }
    }

    public void performMenuTask(boolean cancel){
        switch(this.titleChoice){
        case 0:
            initFlags();
            this.inGame=true;
        case 1: case 2: default:
        }
    }
    public void initFlags(){
        this.inGame=false;
        this.titleMenu=false;
        this.optionsMenu=false;
        this.paused=false;
    }
    public void manageBackground(){
        if (this.inGame){
            if(this.stars.size()<=10){
                if (Math.random()*100>55){
                    this.stars.add(new Star(this.APPLET_WIDTH,rand(400)+90,rand(3),rand(4), new Color(rand(150)+100,rand(200)+50,rand(200)+50,rand(100)+155)));
                }
            }
            if(this.stars.size()>10&&this.stars.size()<=20){
                if (Math.random()*100>75){
                    this.stars.add(new Star(this.APPLET_WIDTH,rand(400)+90,rand(3),rand(3), new Color(rand(150)+100,rand(200)+50,rand(200)+50,rand(100)+155)));
                }
            }
            if(this.stars.size()>20&&this.stars.size()<=30){
                if (Math.random()*100>85){
                    this.stars.add(new Star(this.APPLET_WIDTH,rand(400)+90,rand(2),rand(3), new Color(rand(150)+100,rand(200)+50,rand(200)+50,rand(100)+155)));
                }
            }
            if(this.stars.size()>30&&this.stars.size()<=40){    //50
                if (Math.random()*100>92){
                    this.stars.add(new Star(this.APPLET_WIDTH,rand(400)+90,rand(5),rand(3), new Color(rand(150)+100,rand(200)+50,rand(200)+50,rand(100)+155)));
                }
            }
            if(this.stars.size()>40&&this.stars.size()<=60){    //50
                if (Math.random()*100>96){
                    this.stars.add(new Star(this.APPLET_WIDTH,rand(400)+90,rand(6),rand(4), new Color(rand(150)+100,rand(200)+50,rand(200)+50,rand(100)+155)));
                }
            }
            //System.out.println("No of Stars: "+this.stars.size());
            for (Star star : this.stars){
                if (star.getX()<0){
                    this.stars.remove(star);
                }else{
                    star.moveStars();
                }
            }
        }
    }
    
    public int rand(int top){
        return (int) Math.round(Math.random()*(top-1) +1);
    }
    public void wait (int milliseconds){
        long t0, t1;
        t0 =  System.currentTimeMillis();
        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (milliseconds));
    }
    
    public static void main(String[] args) {
        if (args.length>0){
            if (args[0].equals("0")){
                System.out.println("enableStars=false");
                new Shooter(false);
            }else
                new Shooter(true);
        }else
            new Shooter(true);


    }


    

}
