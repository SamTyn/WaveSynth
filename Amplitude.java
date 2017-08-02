import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class Amplitude extends JPanel{
	
	DADSRD dadsrd;
	
	public Amplitude(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		
		JSlider amplitudeSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,sliderLength);
		amplitudeSlider.setPreferredSize(new Dimension(40,60));
		
		dadsrd=new DADSRD(w, sliderLength, 6);
		
		JLabel ampLab=new JLabel("amplitude");
		ampLab.setPreferredSize(new Dimension(80, 30));
		ampLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add components
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(amplitudeSlider,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(ampLab,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(dadsrd,gbc);
		
		//add actions
		
		amplitudeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				dadsrd.effectLevel=(double)amplitudeSlider.getValue()/sliderLength;
				dadsrd.update();
			}
		});
		
		dadsrd.effectLevel=1;
		dadsrd.initialLevel=1;
		
	}

}
