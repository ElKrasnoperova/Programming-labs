import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import static javax.swing.GroupLayout.Alignment.*;

public class MonstersListUI extends JFrame {

	public TableRowSorter<DefaultTableModel> sorter;
	private Toolkit toolkit;

	private static Collection c;

	int realRow;
	int realId;

	private String[] columnNames = { "Id", "Name", "Age", "Force", "Ordinariness", "Health", "Number of live",
			"Color" };

	private static Object[][] data;

	public MonstersListUI(Collection collection) {

		MonstersListUI.c = collection;
		setSize(500, 580);
		setTitle("Monsters list");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		int centreWidth = size.width / 2 - getSize().width / 2;
		int centreHeight = size.height / 2 - getSize().height / 2;
		setLocation(centreWidth, centreHeight);

		setData(c.getPath());

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 1:
					return String.class;
				case 7:
					return Color.class;
				default:
					return Integer.class;
				}
			}
			
			 @Override
			    public boolean isCellEditable(int i, int i1) {
			        return false;
			    }
		};
		
		JTable table = new JTable(model);
		table.setDefaultRenderer(Object.class, new Renderer());

		
		sorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(sorter);
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setMaximumSize(new Dimension(getMaximumSize().width, 21));
		scrollPane.setColumnHeaderView(table.getTableHeader());
		table.setFillsViewportHeight(true);

		table.setMaximumSize(getMaximumSize());
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int row = table.rowAtPoint(e.getPoint());
					if (row > -1) {
						realRow = table.convertRowIndexToModel(row);
						realId = (int) table.getValueAt(realRow, 0);
					}
				}
			}
		});

		JButton removeGreater = new JButton("Remove Greater");
		removeGreater.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveGreaterUI removeGreaterUI = new RemoveGreaterUI(c, model, table);
				removeGreaterUI.setVisible(true);
			}
		});

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActionsUI actionAdd = new ActionsUI(1, c, model, realRow, realId);
				actionAdd.setTitle("Add");
				actionAdd.setVisible(true);

			}
		});

		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				c.remove(realId);
				model.removeRow(realRow);
			}

		});

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (realRow > 0) {
					ActionsUI actionUpdate = new ActionsUI(0, c, model, realRow, realId);
					actionUpdate.setTitle("Update");
					actionUpdate.setVisible(true);
				}
			}
		});

		JButton filtr = new JButton("Filtr");
		filtr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FiltrUI filtrUI = new FiltrUI(columnNames, table, sorter);
				filtrUI.setVisible(true);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup().addComponent(scrollPane).addComponent(table))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(removeGreater)
						.addComponent(add).addComponent(delete).addComponent(update).addComponent(filtr)));

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(TRAILING).addComponent(scrollPane).addComponent(table)
						.addGroup(layout.createSequentialGroup().addComponent(removeGreater).addComponent(add)
								.addComponent(delete).addComponent(update).addComponent(filtr))));
	}
	

	static void setData(String path) {
		if (path == null) {
			path = "C:\\workspace\\Monsters1.xml";
		}
		Object[][] obj = new Object[c.size()][5];
		int i = 0;
		for (Monster m : c.getMColletion()) {
			Object[] element = { m.id, m.name, m.age, Integer.valueOf((int) m.force.re()), m.ordinariness, m.health,
					m.numberOfLife, m.color.getRGB() };
		
			obj[i] = element;
			i++;
		}
		data = obj;
	}
}