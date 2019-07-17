package simutool.aku;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Test extends JFrame {

	private JPanel contentPane;
	private JTextField titleField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(6, 0, 0, 0));
		
		JPanel titlePanel = new JPanel();
		contentPane.add(titlePanel);
		titlePanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel titleLabel = new JLabel("Title");
		titlePanel.add(titleLabel);
		
		titleField = new JTextField();
		titlePanel.add(titleField);
		titleField.setColumns(10);
		
		JPanel descPanel = new JPanel();
		contentPane.add(descPanel);
		descPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel descLabel = new JLabel("Description");
		descLabel.setToolTipText("Enter a description");
		descPanel.add(descLabel);
		
		JTextArea descField = new JTextArea();
		descPanel.add(descField);
		
		JPanel relationPanel = new JPanel();
		contentPane.add(relationPanel);
		relationPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel relationLabel = new JLabel("Relations");
		relationPanel.add(relationLabel);
		generateComboBox(relationPanel);

		
		
	}
	
	public static void generateComboBox(JPanel relationPanel) {
		JPanel relationItem = new JPanel();
		relationPanel.add(relationItem);
		GridBagLayout gbl_relationItem = new GridBagLayout();
		gbl_relationItem.columnWidths = new int[]{322, 100, 0};
		gbl_relationItem.rowHeights = new int[]{36, 0};
		gbl_relationItem.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_relationItem.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		relationItem.setLayout(gbl_relationItem);
		
		JComboBox relationSelect = new JComboBox();
		relationSelect.addItem("Airbus Group");
		relationSelect.addItem("Faurecia");
		relationSelect.addItem("Loiretech SAS");
		relationSelect.addItem("TWI");
		relationSelect.addItem("Ecole Centrale de Nantes");
		relationSelect.addItem("ECN parametric model Simulator");
		relationSelect.addItem("MW Oven Data");
		relationSelect.addItem("ECN Simulation for AGI demonstrator");
		relationSelect.addItem("S-parameters of the tool concrete");

		AutocompleteDecorator.enable(relationSelect);

		
				GridBagConstraints gbc_relationSelect = new GridBagConstraints();
				gbc_relationSelect.fill = GridBagConstraints.BOTH;
				gbc_relationSelect.insets = new Insets(0, 0, 0, 5);
				gbc_relationSelect.gridx = 0;
				gbc_relationSelect.gridy = 0;
				relationItem.add(relationSelect, gbc_relationSelect);
		
		JButton relationAddBtn = new JButton("+");
		relationAddBtn.addActionListener(new RelationAddBtnListener(relationItem, relationPanel));

		GridBagConstraints gbc_relationAddBtn = new GridBagConstraints();
		gbc_relationAddBtn.fill = GridBagConstraints.BOTH;
		gbc_relationAddBtn.gridx = 1;
		gbc_relationAddBtn.gridy = 0;
		relationItem.add(relationAddBtn, gbc_relationAddBtn);
		System.out.println(relationItem);

	}
	
	public static class RelationAddBtnListener implements ActionListener {
		private JPanel parent;
		public  RelationAddBtnListener(JPanel prevPanel, JPanel parent) {
			this.parent = parent;
		}
	    public void actionPerformed(ActionEvent e) {
			generateComboBox(parent);

			//this.dispose();
			//FileService.syncFile(path);
	    }
	}

}
