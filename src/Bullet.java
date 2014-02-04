import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Bullet extends GameObject {
    
    final int direction;
    
    private static int id;
    private int currentId;
    private Rectangle rect;
    private Player target;

    private int type;
    private int dmg;
    private int speed;
    
    private int imageWidth;
    private int animationPhase;
    private int animationCounter;
    private int animationPhaseMax;
    
    private final int animationSpeed;
    
    private boolean animated;
    
    public Bullet(int x, int y, int width, int height,int type,boolean facingRight, String img, Player target){
        super(x,y,width,height,facingRight, img);
        this.animationSpeed=30;
        id++;
        this.currentId=id;
        this.rect = new Rectangle(x,y,width,height);
        this.target=target;
        this.type=type;
        
        switch (type){
        case 1:case 2: case 3: default:
            this.dmg=1;
            this.speed=1;
            break;
        case 4: case 5:
            this.dmg=2;
            this.speed=2;
        }
        
        this.imageWidth=this.img.getWidth(this);
        if (this.imageWidth>this.width){
            this.animationPhaseMax=this.imageWidth/this.width;
            this.animated=true;
        } else this.animationPhaseMax=0;
        //System.out.println("Initializin bullet ... animationPhaseMax set to "+this.animationPhaseMax);
        
        
        switch(target.getId()){
            case 1:
                this.direction=-1;
                break;
            case 2:
                this.direction=1;
                break;
            default:
                this.direction=0;
                break;
        }
    }
    
    public void draw(Graphics g){
        if (this.facingRight){
            g.drawImage(this.img, this.x, this.y, this.x+this.width, this.y+this.height, 
                this.animationPhase*this.width,0 ,this.animationPhase*this.width+this.width, this.height ,null);
            
        }else{  //flip the image
            g.drawImage(this.img, this.x, this.y, this.x+this.width, this.y+this.height, 
                    this.animationPhase*this.width+this.width,0 ,this.animationPhase*this.width, this.height ,null);
        }
        g.setColor(Color.GREEN);
        g.drawString(Integer.toString(this.currentId), this.x, this.y-5);
        
    }
    
    public boolean move(){
        this.x+=this.direction*this.speed;
        this.rect=new Rectangle(this.x,this.y,this.width,this.height);
        
        if (this.animated){
            this.animationCounter++;
            if (this.animationCounter>=this.animationPhase*this.animationSpeed){
                this.animationPhase++;
            }
            if (this.animationPhase>=this.animationPhaseMax){
                this.animationPhase=0;
                this.animationCounter=0;
            }
        }
        return checkCollision();
    }
    
    private boolean checkCollision(){
        if (this.target.isDefending()){
            if (this.rect.intersects(this.target.getShield().getRect())){
                this.target.addHistory("Enemys bullet ["+this.currentId+"] was deflected by your shield!\n");
                return true;
            }
        }
            if (this.rect.intersects(this.target.getRect())){
                this.target.addHistory("Enemys bullet ["+this.currentId+"] damaged the ship for "+this.dmg+" damage!\n");
                getTarget().changeHp(-getDamage());
                return true;
            }
        return false;
    }

    public Rectangle getRect() {
        return this.rect;
    }
    public Player getTarget() {
        return this.target;
    }
    public int getDamage() {
        return this.dmg;
    }
    public int getType(){
        return this.type;
    }

}
