import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Star {        //Taustan tähdet
    
    int x;
    int y;
    int radius;
    int speed;
    int angle;
    Color color;
    Image planetImage;
    
    public Star(int x,int y, int radius,int speed, Color color) {
        this.x=x;
        this.y=y;
        this.radius=radius;
        this.speed=speed;
        this.color=color;
    }
    public Star(int x,int y,int speed, Image pic){
        this.x=x;
        this.y=y;
        this.speed=speed;
        this.planetImage=pic;
    }
    
    public void moveStars(){
        this.x-=this.speed;
    }
    
    public void draw(Graphics g){
        if (this.planetImage==null){
            g.setColor(this.color);
            g.drawLine( (this.x-this.radius)-1,this.y,(this.x+this.radius)+1,this.y);
            g.drawLine(this.x,(this.y-this.radius)-1,this.x,(this.y+this.radius)+1);

            g.setColor(this.color.brighter());
            g.drawLine(this.x-1,this.y,this.x,this.y);
            g.setColor(this.color.darker());
            g.drawLine(this.x+1,this.y,this.x,this.y+1);
            if (this.radius>=3){
                g.setColor(this.color.brighter());
                g.fillRect(this.x-1,this.y-1,3,3);
            }
        }else{
            g.drawImage(this.planetImage,this.x,this.y,null);
        }
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getRadius() {
        return this.radius;
    }
    public int getSpeed() {
        return this.speed;
    }
    public int getAngle() {
        return this.angle;
    }
    public Color getColor() {
        return this.color;
    }
    
}
