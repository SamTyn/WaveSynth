import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class PhaseDistortion extends JPanel {
	
	double midPos=65535;
	DADSRD dadsrd;
	
	public PhaseDistortion(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		
		JSlider phaseDistort=new JSlider(JSlider.HORIZONTAL,1,sliderLength-1,sliderLength/2);
		phaseDistort.setPreferredSize(new Dimension(160,30));
		
		dadsrd=new DADSRD(w, sliderLength, 3);
		
		JLabel phDistLab=new JLabel("distort");
		phDistLab.setPreferredSize(new Dimension(80, 30));
		phDistLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add components
		
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.gridwidth=2;
		add(phaseDistort,gbc);
		gbc.gridwidth=1;
		gbc.gridx=0;
		gbc.gridy=2;
		add(phDistLab,gbc);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight=2;
		gbc.gridwidth=6;
		add(dadsrd,gbc);
		gbc.gridwidth=1;
		gbc.gridheight=1;
		
		//add actions
		
		ChangeListener pdChL=new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				midPos=65535.0+Math.pow((Math.abs((double)phaseDistort.getValue()/sliderLength*2.0-1.0)),0.5)*65535.0*(phaseDistort.getValue()>sliderLength/2?1:-1);
				dadsrd.effectLevel=Math.abs((double)(midPos-65535)/65535);
				dadsrd.update();
			}
		};
		
		phaseDistort.addChangeListener(pdChL);
		
	}
	
}
