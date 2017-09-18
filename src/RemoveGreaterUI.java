import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class RemoveGreaterUI extends JFrame {

	private Toolkit toolkit;

	public RemoveGreaterUI(Collection c, DefaultTableModel model, JTable table) {
		setSize(500, 130);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Remove Greater");

		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		int centreWidth = size.width / 2 - getSize().width / 2;
		int centreHeight = size.height / 2 - getSize().height / 2;
		setLocation(centreWidth, centreHeight);

		JLabel welcomeText = new JLabel("Input the force");
		welcomeText.setFont(new Font(welcomeText.getFont().getName(), welcomeText.getFont().getStyle(), 20));
		JLabel force = new JLabel("Force");
		JTextArea forceField = new JTextArea();

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				c.removeGreater(Integer.valueOf(forceField.getText()));
				for (int i = model.getRowCount() - 1; i >= 0; --i) {
					if (Integer.parseUnsignedInt(
							model.getValueAt(i, 3).toString()) > (Integer.parseInt(forceField.getText()))) {
						model.removeRow(i);
					}
				}

				dispose();
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(welcomeText))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(force)
						.addGroup(layout.createSequentialGroup().addComponent(forceField)))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(remove)));

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(LEADING).addComponent(welcomeText)
						.addGroup(layout.createSequentialGroup().addComponent(force).addComponent(forceField))
						.addGroup(layout.createParallelGroup(LEADING).addComponent(remove))));

	}

	public static void main(String args[]) {
		// RemoveGreaterUI rgui = new RemoveGreaterUI(null, null,);
		// rgui.setVisible(true);
	}
}
