import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class WaveSynthGUI {
	
	public static int maxWaves=3;
	public static Wave[] waveArray= new Wave[maxWaves];
	public static int numWaves=0;
	public static Dimension lastSize;
	public static String dir=System.getProperty("user.home")+"/WaveSynth/";
	public static Component toolBar;
	public static Component mixer;
	

	public static void main(String[] args) {
		for(int i=0;i<maxWaves;i++){
			Audio.waves[i]=new Audio();
			Process.waves[i]= new Process();
		}
		WaveTables.loadAllWaveforms();
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				JFrame frame=new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				mainWindow(frame);
			}
		});
	}
	
	public static void mainWindow(JFrame frame){
		//create panel for main window
		JPanel p=new JPanel(new GridBagLayout());
		
		//create components
		toolBar= ksToolBar(frame);
		mixer=new Mixer();
		JPanel empty=new JPanel(); //to fill vertical space
		
		//add components
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.NORTHWEST;
		p.add(toolBar, gbc);
		
		/*
		gbc.gridx = 0;
        gbc.gridy = maxWaves+1;
        gbc.weighty=1;
        gbc.weightx=1;
		p.add(mixer, gbc);
		*/
		
		gbc.gridx = 0;
        gbc.gridy = maxWaves+2;
        gbc.weighty=1;
        gbc.weightx=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.NORTH;
		p.add(empty, gbc);
		
		
		
		//add actions
		
		//set content of frame to main window
		frame.setContentPane(p);
		frame.pack();
		frame.setMinimumSize(frame.getPreferredSize());
	}
	
	public static JPanel ksToolBar(JFrame frame){
		JPanel panel= new JPanel(new BorderLayout());
		JToolBar toolBar=new JToolBar("Toolbar");
		
		//create components
		JButton createWave=new JButton("Create new wave");
		JButton saveToFile=new JButton("Save to file");
		
		//add components
		toolBar.add(createWave);
		toolBar.add(saveToFile);
		panel.add(toolBar,BorderLayout.NORTH);
		
		//add actions
		createWave.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(numWaves<maxWaves){
	        		  updateMainWindow(frame);
				  	  JPanel dummyToolBar= ksToolBar(frame);
				  	  GridBagConstraints gbc=new GridBagConstraints();
				  		
				  	  gbc.gridx = 0;
				      gbc.gridy = 0;
				      gbc.fill=GridBagConstraints.HORIZONTAL;
				      gbc.anchor=GridBagConstraints.NORTHWEST;
				      frame.add(dummyToolBar, gbc);
				        
				      gbc.gridx = 0;
				      gbc.gridy = numWaves+1;
				      gbc.fill=GridBagConstraints.HORIZONTAL;
				      gbc.anchor=GridBagConstraints.NORTHWEST;
				      frame.add(new CreateDialog(frame),gbc);
				  		
				  	  Dimension prefSize=frame.getPreferredSize();
				  	  frame.setMinimumSize(frame.getSize());
				  	  frame.remove(dummyToolBar);
					  frame.pack();
					  frame.setMinimumSize(prefSize);
	        	}else{
				  	  JOptionPane.showMessageDialog(frame,"Please delete a wave first.", "Max number of waves reached", JOptionPane.PLAIN_MESSAGE);
				}
	        }
	    });
		
		saveToFile.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(numWaves!=0){
					Object[] selectionValues=new Object[numWaves];
					for(int i=0;i<numWaves;i++){
						selectionValues[i]=waveArray[i];
					}
					Wave toSave=(Wave)JOptionPane.showInputDialog(frame, "Choose", "Which wave do you want to save?", JOptionPane.PLAIN_MESSAGE, null, selectionValues, selectionValues[0]);
					if(toSave!=null){
						if(!new File(WaveSynthGUI.dir.substring(0, WaveSynthGUI.dir.length()-1)).exists()){
							new File(WaveSynthGUI.dir.substring(0, WaveSynthGUI.dir.length()-1)).mkdir();
						}
						int i=0;
						String name=dir+toSave.waveName;
						int nameLen=name.length();
						while(true){
							if(new File(name+".wav").exists()){
								name=name.substring(0, nameLen);
								name+="("+i+")";
								i++;
							}else{
								WAV.createFile(toSave.intData, name+".wav");
								break;
							}
						}
					}
				}
			}
		});
		
		return panel;
	}
	
	public static void updateMainWindow(JFrame frame){
		//create panel for main window
				JPanel p=new JPanel(new GridBagLayout());
				
				//create components
				JPanel empty=new JPanel(); //to fill vertical space
				JPanel dummyToolBar= ksToolBar(frame); //to reserve room for the toolBar when it's out of frame
				
				//add components
				GridBagConstraints gbc=new GridBagConstraints();
				
				gbc.gridx = 0;
		        gbc.gridy = 0;
		        gbc.fill=GridBagConstraints.HORIZONTAL;
		        gbc.anchor=GridBagConstraints.NORTHWEST;
				p.add(dummyToolBar, gbc);
				
				gbc.gridx = 0;
		        gbc.gridy = 0;
		        gbc.fill=GridBagConstraints.HORIZONTAL;
		        gbc.anchor=GridBagConstraints.NORTHWEST;
				p.add(toolBar, gbc);
				
				/*
				gbc.gridx = 0;
		        gbc.gridy = maxWaves+1;
		        gbc.weighty=1;
		        gbc.weightx=1;
				p.add(mixer, gbc);
				*/
				
				gbc.gridx = 0;
		        gbc.gridy = maxWaves+2;
		        gbc.weighty=1;
		        gbc.weightx=1;
		        gbc.fill=GridBagConstraints.HORIZONTAL;
		        gbc.anchor=GridBagConstraints.NORTH;
				p.add(empty, gbc);
				
				for(int i=0;i<maxWaves;i++){
					if(waveArray[i]!=null){
						gbc.gridx = 0;
				        gbc.gridy = i+1;
				        gbc.weightx=0;
				        gbc.weighty=0;
				        gbc.fill=GridBagConstraints.HORIZONTAL;
				        gbc.anchor=GridBagConstraints.NORTHWEST;
						p.add(waveArray[i],gbc);
					}
				}
				
				//add actions
				
				//set content of frame to main window
				frame.setMinimumSize(frame.getSize());
		  		frame.setContentPane(p);
		  		Dimension prefSize=frame.getPreferredSize();
		  		frame.remove(dummyToolBar);
				frame.pack();
				frame.setMinimumSize(prefSize);
	}
	
	public static void resizeMainWindow(JFrame frame){
		JPanel dummyToolBar= ksToolBar(frame);
		GridBagConstraints gbc=new GridBagConstraints();
	  		
	  	gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill=GridBagConstraints.HORIZONTAL;
	    gbc.anchor=GridBagConstraints.NORTHWEST;
	    frame.add(dummyToolBar, gbc);
	  		
	  	Dimension prefSize=frame.getPreferredSize();
	  	frame.setMinimumSize(frame.getSize());
	  	frame.remove(dummyToolBar);
		frame.pack();
		frame.setMinimumSize(prefSize);
	}

}