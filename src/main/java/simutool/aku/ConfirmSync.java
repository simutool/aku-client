package simutool.aku;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class ConfirmSync extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();


	/**
	 * Create the dialog.
	 */
	public ConfirmSync(Path path) {

		
        try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
        setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Synchronizing");
		setBounds(100, 100, 450, 299);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
        setLocationRelativeTo(null);

		{
			JLabel lblDoYouWant = new JLabel("Do you want to synchronize file " + path.getFileName() + "?");
			lblDoYouWant.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblDoYouWant.setHorizontalAlignment(SwingConstants.CENTER);
			lblDoYouWant.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblDoYouWant);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel label = new JLabel("");
				buttonPane.add(label);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new PrivateListener(path));
				okButton.setAction(action);
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JLabel label = new JLabel("");
				buttonPane.add(label);
			}
		}
	}
	
	private class PrivateListener implements ActionListener {
		private Path path;
		public  PrivateListener(Path path) {
			this.path = Paths.get(Config.getConfig().getObserveDirectory() + path);
		}
	    public void actionPerformed(ActionEvent e) {
			dispose();
			FileService.syncFile(path);
	    }
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Ok");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
