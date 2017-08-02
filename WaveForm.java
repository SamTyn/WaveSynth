import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class WaveForm extends JPanel {
	
	DADSRD dadsrd;
	String waveForm="sine";
	
	public WaveForm(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		
		JSlider waveFormSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,sliderLength);
		waveFormSlider.setPreferredSize(new Dimension(40,60));
		
		dadsrd=new DADSRD(w, sliderLength, 6);
		
		JLabel wfLab=new JLabel("amount");
		wfLab.setPreferredSize(new Dimension(80, 30));
		wfLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		String[] boxOptions={"sine", "triangle", "square", "saw"};
		JComboBox<String> waveType=new JComboBox<>(boxOptions);
		waveType.setPreferredSize(new Dimension(100, 30));
		waveType.setSelectedIndex(0);
		
		JLabel wtLab=new JLabel("type");
		wtLab.setPreferredSize(new Dimension(80, 30));
		wtLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add components
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(waveType,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(wtLab,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		add(waveFormSlider,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		add(wfLab,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(dadsrd,gbc);
		
		//add actions
		
		waveFormSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				dadsrd.effectLevel=(double)waveFormSlider.getValue()/sliderLength;
				dadsrd.update();
			}
		});
		
		waveType.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				waveForm=waveType.getSelectedItem().toString();
				dadsrd.update();
			}
		});
		
		dadsrd.initialLevel=0;
		dadsrd.effectLevel=1;
		
	}
	
}
