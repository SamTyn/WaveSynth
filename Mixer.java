import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Mixer extends JPanel{
	
	public Mixer(){
		super(new GridBagLayout());
		
		//create components
		JLabel name=new JLabel("mixer");
		name.setPreferredSize(new Dimension(DrawWave.length+3*80+100+20,30));
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
		JPanel emptyv=new JPanel(); //to fill vertical space
		JPanel emptyh=new JPanel(); //to fill horizontal space
		
		
		//add components
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		add(name,gbc);
		
		gbc.gridx = 10;
        gbc.gridy = 0;
        gbc.weighty=0;
        gbc.weightx=1;
        gbc.fill=GridBagConstraints.VERTICAL;
        gbc.anchor=GridBagConstraints.WEST;
		add(emptyh, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weighty=1;
        gbc.weightx=0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.NORTH;
		add(emptyv, gbc);
		
		//add actions
		
		
	}
}
