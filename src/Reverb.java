import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class Reverb extends JPanel {
	
	DADSRD dadsrd;
	
	public Reverb(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		JSlider reverbSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		reverbSlider.setPreferredSize(new Dimension(40,60));
		JLabel revLab=new JLabel("amount");
		revLab.setPreferredSize(new Dimension(80, 30));
		revLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSlider distanceSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		distanceSlider.setPreferredSize(new Dimension(40,60));
		JLabel disLab=new JLabel("distance");
		disLab.setPreferredSize(new Dimension(80, 30));
		disLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSpinner reflectionSpinner=new JSpinner(new SpinnerNumberModel(1,1,null,1));
		JLabel refLab=new JLabel("reflections");
		refLab.setPreferredSize(new Dimension(80, 30));
		refLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		dadsrd=new DADSRD(w, sliderLength, 6);
		
		//add components
		gbc.gridx=0;
		gbc.gridy=0;
		add(reverbSlider,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(revLab,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		add(distanceSlider,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		add(disLab,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		add(reflectionSpinner,gbc);
		
		gbc.gridx=2;
		gbc.gridy=1;
		add(refLab,gbc);
		
		gbc.gridx=3;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(dadsrd,gbc);
		
		//add actions
		reverbSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				dadsrd.effectLevel=(double)reverbSlider.getValue()/sliderLength;
				dadsrd.update();
			}
		});
		
		distanceSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				w.reverbDistance=(double)distanceSlider.getValue()/sliderLength*4410.0;
				dadsrd.update();
			}
		});
		
		reflectionSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				w.reverbReflections=(int)reflectionSpinner.getValue();
				dadsrd.update();
			}
		});
		
		dadsrd.effectLevel=0;
		dadsrd.initialLevel=0;
		
	}
	
}
