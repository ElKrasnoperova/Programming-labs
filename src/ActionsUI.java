import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ActionsUI extends JFrame {

	private Toolkit toolkit;
	private Color currentColor = new Color(255, 255, 255);
	

	public ActionsUI(int flag, Collection c, DefaultTableModel model, int realRow, int realId) {
		setSize(600, 330);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		int centreWidth = size.width / 2 - getSize().width / 2;
		int centreHeight = size.height / 2 - getSize().height / 2;
		setLocation(centreWidth, centreHeight);

		JLabel welcomeText = new JLabel("Input information about monster");
		welcomeText.setFont(new Font(welcomeText.getFont().getName(), welcomeText.getFont().getStyle(), 20));
		JLabel name = new JLabel("Name");
		JLabel age = new JLabel("Age");
		JLabel health = new JLabel("Health");
		JLabel force = new JLabel("Force");
		JLabel ordinariness = new JLabel("Ordinariness");
		JLabel numberOfLife = new JLabel("Number of lives");
		JLabel color = new JLabel("Color");
		JLabel mistake = new JLabel();
		mistake.setVisible(false);
		mistake.setMinimumSize(getMinimumSize());

		JTextArea nameField = new JTextArea();
		JTextArea ageField = new JTextArea();
		JTextArea healthField = new JTextArea();
		JTextArea forceField = new JTextArea();
		JTextArea ordinarinessField = new JTextArea();
		JTextArea numberOfLifeField = new JTextArea();

		if (flag == 0) {
			Monster m = c.getById(realId);
			currentColor = c.getColor(m);
		}

		ColorChooserButton colorField = new ColorChooserButton(currentColor);
		colorField.addColorChangedListener(new ColorChooserButton.ColorChangedListener() {
			@Override
			public void colorChanged(Color newColor) {
				currentColor = newColor;
			}
		});

		JButton addIfMax = new JButton("Add if max");
		addIfMax.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					if (!checkNumberField(ageField.getText(), healthField.getText(), ordinarinessField.getText(), 
							forceField.getText(), numberOfLifeField.getText())) {
						mistake.setText("Check data, please. Numbers must be numbers");
						mistake.setVisible(true);
						mistake.setForeground(Color.RED);
						return;
					}
				Monster m = new Monster(42, nameField.getText(), Integer.valueOf(ageField.getText()),
						Integer.valueOf(healthField.getText()), Integer.valueOf(ordinarinessField.getText()),
						Integer.valueOf(forceField.getText()), Integer.valueOf(numberOfLifeField.getText()),
						Color.getColor(color.getName()));
				c.addIfMax(m);
				String[] element = { String.valueOf(m.id), m.name, String.valueOf(m.age), String.valueOf(m.force),
						String.valueOf(m.ordinariness), String.valueOf(m.health), String.valueOf(m.numberOfLife),
						String.valueOf(m.color) };
				model.addRow(element);
				dispose();
			}
		});

		JButton addIfMin = new JButton("Add if min");
		addIfMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!checkNumberField(ageField.getText(), healthField.getText(), ordinarinessField.getText(), 
						forceField.getText(), numberOfLifeField.getText())) {
					mistake.setText("Check data, please. Numbers must be numbers");
					mistake.setVisible(true);
					mistake.setForeground(Color.RED);
					return;
				}
				Monster m = new Monster(42, nameField.getText(), Integer.valueOf(ageField.getText()),
						Integer.valueOf(healthField.getText()), Integer.valueOf(ordinarinessField.getText()),
						Integer.valueOf(forceField.getText()), Integer.valueOf(numberOfLifeField.getText()),
						currentColor);
				c.addIfMin(m);
				String[] element = { String.valueOf(m.id), m.name, String.valueOf(m.age), String.valueOf(m.force),
						String.valueOf(m.ordinariness), String.valueOf(m.health), String.valueOf(m.numberOfLife),
						String.valueOf(m.color) };
				model.addRow(element);
				dispose();
			}
		});

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!checkNumberField(ageField.getText(), healthField.getText(), ordinarinessField.getText(), 
						forceField.getText(), numberOfLifeField.getText())) {
					mistake.setText("Check data, please. Numbers must be numbers");
					mistake.setVisible(true);
					mistake.setForeground(Color.RED);
					return;
				}
				Monster m = new Monster(realId + 1, nameField.getText(), Integer.valueOf(ageField.getText()),
						Integer.valueOf(healthField.getText()), Integer.valueOf(ordinarinessField.getText()),
						Integer.valueOf(forceField.getText()), Integer.valueOf(numberOfLifeField.getText()),
						currentColor);
				c.add(m);
				Object[] element = { m.id, m.name, m.age, (int) m.force.re(), m.ordinariness, m.health, m.numberOfLife,
						m.color.getRGB() };
				model.addRow(element);
				dispose();
			}
		});

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!checkNumberField(ageField.getText(), healthField.getText(), ordinarinessField.getText(), 
						forceField.getText(), numberOfLifeField.getText())) {
					mistake.setText("Check data, please. Numbers must be numbers");
					mistake.setVisible(true);
					mistake.setForeground(Color.RED);
					return;
				}
				Monster mon = c.getById(realId);
				mon.name = nameField.getText();
				mon.age = Integer.parseInt(ageField.getText());
				mon.force = new Complex(Integer.parseInt(forceField.getText()), 0);
				mon.ordinariness = Integer.parseInt(ordinarinessField.getText());
				mon.health = Integer.parseInt(healthField.getText());
				mon.numberOfLife = Integer.parseInt(numberOfLifeField.getText());
				mon.color = currentColor;

				Object[] element = { mon.id, mon.name, mon.age, (int) mon.force.re(), mon.ordinariness, mon.health,
						mon.numberOfLife, mon.color.getRGB() };
				c.save();
				for (int i = 0; i < element.length; i++) {
					model.setValueAt(element[i], realRow, i);
				}
				dispose();
			}

		});

		if (flag == 0) {
			add.setVisible(false);
			addIfMax.setVisible(false);
			addIfMin.setVisible(false);
			update.setVisible(true);
			Monster m = c.getById(realRow + 1);
			currentColor = c.getColor(m);

			nameField.setText(m.name);
			ageField.setText(String.valueOf(m.age));
			healthField.setText(String.valueOf(m.health));
			forceField.setText(String.valueOf((int) m.force.re()));
			ordinarinessField.setText(String.valueOf(m.ordinariness));
			numberOfLifeField.setText(String.valueOf(m.numberOfLife));

		} else {
			add.setVisible(true);
			addIfMax.setVisible(true);
			addIfMin.setVisible(true);
			update.setVisible(false);
		}

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(welcomeText))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(name)
						.addComponent(nameField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(age)
						.addComponent(ageField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(health)
						.addComponent(healthField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(force)
						.addComponent(forceField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(ordinariness)
						.addComponent(ordinarinessField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(numberOfLife)
						.addComponent(numberOfLifeField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(color)
						.addComponent(colorField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(mistake))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(add)
						.addComponent(addIfMin)
						.addComponent(addIfMax)
						.addComponent(update)));

		layout.setHorizontalGroup(layout
				.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(welcomeText)

						.addGroup(layout.createParallelGroup(LEADING)
								.addComponent(name)
								.addComponent(age)
								.addComponent(health)
								.addComponent(force)
								.addComponent(ordinariness)
								.addComponent(numberOfLife)
								.addComponent(color)
								.addComponent(mistake)))
//				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(LEADING)
								.addComponent(nameField)
								.addComponent(ageField)
								.addComponent(healthField)
								.addComponent(forceField)
								.addComponent(ordinarinessField)
								.addComponent(numberOfLifeField)
								.addComponent(colorField)
								.addGroup(layout.createSequentialGroup()
										.addComponent(add)
										.addComponent(addIfMin)
										.addComponent(addIfMax)
										.addComponent(update))));
	}
	boolean checkNumberField(String ...input ) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		for (int i=0; i<input.length; i++) {
			if (!pattern.matcher(input[i]).find()) {
				return false;
			}
		}
		return true;
	}
}
