import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class Tremble extends JPanel {
	
	DADSRD dadsrd;
	
	public Tremble(Wave w, int sliderLength){
		
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		//create components
		JSlider trembleSlider=new JSlider(JSlider.VERTICAL,0,sliderLength,0);
		trembleSlider.setPreferredSize(new Dimension(40,60));
		
		dadsrd=new DADSRD(w, sliderLength, 6);
		
		JLabel treLab=new JLabel("amount");
		treLab.setPreferredSize(new Dimension(80, 30));
		treLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextField frequency=new JTextField("0");
		JLabel frequencyLabel=new JLabel("frequency: ");
		frequencyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add components
		gbc.gridx=0;
		gbc.gridy=0;
		add(trembleSlider,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(treLab,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		add(frequencyLabel,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		add(frequency,gbc);
		
		gbc.gridx=3;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(dadsrd,gbc);
		
		//add actions
		frequency.addFocusListener(new FocusListener(){
			
			double tempF;
			
			@Override
			public void focusGained(FocusEvent fe) {
				tempF=Double.parseDouble(frequency.getText());
				frequency.selectAll();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				try{
					w.trembleFrequency=Double.parseDouble(frequency.getText().replace(",","."));
					dadsrd.update();
					w.revalidate();
					w.repaint();
				}catch(Exception ex){
					ex.printStackTrace();
					frequency.setText(String.valueOf(tempF));
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
		
		trembleSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				dadsrd.effectLevel=(double)trembleSlider.getValue()/sliderLength;
				dadsrd.update();
			}
		});
		
		dadsrd.effectLevel=0;
		dadsrd.initialLevel=0;
		
	}
	
}
