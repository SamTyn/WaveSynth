import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


@SuppressWarnings("serial")
public class Frequency extends JPanel {
	
DADSRD dadsrd;
	
	public Frequency(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		
		JTextField peakFrequency=new JTextField(String.valueOf(w.baseFrequency));
		JLabel peakFrequencyLabel=new JLabel("peak: ");
		peakFrequencyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextField baseFrequency=new JTextField(String.valueOf(w.peakFrequency));
		JLabel baseFrequencyLabel=new JLabel("base: ");
		baseFrequencyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		dadsrd=new DADSRD(w, sliderLength, 20);
		
		//add components
		
		gbc.gridx=1;
		gbc.gridy=0;
		add(peakFrequency,gbc);
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(peakFrequencyLabel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		add(baseFrequency,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(baseFrequencyLabel,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(dadsrd,gbc);
		
		//add actions
		
		peakFrequency.addFocusListener(new FocusListener(){
			
			double tempBF;
			
			@Override
			public void focusGained(FocusEvent fe) {
				tempBF=Double.parseDouble(peakFrequency.getText());
				peakFrequency.selectAll();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				try{
					w.peakFrequency=Double.parseDouble(peakFrequency.getText().replace(",","."));
					dadsrd.update();
					w.revalidate();
					w.repaint();
				}catch(Exception ex){
					peakFrequency.setText(String.valueOf(tempBF));
					w.revalidate();
					w.repaint();
            		JOptionPane.showMessageDialog(w,"Invalid input, try again.", "Oops!", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
		});
		
		peakFrequency.getDocument().addDocumentListener(new DocumentListener(){

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
		
		peakFrequency.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				peakFrequency.setFocusable(false);
				peakFrequency.setFocusable(true);
			}
			
		});
		
		baseFrequency.addFocusListener(new FocusListener(){
			
			double tempBF;
			
			@Override
			public void focusGained(FocusEvent fe) {
				tempBF=Double.parseDouble(baseFrequency.getText());
				baseFrequency.selectAll();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				try{
					w.baseFrequency=Double.parseDouble(baseFrequency.getText().replace(",","."));
					dadsrd.update();
					w.revalidate();
					w.repaint();
				}catch(Exception ex){
					ex.printStackTrace();
					baseFrequency.setText(String.valueOf(tempBF));
					w.revalidate();
					w.repaint();
            		JOptionPane.showMessageDialog(w,"Invalid input, try again.", "Oops!", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
		});
		
		baseFrequency.getDocument().addDocumentListener(new DocumentListener(){

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
		
		baseFrequency.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				baseFrequency.setFocusable(false);
				baseFrequency.setFocusable(true);
			}
			
		});
		
		dadsrd.initialLevel=0.5;
		dadsrd.effectLevel=0.5;
		
	}
}
