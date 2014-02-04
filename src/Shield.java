import java.awt.Graphics;
import java.awt.Rectangle;


public class Shield extends GameObject {
    private Player deployer;
    private Player enemy;
    
    private int counter;
    private int animationPhase;
    
    private Rectangle rect;
    
    public Shield(int x, int y, int type,Player deployer, Player enemy){
        super(x,y,deployer.isFacingRight(),type);
        System.out.println("X="+this.x+" width="+this.width);
        this.counter=0;
        this.animationPhase=0;
        
        this.deployer=deployer;
        this.enemy=enemy;
        this.rect=new Rectangle(x,y,this.width,this.height);
        
        System.out.println("deployer is "+this.deployer.getName()+"  =  "+this.deployer);
        System.out.println("Shield initialized. FacingRight ="+ this.facingRight+". deployer is "+this.deployer.getName()+" = "+this.deployer + ". image: "+this.img);
        
    }

    void draw(Graphics g) {
        //System.out.println("drawing. facingRight="+this.facingRight);
        if (this.facingRight){
            g.drawImage(this.img, this.x, this.y, this.x+this.width, this.y+this.height, 
                this.animationPhase*this.width,0 ,this.animationPhase*this.width+this.width, this.height ,null);
            
        }else{  //flip the image
            g.drawImage(this.img, this.x-this.width-60, this.y, this.x-60, this.y+this.height, 
                    this.animationPhase*this.width+this.width,0 ,this.animationPhase*this.width, this.height ,null);
        }
    }
    
    public void updateShield(int x, int y){
        this.x=x;
        this.y=y;
        if (this.facingRight){
            this.rect=new Rectangle(x,y,this.width,this.height);
        }else{
            this.rect=new Rectangle(this.x-this.width-60, this.y,this.width,this.height);
        }
    }
    
    public int getCounter(){
        return this.counter;
    }
    public int getAnimationPhase(){
        return this.animationPhase;
    }
    public Rectangle getRect(){
        return this.rect;
    }
    public Player getDeployer() {
        return this.deployer;
    }
    public Player getEnemy() {
        return this.enemy;
    }
    public void increaseCounter(){
        this.counter++;
    }
    public void increaseAnimationPhase(){
        this.animationPhase++;
    }
    public void resetCounter(){
        this.counter=0;
    }
    public void resetAnimation(){
        this.animationPhase=0;
        resetCounter();
    }

    
}
