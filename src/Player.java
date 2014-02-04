import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Player extends GameObject {
    
    

    private CopyOnWriteArrayList<Bullet> bullets;
    
    private boolean movingUp, movingDown, defending;
    private int id;
    private Rectangle rect;
    private Shield shield;
    private Player enemy;
    private boolean changingWeapons;
    
    private String name;
    private double hp, energy;
    private final double  hpMax, energyMax;
    private final double energyCoefficient;
    
    private String[] history;
    
    private HashMap<String,Boolean> weapons;
    private int currentWeapon;
    private int lastWeapon;
    private int burstCounter;
    private final int maxBurst;
    
    
    private int animationPhase;
    private int animationCounter;
    
    public Player(int x, int y, int width, int height,boolean facingRight, String img, int id){
        super(x,y,width,height,facingRight,img);
        this.id=id;
        this.name=img;
        this.hp=this.hpMax=100;
        this.energy=this.energyMax=100;
        this.energyCoefficient=0.05;
        
        this.bullets = new CopyOnWriteArrayList<Bullet>();
        this.rect=new Rectangle(x,y,width,height);
        this.weapons=new HashMap<String,Boolean>();
        this.currentWeapon=3;
        this.lastWeapon=3;
        this.changingWeapons=false;
        this.maxBurst=5;
        
        
        initWeapons();
        initHistory();
        System.out.println("Player initialized. FacingRight = "+this.facingRight+ "  X="+this.x+" width: "+this.width+" Y="+this.y+" height: "+this.height+" image: "+this.img);
    }

    public boolean isMovingUp() {
        return this.movingUp;
    }

    public boolean isMovingDown() {
        return this.movingDown;
    }

    public int getId() {
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    public String getIdAndName(){
        return "ID: "+ this.id+" = "+ this.name;
    }
    
    public String[] getHistory(){
        if (this.history[0]==null)initHistory();
        String s="";
        for (int i=0; i<this.history.length;i++){
            s.concat(this.history[i]);
        }
        return this.history;
        //return s;
    }
    
    public void initShield(){
        if (this.facingRight){
            System.out.println("in Player.initShield() ... facing right.  shield="+this.shield+"  about to create shield for "+this+" ("+this.getName()+")");
            this.shield = new Shield(this.x+this.width+5,this.y+5,1,this,this.enemy);
        } else {
            System.out.println("in Player.initShield() ... NOT facing right  shield="+this.shield+"  about to create shield for "+this+" ("+this.getName()+")");
            System.out.println("creating a shield with x="+(this.x-505));
            this.shield = new Shield(this.x-505,this.y+5,1,this,this.enemy);
        }
    }
    public void initWeapons(){
        this.weapons.put("Normal", true);
        this.weapons.put("Laser", true);
        this.weapons.put("Ion Charge", true);
        this.weapons.put("Mega Shell", true);
        this.weapons.put("Corona Blast", true);
    }
    public void initHistory(){
        this.history = new String[5];
        for (int i=0; i<this.history.length;i++){
            this.history[i]=i+") init\n";
        }
//        System.out.println("History inited: ");
//        printHistory();
    }
    
    public void addHistory(String s){
        pushHistory();
        this.history[0]=s;
    }

    public void pushHistory(){
//        System.out.println("History before pushing:");
//        printHistory();
        for (int i=this.history.length-1;i>=0;i--){
            if (i==0){
                this.history[i]="";
                break;
            }
            this.history[i]=this.history[i-1];
        }
//        System.out.println("History after pushing:");
//        printHistory();
    }
    
//    public void printHistory(){
//        String s="";
//        for (int i=0; i<this.history.length;i++){
//            s+=this.history[i];
//        }
//        System.out.println(s);
//    }
    
    public Rectangle getRect() {
        return this.rect;
    }
    public Shield getShield(){
        return this.shield;
    }
    public HashMap<String, Boolean> getWeapons(){
        return this.weapons;
    }
    public String getCurrentWeapon(){
        return idToWeapon(this.currentWeapon);
    }
    public String getLastWeapon(){
        return idToWeapon(this.lastWeapon);
    }
    public int getCurrentWeaponId(){
        return this.currentWeapon;
    }
    public Player getEnemy() {
        return this.enemy;
    }
    public int getMaxBurst() {
        return this.maxBurst;
    }
    public int getBurstCounter(){
        return this.burstCounter;
    }
    public void incrementBurstCounter(){
        this.burstCounter++;
    }
    public void initBurstCounter(){
        this.burstCounter=0;
    }
    public double getHpMax() {
        return this.hpMax;
    }
    public double getHp() {
        return this.hp;
    }
    public double getEnergyMax() {
        return this.energyMax;
    }
    public double getEnergy() {
        return this.energy;
    }
    public void changeHp(int change) {
        this.hp += change;
    }
    public void changeEnergy(int change) {
        this.energy += change;
    }
    public void setEnemy(Player enemy) {
        this.enemy=enemy;
        System.out.println("Finished setting enemy(="+this.enemy.getName()+") for "+this.getName());
    }
    public boolean isDefending(){
        return this.defending;
    }
    public boolean isChangingWeapons(){
        return this.changingWeapons;
    }
    
    public void changingWeapons(boolean changing){
        this.changingWeapons=changing;
    }
    public void changeWeapon(int weapon){
        if (this.weapons.containsKey(idToWeapon(weapon))) {
            if (this.weapons.get(idToWeapon(weapon))){
                this.lastWeapon=this.currentWeapon;
                this.currentWeapon=weapon;
            }
        }else{
            System.out.println("No weapon named "+idToWeapon(weapon)+" (with id "+weapon+")");
        }
    }
    public String idToWeapon(int id){
        switch(id){
        case 1:
            return "Laser";
        case 2:
            return "Corona Blast";
        case 3:
            return "Normal";
        case 4:
            return "Ion Charge";
        case 5:
            return "Mega Shell";
        default:
            return "FUUUUUUUU";
        }
    }
    
    public CopyOnWriteArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    public void draw(Graphics g){
        //g.drawImage(this.img,this.x,this.y,null);
        
        if (this.facingRight){
            g.drawImage(this.img, this.x, this.y, this.x+this.width, this.y+this.height, 
                this.animationPhase*this.width,0 ,this.animationPhase*this.width+this.width, this.height ,null);
            
        }else{  //flip the image
            g.drawImage(this.img, this.x, this.y, this.x+this.width, this.y+this.height, 
                    this.animationPhase*this.width+this.width,0 ,this.animationPhase*this.width, this.height ,null);
        }

        if (this.defending){
            this.shield.draw(g);
        }
        
        
    }
    public void setMoveFlag(boolean up){
        //System.out.println("IN MOVE! \nmovingUP="+this.movingUp);
        if (up) this.movingUp=true;
        else this.movingDown=true;
    }
    public void move(){
        if (this.defending){
            this.energy-=this.energyCoefficient;
            //System.out.println("DEFENDING  Animationphase: "+this.shield.getAnimationPhase());
            this.shield.increaseCounter();
            if (this.shield.getCounter()>=this.shield.getAnimationPhase()*25)this.shield.increaseAnimationPhase();
            if (this.shield.getAnimationPhase()>4){
                this.shield.resetAnimation();
            }
        }
        if (this.movingUp)this.y--;
        else if (this.movingDown)this.y++; 
        if (this.y<25){
            this.y=25;
        }
        if (this.y>417){
            this.y=417;
        }
        this.animationCounter++;
        if (this.animationCounter>=this.animationPhase*20)this.animationPhase++;
        if (this.animationPhase>17){
            this.animationCounter=0;
            this.animationPhase=0;
        }
        this.shield.updateShield(this.x+this.width+5,this.y+5);
        this.rect=new Rectangle(this.x,this.y,this.width,this.height);
    }
    
    public void cancelMove(){
        this.movingUp=false;
        this.movingDown=false;
    }
    
    public Bullet shoot(Player target){
        return getWeaponsBullet(target);
//        this.bullets.add(new Bullet(this.x+this.width,this.y+this.height/2,5,5,"bullet",target));
//        System.out.println("SIZE: "+this.bullets.size());
    }
    
    public Bullet getWeaponsBullet(Player target){
        if (this.facingRight){
            switch (this.currentWeapon){
            case 1:
                
            case 2:
                
            case 3: default:
                return new Bullet(this.x+this.width,this.y+this.height/2,5,5,3,this.facingRight,"bullet",target);
            case 4:
                
            case 5:
                return new Bullet(this.x+this.width,this.y+this.height/2,15,15,5,this.facingRight,"bullet2",target);
            }
        } else{
            switch (this.currentWeapon){
            case 1:
                
            case 2:
                
            case 3: default:
                return new Bullet(this.x,this.y+this.height/2,5,5,3,this.facingRight,"bullet",target);
            case 4:
                
            case 5:
                return new Bullet(this.x,this.y+this.height/2,15,15,5,this.facingRight,"bullet2",target);
            }
        }
        
    }
    public void defend(){
        this.defending=true;
    }
    public void noDefend(){
        this.defending=false;
        this.shield.resetCounter();
    }

    
    
    
}
