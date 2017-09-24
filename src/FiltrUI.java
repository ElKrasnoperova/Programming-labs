import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import static javax.swing.GroupLayout.Alignment.*;

public class FiltrUI extends JFrame {

	private static String chosenColumn;
	private static String filtrValue;
	TableRowSorter<DefaultTableModel> sorter;
	private Color colorValue;

	public FiltrUI(String[] columnNames, JTable table, TableRowSorter<DefaultTableModel> sorter) {
		
		Toolkit toolkit;
		this.sorter = sorter;
		
		setSize(500, 380);
		setTitle("Filtr");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		int centreWidth = size.width/2 - getSize().width/2;
		int centreHeight = size.height/2 - getSize().height/2;
		setLocation(centreWidth, centreHeight);
		
		JLabel welcomeTextField = new JLabel("Choose field");
		JLabel welcomeTextValue = new JLabel("Input value");
		JLabel word = new JLabel("Word     ");
		JLabel number = new JLabel("Number");
		JLabel color = new JLabel("Color     ");
		
		JList columnList = new JList(columnNames);
		columnList.setMaximumSize(getMaximumSize());
		
		JTextField wordField = new JTextField();
		JTextField numberField = new JTextField();
		
		
		ColorChooserButton colorField = new ColorChooserButton(Color.WHITE);
		colorField.addColorChangedListener(new ColorChooserButton.ColorChangedListener() {
		    @Override
		    public void colorChanged(Color newColor) {
		    	colorValue = newColor;
		    }
		});
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String value;
				if (chosenColumn == null) {
					dispose();
					return;
				}
				else if (chosenColumn.equals("Name")) {
					value = wordField.getText();
				}
				else if (chosenColumn.equals("Color")) {
					value = String.valueOf(colorValue.getRGB());
				}
				else { 
					value = numberField.getText();
				}
				newFilter(columnList.getSelectedIndex(), value);
				dispose();
				
			}
		});
			
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newFilter(0, "");
				dispose();
			}
		});
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true); 
        layout.setAutoCreateContainerGaps(true);
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(welcomeTextField)
        				.addComponent(columnList)
        				.addComponent(welcomeTextValue)
        				.addGroup(layout.createParallelGroup(LEADING)
                				.addComponent(word)
                				.addComponent(wordField))
        				.addGroup(layout.createParallelGroup(LEADING)
                				.addComponent(number)
                				.addComponent(numberField))
        				.addGroup(layout.createParallelGroup(LEADING)
                				.addComponent(color)
                				.addComponent(colorField))
        				.addGroup(layout.createParallelGroup(TRAILING)
        						.addComponent(ok)
        						.addComponent(reset))
        				)	
        );
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(welcomeTextField)
        				.addComponent(columnList)
        				.addComponent(welcomeTextValue)
        				.addGroup(layout.createSequentialGroup()
                				.addComponent(word)
                				.addComponent(wordField))
        				.addGroup(layout.createSequentialGroup()
                				.addComponent(number)
                				.addComponent(numberField))
        				.addGroup(layout.createSequentialGroup()
                				.addComponent(color)
                				.addComponent(colorField))
        				.addGroup(layout.createSequentialGroup()
        						.addComponent(ok)
        						.addComponent(reset))
        				)
        		);
        
        columnList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				  String selected = (String) columnList.getSelectedValue();
			        if (selected == "Name") {
			        	wordField.setEditable(true);
			        	numberField.setEditable(false);
			        	colorField.setEnabled(false);
			        	chosenColumn = selected;
			        	filtrValue = wordField.getText();
			        }
			        else if (selected == "Color") {
			        	wordField.setEditable(false);
			        	numberField.setEditable(false);
			        	colorField.setEnabled(true);
			        	chosenColumn = selected;
			        	filtrValue = colorField.toString();
			        }
			        else {
			        	wordField.setEditable(false);
			        	numberField.setEditable(true);
			        	colorField.setEnabled(false);
			        	chosenColumn = selected;
			        	filtrValue = numberField.getText();
			        }
			}
		});
	}
	
	public static String getColumn() {
		return chosenColumn;
	}
	
	public static String getValue() {
		return filtrValue;
	}
	
	private void newFilter(int column, String value) {
		String text = Pattern.quote(value);
		String regex = String.format("^%s$", text);
        sorter.setRowFilter(RowFilter.regexFilter(regex, column));
        if (value.equals("")) {
        	sorter.setRowFilter(RowFilter.regexFilter("", column));
        }
	}
}
