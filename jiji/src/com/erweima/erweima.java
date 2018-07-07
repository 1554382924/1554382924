package com.erweima;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.ObjectInputStream.GetField;
import java.sql.BatchUpdateException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.JLabel;

import com.swetake.util.Qrcode;

public class erweima {
		public static void main(String[] ages) throws Exception{
			
			int n = 60;
			Qrcode qrcode = new Qrcode();
			String s = 	"BEGIN:VCARD\n"+
			            "VERSION:4.0\n"+ 
			            "PRODID:ez-vcard 0.9.11\n "+
			            "N:吉宇\n"+
			            
			            "ADR;TYPE=dom;TZ=UTC+8:河北海洋大学309\n"+ 
			
			            "TEL;TYPE=cell:17551799262\n"+
			            "URL:http://www.dijiaxueshe.comn\n"+
			            "END:VCARD "; 
						
			int image = 300;
//			int imageSize = image/5;
			byte[] data = s.getBytes("utf-8");
			boolean[][] qrdata = qrcode.calQrcode(data);
			BufferedImage buffere = new BufferedImage(image, image, BufferedImage.TYPE_INT_RGB);
			int width = (buffere.getWidth()-n)/2;
		    int height = (buffere.getHeight()-n)/2;
		    
			Graphics2D ew = buffere.createGraphics();
			
			ew.setBackground(Color.WHITE);
			
			ew.clearRect(0, 0, image, image);
			int starR=254,starG=0,starB=0;
			int endR=0,endG=0,endB=254;
			
			for(int i=0;i<qrdata.length;i++){
				for(int j=0;j<qrdata.length;j++){
					if(qrdata[j][i]){
					    int num1=starR+(endR-starR)*(i+j+1)/2/qrdata.length;
						int num2=starG+(endG-starG)*(i+j+1)/2/qrdata.length;
						int num3=starB+(endB-starB)*(j+i+1)/2/qrdata.length;
						Color color= new Color(num1,num2,num3);
						ew.setColor(color);
						ew.fillRect(i*5+16, j*5+16, 5, 5);
					}
				}
			}
			BufferedImage  logo = scale("C:\\Users\\jiyu\\Desktop\\logo.jpg",n,n,true);
			ew.drawImage(logo,width,height, n, n,  null);
			ew.dispose();
			buffere.flush();
			try {
				ImageIO.write(buffere, "png", new File("E:/qrcode.png"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("生成错误！！");
			}
			System.out.println("生成成功！！");
			
		}
		private static BufferedImage scale(String logopath,int width ,int height,boolean hasFiller) throws Exception{
			double ratio = 0.0;
			File file = new File(logopath);
			BufferedImage srcImage = ImageIO.read(file);
			Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			if((srcImage.getHeight()>height) || (srcImage.getWidth()>width)){
				if(srcImage.getHeight()>srcImage.getWidth()){
					ratio = (new Integer(height)).doubleValue()/srcImage.getHeight();
				}else {
					ratio = (new Integer(width)).doubleValue()/srcImage.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				destImage = op.filter(srcImage, null);
			}
			if(hasFiller){ //补白
				BufferedImage image1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphic = image1.createGraphics();
				graphic.setColor(Color.white);
				graphic.fillRect(0, 0, width, height);
				if(width == destImage.getWidth(null)){
					graphic.drawImage(destImage, 0, (height-destImage.getHeight(null))/2, destImage.getWidth(null),
							destImage.getHeight(null),Color.white,null);
					
				}else{
					graphic.drawImage(destImage, (width-destImage.getWidth(null))/2, 0, destImage.getWidth(null),
							destImage.getHeight(null),Color.white,null);
				}
				graphic.dispose();
				destImage = image1;
				
			}
			return (BufferedImage) destImage;
			
		} 

	}



