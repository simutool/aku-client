package simutool.aku;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
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

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;


public class MetadataInput extends JFrame {
	
	private JScrollPane contentPane;
	private JTextField titleField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MetadataInput frame = new MetadataInput(Paths.get("somefile"));
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
	public MetadataInput(Path path) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JScrollPane(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//contentPane.setLayout(new GridLayout(6, 0, 0, 0));
        setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//contentPane.add(scrollPane);
		
		JPanel titlePanel = new JPanel();
		contentPane.add(titlePanel);
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		titleField = new JTextField();
		titlePanel.add(titleField);
		titleField.setColumns(10);
		
		JLabel titleLabel = new JLabel("Title");
		titlePanel.add(titleLabel);
		
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
		
//				JPanel buttonPane = new JPanel();
//				getContentPane().add(buttonPane, BorderLayout.LINE_END);
//				buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//				{
//					JLabel label = new JLabel("");
//					buttonPane.add(label);
//				}
//				{
//					JButton okButton = new JButton("OK");
//					okButton.addActionListener(new MetadataEnteredListener(path));
//				//	okButton.setAction(action);
//					okButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
//					okButton.setActionCommand("OK");
//					buttonPane.add(okButton);
//					getRootPane().setDefaultButton(okButton);
//				}
//				{
//					JButton cancelButton = new JButton("Cancel");
//					cancelButton.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							dispose();
//						}
//					});
//					cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
//					cancelButton.setActionCommand("Cancel");
//					buttonPane.add(cancelButton);
//				}
		
		
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
	
	private class MetadataEnteredListener implements ActionListener {
		private Path path;
		public  MetadataEnteredListener(Path path) {
			this.path = path;
		}
	    public void actionPerformed(ActionEvent e) {
			dispose();
			FileService.syncFile(path);
	    }
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
