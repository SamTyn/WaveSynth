import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Effects extends JPanel{
	
	PhaseDistortion phaseDistortion;
	Amplitude amplitude;
	Frequency frequency;
	Clip clip;
	WaveForm waveForm;
	FrequencyModulation frequencyModulation;
	Tremble tremble;
	Reverb reverb;
	JPanel smPanel;
	boolean isPlaying=false;
	int sliderLength=300;
	int panelWidth=10;
	
	public Effects(Wave w,JFrame frame){
		super(new GridBagLayout());
		
		//create components
		
			//amplitude effect
			JButton ampl=new JButton("amplitude");
			amplitude=new Amplitude(w,sliderLength);
			amplitude.setVisible(false);
				
			//phase distortion effect
			JButton phaseDist=new JButton("phase");
			phaseDistortion=new PhaseDistortion(w,sliderLength);
			phaseDistortion.setVisible(false);
			
			//frequency effect
			JButton freq=new JButton("frequency");
			frequency=new Frequency(w,sliderLength);
			frequency.setVisible(false);
			
			//frequency modulation effect
			JButton frMo=new JButton("FM");
			frequencyModulation=new FrequencyModulation(w,sliderLength);
			frequencyModulation.setVisible(false);
			
			//clip effect
			JButton cl=new JButton("clip");
			clip=new Clip(w,sliderLength);
			clip.setVisible(false);
				
			//irr low pass filter
			JButton smooth=new JButton("smooth");
			smPanel=new JPanel(new GridBagLayout());
			smPanel.setVisible(false);
			GridBagConstraints smGbc=new GridBagConstraints();
					
				JSlider smFact=new JSlider(JSlider.HORIZONTAL,0,sliderLength,0);
				smFact.setPreferredSize(new Dimension(160,30));
				JButton resetSm=new JButton("reset");
				resetSm.setPreferredSize(new Dimension(80, 30));
				smGbc.gridx=0;
				smGbc.gridy=0;
				smGbc.gridwidth=2;
				smPanel.add(smFact,smGbc);
				smGbc.gridwidth=1;
				smGbc.gridx=0;
				smGbc.gridy=1;
				smPanel.add(resetSm,smGbc);
					
				smFact.addChangeListener(new ChangeListener(){
					@Override
					public void stateChanged(ChangeEvent arg0) {
						w.smoothFactor=(double) 1-1/((double)smFact.getValue()/sliderLength*99+1);
						w.updateIntData();
						w.repaint();
					}
				});
				resetSm.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						smFact.setValue(0);
					}
				});
				
			//waveform effect
			JButton wf=new JButton("waveform");
			waveForm=new WaveForm(w,sliderLength);
			waveForm.setVisible(false);
			
			//tremble effect
			JButton tr=new JButton("tremble");
			tremble=new Tremble(w,sliderLength);
			tremble.setVisible(false);
			
			//reverb effect
			JButton rev=new JButton("reverb");
			reverb=new Reverb(w,sliderLength);
			reverb.setVisible(false);
				
		//add components
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(ampl,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(amplitude,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=1;
		gbc.gridy=0;
		add(phaseDist,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(phaseDistortion,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=2;
		gbc.gridy=0;
		add(cl,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(clip,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=3;
		gbc.gridy=0;
		add(smooth,gbc);
		gbc.gridwidth=2;
		gbc.gridx=3;
		gbc.gridy=1;
		add(smPanel,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=4;
		gbc.gridy=0;
		add(freq,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(frequency,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=5;
		gbc.gridy=0;
		add(frMo,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(frequencyModulation,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=6;
		gbc.gridy=0;
		add(wf,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(waveForm,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=7;
		gbc.gridy=0;
		add(tr,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(tremble,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=8;
		gbc.gridy=0;
		add(rev,gbc);
		gbc.gridwidth=panelWidth;
		gbc.gridx=0;
		gbc.gridy=1;
		add(reverb,gbc);
		
		//add actions
		ampl.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(amplitude.isVisible()){
					amplitude.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					amplitude.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		phaseDist.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(phaseDistortion.isVisible()){
					phaseDistortion.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					phaseDistortion.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		cl.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(clip.isVisible()){
					clip.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					clip.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		smooth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(smPanel.isVisible()){
					smPanel.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					smPanel.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		freq.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frequency.isVisible()){
					frequency.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					frequency.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		wf.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(waveForm.isVisible()){
					waveForm.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					waveForm.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		frMo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frequencyModulation.isVisible()){
					frequencyModulation.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					frequencyModulation.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		tr.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tremble.isVisible()){
					tremble.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					tremble.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		rev.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(reverb.isVisible()){
					reverb.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					setAllInvisible();
					reverb.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
	}
	
	public void setAllInvisible(){
		amplitude.setVisible(false);
		phaseDistortion.setVisible(false);
		smPanel.setVisible(false);
		frequency.setVisible(false);
		clip.setVisible(false);
		waveForm.setVisible(false);
		frequencyModulation.setVisible(false);
		tremble.setVisible(false);
		reverb.setVisible(false);
	}
}
