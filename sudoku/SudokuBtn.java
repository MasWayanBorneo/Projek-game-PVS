package sudoku;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class SudokuBtn extends JButton {
    
    private int num;
    private String iconPath;
    private int rowId;
    private int colId;
    private boolean isEnabled;
    
    public SudokuBtn(int value, int i, int j){
        setText("");
        setPreferredSize(new Dimension(56, 56));
        setSize(new Dimension(56, 56));
        this.isEnabled = true;
        this.num = value;
        this.rowId = i;
        this.colId = j;
        this.iconPath = "";
        setIcon(null);
    }
    
    public int getValue(){
        return num;
    }
    
    public int getRowId(){
       return rowId; 
    }

    public int getColId(){
       return colId; 
    }
    
    public boolean getIsEnabled(){
        return isEnabled;
    }
    
    public void setValue(int value){
        this.num = value;
        updateIcon(value);
    }
    
    public void setIsEnabled(boolean bool){
        this.isEnabled = bool;
    }
    
    public void updateValue(){
        num ++;
        if (num > 9) {
            num = 0;
        }
        updateIcon(num);  
    }
    
    private void updateIcon(int num){
        Image image;
        Image newImage;
        switch (num) {
            case 0:
                this.iconPath = "";
                this.setIcon(null);
                break;
            case 1:
                this.iconPath = "image/satu.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                
                this.setIcon(new ImageIcon(newImage));
                break;
            case 2:
                this.iconPath = "image/dua.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;
            case 3:
                this.iconPath = "image/tiga.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;                
            case 4:
                this.iconPath = "image/empat.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;             
            case 5:
                this.iconPath = "image/lima.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;
            case 6:
                this.iconPath = "image/enam.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;              
            case 7:
                this.iconPath = "image/tujuh.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;
            case 8:
                this.iconPath = "image/delapan.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;             
            case 9:
                this.iconPath = "image/sembilan.png";
                image = (new ImageIcon(getClass().getResource(this.iconPath))).getImage();
                newImage = image.getScaledInstance(this.getWidth()-((int)(this.getWidth()*0.2)), this.getHeight()-((int)(this.getHeight()*0.2)), java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(newImage));
                break;                
            default:
                throw new AssertionError();
        }        
    }
    
}
