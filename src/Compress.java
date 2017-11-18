import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class Compress extends JPanel{
	
	DADSRD dadsrd;
	double threshold=1;
	
	public Compress(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		
		JSlider thresholdSlider=new JSlider(JSlider.VERTICAL,1,sliderLength,sliderLength);
		thresholdSlider.setPreferredSize(new Dimension(40,60));
		
		JLabel thresLab=new JLabel("threshold");
		thresLab.setPreferredSize(new Dimension(80, 30));
		thresLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSlider factorSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,sliderLength);
		factorSlider.setPreferredSize(new Dimension(40,60));
		factorSlider.setInverted(true);
		
		JLabel facLab=new JLabel("factor");
		facLab.setPreferredSize(new Dimension(80, 30));
		facLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		dadsrd=new DADSRD(w, sliderLength, 3);
		
		//add components
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(thresholdSlider,gbc);
		gbc.gridx=0;
		gbc.gridy=1;
		add(thresLab,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		add(factorSlider,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		add(facLab,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.gridheight=2;
		gbc.gridwidth=6;
		add(dadsrd,gbc);
		gbc.gridwidth=1;
		gbc.gridheight=1;
		
		//add actions
		
		factorSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				double factor=(double)factorSlider.getValue();
				dadsrd.effectLevel=1-(factor*factor*factor*factor/sliderLength/sliderLength/sliderLength/sliderLength);
				dadsrd.update();
			}
		});
		
		thresholdSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				threshold=(double)thresholdSlider.getValue()/sliderLength;
				w.updateIntData();
			}
		});
		
		dadsrd.effectLevel=0;
		dadsrd.initialLevel=0;
		
	}

}
