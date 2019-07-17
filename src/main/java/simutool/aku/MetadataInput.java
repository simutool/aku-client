package simutool.aku;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MetadataInput {
	
	private final JPanel contentPanel = new JPanel();
    JFrame frame;
    JPanel buttonPane, fieldsPanel;
    JLabel title, desc;
    JTextField titleField, descField;
    JButton ok, cancel;
    
	
	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	public MetadataInput() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
	//	frame.setBounds(100, 100, 550, 299);
		frame.setSize(300, 400);
	//	getContentPane().add(contentPanel, BorderLayout.CENTER);
		//contentPanel.setLayout(new GridLayout(8, 1, 1, 1));
		frame.setTitle("Please enter metadata");
		frame.setLocationRelativeTo(null);
		
        buttonPane = new JPanel();
        fieldsPanel = new JPanel(new GridLayout(8, 1, 1, 1));
        
        title = new JLabel("title");
        desc = new JLabel("Description");
        titleField = new JTextField("");
        descField = new JTextField("");
        
        ok = new JButton("OK");
        cancel = new JButton("Cancel");

        fieldsPanel.add(descField, 5, 0);
        fieldsPanel.add(desc, 6, 0);
        fieldsPanel.add(titleField, 7, 0);

        fieldsPanel.add(title, 8, 0);
        buttonPane.add(ok);
        buttonPane.add(cancel);
        frame.add(fieldsPanel, BorderLayout.PAGE_START);
        frame.add(buttonPane, BorderLayout.PAGE_END);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	

	public static void main(String[] args) {
		MetadataInput m = new MetadataInput();
		m.getFrame().setVisible(true);
		
	}
	
}
