package view;
import java.awt.Color;
import java.awt.Component;

import javax.swing.*;

public class ImageListCellRenderer implements ListCellRenderer<Object> {

	@Override
	public Component getListCellRendererComponent(JList<?> jlist, Object value, int cellIndex, boolean isSelected,
			boolean cellHasFocus) {
		if (value instanceof JPanel) {
			Component component = (Component) value;
			component.setForeground(Color.white);
			component.setBackground(isSelected ? UIManager.getColor("Table.focusCellForeground") : Color.white);
			return component;
		} else {
			return new JLabel("");
		}
	}

}