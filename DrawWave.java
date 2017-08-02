import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawWave extends JPanel{
	
	Wave w;
	double scaleX;
	static int length=600;
	boolean lineStyle=false;
	
	public DrawWave(Wave w){
		this.w=w;
		scaleX=Math.max((double)length/w.intData.length,1);
	}
	
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension((int)((double)(w.intData.length)*scaleX),80);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		g2d.scale(scaleX, 1);
		g2d.setStroke(new BasicStroke(0));
		g2d.setColor(Color.blue);
		double begin=w.viewp.getViewPosition().getX()/scaleX;
		double end=Math.min(w.intData.length-1,(w.viewp.getViewPosition().getX()+length)/scaleX);
		if(lineStyle){
			int step=(int)Math.max((end-begin)/(length*20), 1);
			for(int i=(int)(begin);i<end-step;i+=step){
				g2d.drawLine(i, (int)(-(double)w.intData[i]/32767.5*40+40), i+step, (int)(-(double)w.intData[i+step]/32767.5*40+40));
			}
		}else{
			int step=(int)Math.max((end-begin)/(length*20), 1);
			for(int i=(int)(begin);i<end;i+=step){
				g2d.drawLine(i, (int)(-(double)w.intData[i]/32767.5*40+40), i, (int)(-(double)w.intData[i]/32767.5*40+40));
			}
		}
		
		g2d.setColor(Color.black);
		g2d.drawLine(0, 40, w.intData.length-1, 40);
		
	}
}
