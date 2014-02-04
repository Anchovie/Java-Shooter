import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


abstract class GameObject extends Applet {
    
    protected int x;
    protected int y;
    protected int height;
    protected int width;
    protected Image img;
    protected boolean facingRight;
    
    
    public GameObject(int x, int y, int height, int width, boolean facingRight, String img) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.facingRight=facingRight;
        this.img = getImage(img);
    }
    public GameObject(int x, int y, boolean facingRight, int type){
        this.x=x;
        this.y=y;
        this.facingRight=facingRight;
        String img="shield"+Integer.toString(type);
        switch(type){
        case 1:
            this.width=20;
            this.height=50;
            break;
        default:
            this.width=20;
            this.height=50;
            break;
        }
        
        this.img = getImage(img);
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }
    public Image getImg() {
        return this.img;
    }
    public boolean isFacingRight(){
        return this.facingRight;
    }
    
    abstract void draw(Graphics g);
    
    public Image getImage(String filename){
        //return Toolkit.getDefaultToolkit().getImage("images\\"+filename+"gif");
        Image img = null;
        try {
//            img =ImageIO.read(new File("D:\\Ohjelmat\\Eclipse\\Omat\\Shooter\\src\\images\\"+filename+".gif"));
//            img =getImage(getCodeBase(),"../../img/Shooter/"+filename+".gif");
            img =ImageIO.read(new File("D:\\Ohjelmat\\Eclipse\\Omat\\Shooter\\src\\images\\"+filename+".gif"));
            //System.out.println("Image ("+filename+") is "+img);
//            System.out.println ("D:\\Ohjelmat\\Eclipse\\Omat\\Shooter\\src\\images\\"+filename+".gif   was searched");
        } catch (IOException e) {
            System.out.println (e);
            System.out.println ("D:\\Ohjelmat\\Eclipse\\Omat\\Shooter\\src\\images\\"+filename+".gif   was searched");
        }
        return img;
    }
    

}
