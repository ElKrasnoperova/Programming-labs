import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer {
public Component getTableCellRendererComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               boolean hasFocus,
                                               int row,
                                               int column) {
   Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
   String cellvalue = value.toString();
   if(column == 7) {
	   cell.setBackground(new Color(Integer.valueOf(cellvalue)));
   }
   else cell.setBackground(Color.WHITE);
   return cell;
}}
