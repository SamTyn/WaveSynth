import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class DADSRD extends JPanel{
	
	Wave wave;
	
	double exponent;
	double maxExponent;
	
	JSlider delayAtBeginSlider;
	double delayAtBegin=0;
	
	JSlider attackSlider;
	double attack=0;
	JSlider attackFactorSlider;
	double attackFactor;
	double attackScale;
	double attackTarget;
	JCheckBox invertAttack;
	
	JSlider decaySlider;
	double decay=0;
	JSlider decayFactorSlider;
	double decayFactor;
	double decayScale;
	double decayTarget;
	JCheckBox invertDecay;
	
	JSlider sustainSlider;
	double sustain=0;
	
	JSlider releaseSlider;
	double release=0;
	JSlider releaseFactorSlider;
	double releaseFactor;
	double releaseScale;
	double releaseTarget;
	JCheckBox invertRelease;
	
	JSlider delayAtEndSlider;
	double delayAtEnd=0;
	
	double totalSliderVal=0;
	double peak=1;
	double effectLevel=0;
	double initialLevel=0;
	double currentLevel=0;
	
	public DADSRD(Wave w, int sliderLength, double maxExponent){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		wave=w;
		
		this.maxExponent=maxExponent;
		
		Dimension sliderDim=new Dimension(50,60);
		Dimension labelDim=new Dimension(50,30);
		
		//create components
		
		delayAtBeginSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		delayAtBeginSlider.setPreferredSize(sliderDim);
		delayAtBeginSlider.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
		JLabel delAtBegLab=new JLabel("delay");
		delAtBegLab.setPreferredSize(labelDim);
		delAtBegLab.setHorizontalAlignment(SwingConstants.CENTER);
		delAtBegLab.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		
		attackSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		attackSlider.setPreferredSize(sliderDim);
		attackSlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		JLabel attackSliderLab=new JLabel("attack");
		attackSliderLab.setPreferredSize(labelDim);
		attackSliderLab.setHorizontalAlignment(SwingConstants.CENTER);
		attackSliderLab.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		attackFactorSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		attackFactorSlider.setPreferredSize(sliderDim);
		attackFactorSlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		invertAttack=new JCheckBox();
		invertAttack.setPreferredSize(labelDim);
		invertAttack.setHorizontalAlignment(SwingConstants.CENTER);
		invertAttack.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		invertAttack.setBorderPainted(true);
		
		decaySlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		decaySlider.setPreferredSize(sliderDim);
		decaySlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		JLabel decaySliderLab=new JLabel("decay");
		decaySliderLab.setPreferredSize(labelDim);
		decaySliderLab.setHorizontalAlignment(SwingConstants.CENTER);
		decaySliderLab.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		decayFactorSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		decayFactorSlider.setPreferredSize(sliderDim);
		decayFactorSlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		invertDecay=new JCheckBox();
		invertDecay.setPreferredSize(labelDim);
		invertDecay.setHorizontalAlignment(SwingConstants.CENTER);
		invertDecay.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		invertDecay.setBorderPainted(true);
		
		sustainSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		sustainSlider.setPreferredSize(sliderDim);
		sustainSlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		JLabel sustainSliderLab=new JLabel("sustain");
		sustainSliderLab.setPreferredSize(labelDim);
		sustainSliderLab.setHorizontalAlignment(SwingConstants.CENTER);
		sustainSliderLab.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		
		releaseSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		releaseSlider.setPreferredSize(sliderDim);
		releaseSlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		JLabel releaseSliderLab=new JLabel("release");
		releaseSliderLab.setPreferredSize(labelDim);
		releaseSliderLab.setHorizontalAlignment(SwingConstants.CENTER);
		releaseSliderLab.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		releaseFactorSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		releaseFactorSlider.setPreferredSize(sliderDim);
		releaseFactorSlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		invertRelease=new JCheckBox();
		invertRelease.setPreferredSize(labelDim);
		invertRelease.setHorizontalAlignment(SwingConstants.CENTER);
		invertRelease.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		invertRelease.setBorderPainted(true);
		
		delayAtEndSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		delayAtEndSlider.setPreferredSize(sliderDim);
		delayAtEndSlider.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		JLabel delAtEndLab=new JLabel("delay");
		delAtEndLab.setPreferredSize(labelDim);
		delAtEndLab.setHorizontalAlignment(SwingConstants.CENTER);
		delAtEndLab.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		
		//add components
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(delayAtBeginSlider,gbc);
		gbc.gridx=0;
		gbc.gridy=1;
		add(delAtBegLab,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		add(attackSlider,gbc);
		gbc.gridx=2;
		gbc.gridy=0;
		add(attackFactorSlider,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		add(attackSliderLab,gbc);
		gbc.gridx=2;
		gbc.gridy=1;
		add(invertAttack,gbc);
		
		
		gbc.gridx=3;
		gbc.gridy=0;
		add(decaySlider,gbc);
		gbc.gridx=4;
		gbc.gridy=0;
		add(decayFactorSlider,gbc);
		gbc.gridx=3;
		gbc.gridy=1;
		add(decaySliderLab,gbc);
		gbc.gridx=4;
		gbc.gridy=1;
		add(invertDecay,gbc);
		
		gbc.gridx=5;
		gbc.gridy=0;
		add(sustainSlider,gbc);
		gbc.gridx=5;
		gbc.gridy=1;
		add(sustainSliderLab,gbc);
		
		gbc.gridx=6;
		gbc.gridy=0;
		add(releaseSlider,gbc);
		gbc.gridx=7;
		gbc.gridy=0;
		add(releaseFactorSlider,gbc);
		gbc.gridx=6;
		gbc.gridy=1;
		add(releaseSliderLab,gbc);
		gbc.gridx=7;
		gbc.gridy=1;
		add(invertRelease,gbc);
		
		gbc.gridx=8;
		gbc.gridy=0;
		add(delayAtEndSlider,gbc);
		gbc.gridx=8;
		gbc.gridy=1;
		add(delAtEndLab,gbc);
		
		//add actions
		
		ChangeListener dadsrdListener=new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				update();
			}
		};
		
		delayAtBeginSlider.addChangeListener(dadsrdListener);
		
		attackSlider.addChangeListener(dadsrdListener);
		
		attackFactorSlider.addChangeListener(dadsrdListener);
		
		invertAttack.addChangeListener(dadsrdListener);
		
		decaySlider.addChangeListener(dadsrdListener);
		
		decayFactorSlider.addChangeListener(dadsrdListener);
		
		invertDecay.addChangeListener(dadsrdListener);
		
		sustainSlider.addChangeListener(dadsrdListener);
		
		releaseSlider.addChangeListener(dadsrdListener);
		
		releaseFactorSlider.addChangeListener(dadsrdListener);
		
		invertRelease.addChangeListener(dadsrdListener);
		
		delayAtEndSlider.addChangeListener(dadsrdListener);
		
	}
	
	public double value(double position){
		if(totalSliderVal==0){
			return initialLevel;
		}
		if(position==0){
			if(attack==0 && decay==0){
				currentLevel=effectLevel;
			}else if(attack==0){
				currentLevel=1;
			}else{
				currentLevel=0;
			}
		}
		if(position<delayAtBegin){
			return 0;
		}else if(position<delayAtBegin+attack){
			currentLevel+=attackFactor*(attackTarget-currentLevel);
			if(currentLevel>=1){
				currentLevel=1;
			}
			return currentLevel;
		}else if(position<delayAtBegin+attack+decay){
			currentLevel+=decayFactor*(decayTarget-currentLevel);
			return currentLevel;
		}else if(position<delayAtBegin+attack+decay+sustain){
			return currentLevel;
		}else if(position<delayAtBegin+attack+decay+sustain+release){
			if(position<delayAtBegin+attack+decay+sustain+1){
				currentLevel=effectLevel;
			}
			currentLevel+=releaseFactor*(releaseTarget-currentLevel);
			return currentLevel;
		}else{
			return 0;
		}
	}
	
	public void update(){
		
		totalSliderVal=(double)(delayAtBeginSlider.getValue()+attackSlider.getValue()+decaySlider.getValue()+sustainSlider.getValue()+releaseSlider.getValue()+delayAtEndSlider.getValue());
		
		delayAtBegin=delayAtBeginSlider.getValue()*wave.intData.length/totalSliderVal;
		
		decay=decaySlider.getValue()*wave.intData.length/totalSliderVal;
		peak=(decay==0?effectLevel:1);
		exponent=(invertDecay.isSelected()?-1:1)*(double)decayFactorSlider.getValue()/decayFactorSlider.getMaximum()*maxExponent+0.01;
		decayFactor=(decay==0?0:1-Math.pow(0.25, exponent/decay));
		decayScale=1-Math.pow(1-decayFactor,decay);
		decayTarget=1-(1-effectLevel)/decayScale;
		
		attack=attackSlider.getValue()*wave.intData.length/totalSliderVal;
		exponent=(invertAttack.isSelected()?-1:1)*(double)attackFactorSlider.getValue()/attackFactorSlider.getMaximum()*maxExponent+0.01;
		attackFactor=(attack==0?0:1-Math.pow(0.25, exponent/attack));
		attackScale=1-Math.pow(1-attackFactor,attack);
		attackTarget=peak/attackScale;
		
		sustain=sustainSlider.getValue()*wave.intData.length/totalSliderVal;
		
		release=releaseSlider.getValue()*wave.intData.length/totalSliderVal;
		exponent=(invertRelease.isSelected()?-1:1)*(double)releaseFactorSlider.getValue()/releaseFactorSlider.getMaximum()*maxExponent+0.01;
		releaseFactor=(release==0?0:1-Math.pow(0.25, exponent/release));
		releaseScale=(1-Math.pow(1-releaseFactor,release));
		releaseTarget=effectLevel-effectLevel/releaseScale;
		
		delayAtEnd=delayAtEndSlider.getValue()*wave.intData.length/totalSliderVal;
		
		wave.updateIntData();
	}
	
	public boolean overPeak(double i){
		if(i<delayAtBegin+attack){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean overSustain(double i){
		if(i<delayAtBegin+attack+decay+sustain){
			return false;
		}
		else{
			return true;
		}
	}
	
}
