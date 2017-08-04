import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class FrequencyModulation extends JPanel{

	DADSRD dadsrd;
	
	public FrequencyModulation(Wave w, int sliderLength){
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		JTextField frequency=new JTextField("0");
		JLabel frequencyLabel=new JLabel("frequency: ");
		frequencyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextField factor=new JTextField("0");
		JLabel factorLabel=new JLabel("factor: ");
		factorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		dadsrd=new DADSRD(w, sliderLength, 6);
		
		//add components
		gbc.gridx=1;
		gbc.gridy=0;
		add(frequency,gbc);
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(frequencyLabel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		add(factor,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(factorLabel,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(dadsrd,gbc);
		
		//add actions
		frequency.addFocusListener(new FocusListener(){
			
			double tempFFM;
			
			@Override
			public void focusGained(FocusEvent fe) {
				tempFFM=Double.parseDouble(frequency.getText());
				frequency.selectAll();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				try{
					w.frequencyFM=Double.parseDouble(frequency.getText().replace(",","."));
					dadsrd.update();
					w.revalidate();
					w.repaint();
				}catch(Exception ex){
					frequency.setText(String.valueOf(tempFFM));
					w.revalidate();
					w.repaint();
            		JOptionPane.showMessageDialog(w,"Invalid input, try again.", "Oops!", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
		});
		
		frequency.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				w.revalidate();
				w.repaint();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				w.revalidate();
				w.repaint();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				w.revalidate();
				w.repaint();
			}
			
		});
		
		frequency.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				frequency.setFocusable(false);
				frequency.setFocusable(true);
			}
			
		});
		
		factor.addFocusListener(new FocusListener(){
			
			double tempF;
			
			@Override
			public void focusGained(FocusEvent fe) {
				tempF=Double.parseDouble(factor.getText());
				factor.selectAll();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				try{
					w.factorFM=Double.parseDouble(factor.getText().replace(",","."));
					dadsrd.update();
					w.revalidate();
					w.repaint();
				}catch(Exception ex){
					factor.setText(String.valueOf(tempF));
					w.revalidate();
					w.repaint();
            		JOptionPane.showMessageDialog(w,"Invalid input, try again.", "Oops!", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
		});
		
		factor.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				w.revalidate();
				w.repaint();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				w.revalidate();
				w.repaint();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				w.revalidate();
				w.repaint();
			}
			
		});
		
		factor.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				factor.setFocusable(false);
				factor.setFocusable(true);
			}
			
		});
		
		dadsrd.initialLevel=0;
		dadsrd.effectLevel=0.5;
		
	}
}
