import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class OpenFileUI extends JFrame {

	private static String fileName;
	private Toolkit toolkit;
	private Collection c = new Collection();

	public OpenFileUI() {
		setSize(480, 580);
		setTitle("Loading monsters");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		int centreWidth = size.width / 2 - getSize().width / 2;
		int centreHeight = size.height / 2 - getSize().height / 2;
		setLocation(centreWidth, centreHeight);

		JPanel panel = new JPanel();
		getContentPane().add(panel);

		JProgressBar prbar = new JProgressBar();
		prbar.setValue(50);
		prbar.setMaximum(100);
		prbar.setBounds(getSize().width / 2 - 50, getSize().height / 2, 100, 10);
		panel.add(prbar);

		JLabel mistake = new JLabel("riguvsyrgsuyblury");
		panel.add(mistake);
		mistake.setVisible(false);
		mistake.setBounds(getSize().width / 2 - 100, getSize().height/2+200, getSize().width, 20);
		mistake.setForeground(Color.RED);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("C:\\Users\\May\\Desktop\\MonsterPicture.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel label = new JLabel(new ImageIcon(image));
		label.setBounds(0, 0, getWidth(), getHeight());
		panel.add(label);
		panel.setLayout(null);

		JButton button = new JButton("Choose file");
		button.setBounds(getSize().width / 2 - 50, getSize().height / 2 - 50, 100, 30);
		
		try {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				int ret = fileopen.showDialog(null, "Open file");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileopen.getSelectedFile();
					fileName = file.getAbsolutePath();
					System.out.println(fileName);
					try {
						c.setPath(fileName);
						if (c.fill()) {
							MonstersListUI monsterList = new MonstersListUI(c);
							monsterList.setVisible(true);
							dispose();
						}
						else {
							mistake.setVisible(true);
							mistake.setText("Problems with the file.Check it, please");
							return;
						}
					}
					catch (NullPointerException npe) {
						npe.getLocalizedMessage();
						mistake.setVisible(true);
						mistake.setText(npe.getLocalizedMessage());
						return;
					}
				}
			}
		});
		}
		catch (NullPointerException npe) {
			System.out.println(npe.getLocalizedMessage());
			mistake.setVisible(true);
			mistake.setText(npe.getLocalizedMessage());
			return;
		}

		panel.add(button);
	}

	public static String getFileName() {
		return fileName;
	}

}