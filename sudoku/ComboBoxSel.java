package sudoku;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
//import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ComboBoxSel<E> extends JComboBox<E> {

    public ComboBoxSel() {
        setRenderer(new ComboBoxSelRenderer());
    }
    
    /*private class ComboBoxSelRenderer extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ComboBoxItem cbi = (ComboBoxItem)value;
            if (isSelected) {
                cbi.setBackground(list.getSelectionBackground());
                cbi.setForeground(list.getSelectionForeground());
            } else {
                cbi.setBackground(list.getBackground());
                cbi.setForeground(list.getForeground());
            }
            return cbi;
        }
        
    }*/
    
    private class ComboBoxSelRenderer implements ListCellRenderer<Object> {

        @Override
        public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
           ComboBoxItem cbi = (ComboBoxItem)value;
           if(cbi == null){
               cbi = new ComboBoxItem("","/sudoku/image/easy.png");
           }
            if (isSelected) {
                cbi.setBackground(list.getSelectionBackground());
                cbi.setForeground(list.getSelectionForeground());
            } else {
                cbi.setBackground(list.getBackground());
                cbi.setForeground(list.getForeground());
            }
            return cbi;            
        }

    } 
    
    
    
}
