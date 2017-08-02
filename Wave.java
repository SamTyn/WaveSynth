import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Wave extends JPanel{
	String waveName;
	int waveNum;
	int intLength;
	int[] waveForm;
	double[] frequency;
	double baseFrequency;
	double peakFrequency;
	double beginFreq;
	double endFreq;
	double frequencyFM;
	double factorFM;
	double[] position;
	double threshold=1;
	double comprFactor=1;
	int[] intData;
	int beginVal;
	int lastVal;
	double smoothFactor=0;
	double trembleFrequency=0;
	double reverbDistance=0;
	int reverbReflections=1;
	boolean isPlaying=false;
	Effects effects;
	JButton loop;
	JViewport viewp;
	int mstartX;
	int vpStartX;
	double minScaleX;
	double scaleFactor=1.3;
	double freqX=40;
	double freqY=40;
	
	public Wave(JFrame frame, String name, String type, double dur, double baseFrequency){
		super(new GridBagLayout());
		intLength=(int)(44100*dur);
		intData=new int[intLength];
		this.baseFrequency=baseFrequency;
		this.peakFrequency=baseFrequency;
		waveName=name;
			
		//create components	
		Dimension dim=new Dimension(80, 30);
		JSlider pause=new JSlider(JSlider.VERTICAL,0,100,50);
		JSlider intensity=new JSlider(JSlider.VERTICAL,0,100,100);
		pause.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		intensity.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		pause.setPreferredSize(new Dimension(80, 60));
		intensity.setPreferredSize(new Dimension(80, 60));
		JLabel pse=new JLabel("pause ");
		JLabel inten=new JLabel("volume");
		pse.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		inten.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		pse.setPreferredSize(dim);
		inten.setPreferredSize(dim);
		pse.setHorizontalAlignment(SwingConstants.CENTER);
		inten.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel waveName=new JLabel(name);
		waveName.setPreferredSize(new Dimension(100, 60));
		waveName.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
		waveName.setHorizontalAlignment(SwingConstants.CENTER);
		String[] boxOptions={"sine", "triangle", "square", "saw"};
		JComboBox<String> waveType=new JComboBox<>(boxOptions);
		waveType.setPreferredSize(new Dimension(100, 30));
		waveType.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		if(type.equals("sine")){
			waveForm=WaveTables.sine;
			waveType.setSelectedIndex(0);
		}else if(type.equals("saw")){
			waveForm=WaveTables.saw;
			waveType.setSelectedIndex(3);
		}else if(type.equals("square")){
			waveForm=WaveTables.square;
			waveType.setSelectedIndex(2);
		}else if(type.equals("triangle")){
			waveForm=WaveTables.triangle;
			waveType.setSelectedIndex(1);
		}else{
			System.out.println("Wave doesn't exsist");
		}
		loop=new JButton("loop");
		JButton delete=new JButton("delete");
		JButton effectsBut=new JButton("effects");
		effects=new Effects(this,frame);
		effects.setVisible(false);
		loop.setPreferredSize(dim);
		delete.setPreferredSize(dim);
		effectsBut.setPreferredSize(dim);
		loop.setHorizontalAlignment(SwingConstants.CENTER);
		delete.setHorizontalAlignment(SwingConstants.CENTER);
		effectsBut.setHorizontalAlignment(SwingConstants.CENTER);
		DrawWave drawPanel=new DrawWave(this);
		viewp=new JViewport();
		viewp.add(drawPanel);
		viewp.setPreferredSize(new Dimension(DrawWave.length,90));
		JPanel viewpCont=new JPanel();
		viewpCont.setPreferredSize(new Dimension(DrawWave.length,90));
		viewpCont.add(viewp);
		minScaleX=(double)DrawWave.length/intData.length;
		JButton style=new JButton();
		style.setPreferredSize(new Dimension(20,90));
		JPanel emptyv=new JPanel(); //to fill vertical space
		JPanel emptyh=new JPanel(); //to fill horizontal space
		
		//add components
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight=2;
        add(waveName,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight=1;
        add(waveType,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight=2;
        add(intensity,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight=1;
        add(inten,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight=2;
        add(pause,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight=1;
        add(pse,gbc);
        
		gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        add(loop,gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        add(delete,gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        add(effectsBut,gbc);
        
        gbc.gridwidth=2;
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        add(effects,gbc);
        
        gbc.gridwidth=1;
        gbc.gridheight=3;
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor=GridBagConstraints.NORTHWEST;
		add(viewpCont, gbc);
		
		gbc.gridwidth=1;
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.anchor=GridBagConstraints.NORTHWEST;
		add(style, gbc);
        
		gbc.gridheight=1;
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.weighty=0;
        gbc.weightx=1;
        gbc.fill=GridBagConstraints.VERTICAL;
        gbc.anchor=GridBagConstraints.WEST;
		add(emptyh, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty=1;
        gbc.weightx=0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.NORTH;
		add(emptyv, gbc);
		
		for(int i=0;i<WaveSynthGUI.maxWaves;i++){
			if(WaveSynthGUI.waveArray[i]==null){
				WaveSynthGUI.waveArray[i]=this;
				waveNum=i;
				Audio.waves[waveNum].toPlay=intData;
				Process.waves[waveNum].w=this;
				break;
			}
		}
		
		//add actions
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				if(JOptionPane.showConfirmDialog(frame,"Are you sure you want to delete this wave?","Delete wave",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
					WaveSynthGUI.waveArray[waveNum]=null;
					if(isPlaying){
						Audio.waves[waveNum].stopPlaying=true;
					}
					Audio.waves[waveNum]=new Audio();
					for(int i=waveNum;i<WaveSynthGUI.maxWaves-1;i++){
						if(WaveSynthGUI.waveArray[i+1]!=null){
							Process.waves[i].w=WaveSynthGUI.waveArray[i+1];
							WaveSynthGUI.waveArray[i]=WaveSynthGUI.waveArray[i+1];
							Audio.waves[i]=Audio.waves[i+1];
							WaveSynthGUI.waveArray[i].waveNum--;
							WaveSynthGUI.waveArray[i+1]=null;
							Audio.waves[i+1]=new Audio();
						}
					}
					WaveSynthGUI.numWaves--;
					frame.remove(Wave.this);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		
		loop.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent ae){
					try{
						if(!isPlaying){
							for(Wave w:WaveSynthGUI.waveArray){
								if(w!=Wave.this && w!=null && w.isPlaying){
									w.loop.setText("loop");
									Audio.waves[w.waveNum].stopPlaying=true;
									w.isPlaying=false;
								}
							}
							isPlaying=true;
							loop.setText("stop");
							Audio.waves[waveNum].startPlaying=true;
						}else{
							Audio.waves[waveNum].stopPlaying=true;
							isPlaying=false;
							loop.setText("loop");
						}
							
					}catch(Exception e){
						e.printStackTrace();;
					}
				}
		});
		
		effectsBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(effects.isVisible()){
					effects.setVisible(false);
					WaveSynthGUI.resizeMainWindow(frame);
				}else{
					for(Wave w:WaveSynthGUI.waveArray){
						if(w!=Wave.this && w!=null){
							w.effects.setVisible(false);
						}
					}
					effects.setVisible(true);
					WaveSynthGUI.resizeMainWindow(frame);
				}
			}
		});
		
		pause.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent che) {
				Audio.waves[waveNum].pause=(double)pause.getValue()/100;
			}
			
		});
		
		intensity.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent che) {
				Audio.waves[waveNum].maxAmplitude=(double)intensity.getValue()*intensity.getValue()/10000;
			}
			
		});
		
		viewp.addMouseListener(new MouseListener(){
			@Override
			public void mousePressed(MouseEvent me) {
				mstartX=me.getX();
				vpStartX=(int)viewp.getViewPosition().getX();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		});
		
		viewp.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent me) {
				Point p=new Point(vpStartX+mstartX-me.getX(),0);
				if(vpStartX+mstartX-me.getX()<0){
					p=new Point(0,0);
				}else if(vpStartX+mstartX-me.getX()>drawPanel.scaleX*drawPanel.w.intData.length-DrawWave.length){
					p=new Point((int)(drawPanel.scaleX*drawPanel.w.intData.length-DrawWave.length),0);
				}
				viewp.setViewPosition(p);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			}
			
		});
		
		viewp.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				if(mwe.getWheelRotation()>0 && drawPanel.scaleX>minScaleX){
					int newXpos=(int)(Math.round(viewp.getViewPosition().getX()/scaleFactor-(scaleFactor-1)/scaleFactor*mwe.getPoint().getX()));
					drawPanel.scaleX=Math.max(drawPanel.scaleX/scaleFactor,minScaleX);
					if(newXpos>0){
						viewp.setViewPosition(new Point(newXpos,0));
					}else{
						viewp.setViewPosition(new Point(0,0));
					}
					Wave.this.repaint();
				}else if(mwe.getWheelRotation()<0 && drawPanel.scaleX<10){
					int newXpos=(int)(Math.round(viewp.getViewPosition().getX()*scaleFactor+(scaleFactor-1)*mwe.getPoint().getX()));
					drawPanel.scaleX*=scaleFactor;
					viewp.setViewPosition(new Point(newXpos,0));
					Wave.this.repaint();
				}
			}
		});
		
		style.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(drawPanel.lineStyle==false){
					drawPanel.lineStyle=true;
				}else{
					drawPanel.lineStyle=false;
				}
				Wave.this.repaint();
			}
		});
		waveType.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(waveType.getSelectedItem().toString().equals("sine")){
					waveForm=WaveTables.sine;
				}else if(waveType.getSelectedItem().toString().equals("saw")){
					waveForm=WaveTables.saw;
				}else if(waveType.getSelectedItem().toString().equals("square")){
					waveForm=WaveTables.square;
				}else if(waveType.getSelectedItem().toString().equals("triangle")){
					waveForm=WaveTables.triangle;
				}
				updateIntData();
				Wave.this.repaint();
			}
		});
		updateIntData();
	}
	
	public void updateIntData(){
		if(Process.waves[waveNum].startProcessing){
			Process.waves[waveNum].stopProcessing=true;
			while(Process.waves[waveNum].stopProcessing){
				try{
					Thread.sleep(10);
				}catch(Exception e){
				
				}
			}
		}
		Process.waves[waveNum].startProcessing=true;
	}
	
	
	public String toString(){
		return waveName;
	}
}
