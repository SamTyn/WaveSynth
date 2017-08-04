import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Input extends JPanel {
	
	double xPos;
	double yPos;
	
	public Input(){
		xPos=80.0;
		yPos=80.0;
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(80,80);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		g2d.setStroke(new BasicStroke(0));
		g2d.setColor(Color.blue);
		for(double i=0;i<xPos;i++){
			g2d.drawLine((int)i,(int)(yPos*Math.pow(i/xPos, xPos/yPos)),(int)i+1, (int)(yPos*Math.pow((i+1)/xPos, xPos/yPos)));
		}
		for(double i=xPos;i<80;i++){
			g2d.drawLine((int)i,(int)((yPos-80)*Math.pow((i-80)/(xPos-80), (xPos-80)/(yPos-80))+80),(int)i+1, (int)((yPos-80)*Math.pow((i-79)/(xPos-80), (xPos-80)/(yPos-80))+80));
		}
		
	}		
	
}
