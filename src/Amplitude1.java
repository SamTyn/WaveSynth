import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class Amplitude1 extends JPanel{
	
	DADSRD dadsrd;
	
	public Amplitude1(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		
		JSlider amplitude1Slider=new JSlider(JSlider.VERTICAL,0,sliderLength,sliderLength);
		amplitude1Slider.setPreferredSize(new Dimension(40,60));
		
		dadsrd=new DADSRD(w, sliderLength, 6);
		
		JLabel ampLab=new JLabel("amplitude");
		ampLab.setPreferredSize(new Dimension(80, 30));
		ampLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add components
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(amplitude1Slider,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(ampLab,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(dadsrd,gbc);
		
		//add actions
		
		amplitude1Slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				dadsrd.effectLevel=(double)amplitude1Slider.getValue()/sliderLength;
				dadsrd.update();
			}
		});
		
		dadsrd.effectLevel=1;
		dadsrd.initialLevel=1;
		
	}

}
