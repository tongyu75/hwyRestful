/**
 * 
 */
package com.czz.hwy.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 创建验证码
 * @author 张纪才
 *
 */
public class CreateCheckImg {
    
    /** 
     * 验证码图片的宽度。 
     */  
    private int width = 60;  
    /** 
     * 验证码图片的高度。 
     */  
    private int height = 30;  
    /** 
     * 验证码的数量。 
     */  
    private Random random = new Random();  
      
    public CreateCheckImg(){}  
    /** 
     * 生成随机颜色 
     * @param fc    前景色 
     * @param bc    背景色 
     * @return  Color对象，此Color对象是RGB形式的。 
     */  
    public Color getRandomColor(int fc, int bc) {  
        if (fc > 255)  
            fc = 200;  
        if (bc > 255)  
            bc = 255;  
        int r = fc + random.nextInt(bc - fc);  
        int g = fc + random.nextInt(bc - fc);  
        int b = fc + random.nextInt(bc - fc);  
        return new Color(r, g, b);  
    }  
      
    /** 
     * 绘制干扰线 
     * @param g Graphics2D对象，用来绘制图像 
     * @param nums  干扰线的条数 
     */  
    public void drawRandomLines(Graphics2D g ,int nums ){  
        g.setColor(this.getRandomColor(160, 200)) ;  
        for(int i=0 ; i<nums ; i++){  
            int x1 = random.nextInt(width) ;  
            int y1 = random.nextInt(height);  
            int x2 = random.nextInt(width) ;  
            int y2 = random.nextInt(height) ;  
            g.drawLine(x1, y1, x2, y2) ;  
        }  
    }  
      
    /** 
     * 获取随机字符串， 
     *      此函数可以产生由大小写字母，汉字，数字组成的字符串 
     * @param length    随机字符串的长度 
     * @return  随机字符串 
     * @throws UnsupportedEncodingException 
     */  
    public String drawRandomString(String[] strs , Graphics2D g) throws UnsupportedEncodingException{  
        StringBuffer strbuf = new StringBuffer() ;  
        
        for(int i=0 ; i<strs.length ; i++){
            
            String temp = strs[i];
            
            Color color = new Color(random.nextInt(200) , random.nextInt(155) ,random.nextInt(255) );  
            g.setColor(color) ;  
            //想文字旋转一定的角度  
            AffineTransform trans = new AffineTransform();
            //设置文字的旋转角度及文字的中心点坐标x，y
            trans.rotate(random.nextInt(12)*3.14/180, 12*i, 25) ;  
            //缩放文字  
            float scaleSize = random.nextFloat() + 0.7f;  
            if(scaleSize>1f)  
                scaleSize = 1f ;  
            trans.scale(scaleSize, scaleSize) ;  
            g.setTransform(trans) ;  
            g.drawString(temp, 15*i, 20) ;  
              
            strbuf.append(temp) ;  
        }  
        g.dispose() ;  
        return strbuf.toString() ;  
    }
    //生成指定个数的数字与字母
    public String[] getRandomStr(int itemCount){
        String temp[] = new String[itemCount];
        int itmp = 0 ; 
        for(int i = 0;i<itemCount;i++){
            
            switch(random.nextInt(5)){  
            case 1:     //生成A～Z的字母  
                itmp = random.nextInt(26) + 65 ;  
                temp[i] = String.valueOf((char)itmp);  
                break;  
            case 2:  
                itmp = random.nextInt(26) + 97 ;  
                temp[i] = String.valueOf((char)itmp);  
                break;  
            default:  
                itmp = random.nextInt(10) + 48 ;  
                temp[i] = String.valueOf((char)itmp) ;  
                break;  
            }
        }
        return temp;
    }
    public int getWidth() {  
        return width;  
    }  
    public void setWidth(int width) {  
        this.width = width;  
    }  
    public int getHeight() {  
        return height;  
    }  
    public void setHeight(int height) {  
        this.height = height;  
    }  
}     

