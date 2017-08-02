import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class CreateDialog extends JPanel{
	
	public CreateDialog(JFrame frame){
		//create panel
		super.setLayout(new GridBagLayout());
		
		//create components
		JTextField waveName=new JTextField();
		String[] boxOptions={"sine", "triangle", "square", "saw"};
		JComboBox<String> waveType=new JComboBox<>(boxOptions);
		waveType.setSelectedIndex(0);
		JTextField duration=new JTextField();
		JTextField freq1=new JTextField();
		JTextField freq2=new JTextField();
		JLabel name=new JLabel("Name:");
		JLabel type=new JLabel("Type of wave:");
		JLabel dur=new JLabel("Duration (sec):");
		JLabel baseFrequency=new JLabel("Base frequency: ");
		Dimension dim=new Dimension(80,20);
		waveName.setPreferredSize(dim);
		boolean found=false;
		for(int i=0;i<WaveSynthGUI.maxWaves;i++){
			for(Wave w: WaveSynthGUI.waveArray){
				if(w!=null){
					if(w.waveName.equals("wave"+i)){
					found=true;
					break;
					}
				}
			}
			if(!found){
				waveName.setText("wave"+i);
				break;
			}
			found=false;
		}
		waveType.setPreferredSize(dim);
		duration.setPreferredSize(dim);
		freq1.setPreferredSize(dim);
		freq2.setPreferredSize(dim);
		JButton create=new JButton("create");
		JButton discard=new JButton("discard");
		JPanel emptyv=new JPanel(); //to fill vertical space
		JPanel emptyh=new JPanel(); //to fill horizontal space
		
		//add components
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        add(name,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(waveName,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(type,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(waveType,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(dur,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(duration,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(baseFrequency,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(freq1,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridwidth=2;
        add(create,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(discard,gbc);
        gbc.fill=GridBagConstraints.NONE;
        gbc.gridwidth=1;
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty=0;
        gbc.weightx=1;
        gbc.anchor=GridBagConstraints.WEST;
		add(emptyh, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weighty=1;
        gbc.weightx=0;
        gbc.anchor=GridBagConstraints.NORTH;
		add(emptyv, gbc);
        
		//add actions
		discard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	WaveSynthGUI.updateMainWindow(frame);
            }
        });
		
		create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {	
            	boolean duplName=false;
            	for(Wave w: WaveSynthGUI.waveArray){
            		if(w!=null){
            			if(w.waveName.equals(waveName.getText())){
                			duplName=true;
                		}
            		}
            	}
            	
            	if(!duplName){
            		try{
                		new Wave(frame,waveName.getText(),waveType.getSelectedItem().toString(),Double.parseDouble(duration.getText().replace(",",".")),Double.parseDouble(freq1.getText().replace(",",".")));
                		WaveSynthGUI.numWaves++;
                        WaveSynthGUI.updateMainWindow(frame);
                	}catch(Exception ex){
                		ex.printStackTrace();
                		JOptionPane.showMessageDialog(frame,"Invalid input, try again.", "Oops!", JOptionPane.PLAIN_MESSAGE);
                	}
            	}else{
            		JOptionPane.showMessageDialog(frame,"Choose different name", "Duplicate name", JOptionPane.PLAIN_MESSAGE);
            	}
            }
        });  
	}
}
