package todolistapp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Dam Linh
 */
public class KulComboBox<E> extends JComboBox<E> {

    private Font elementFont;

    public KulComboBox() {
        init();
    }

    public KulComboBox(E[] items) {
        super(items);
        init();
    }

    private void init() {
        elementFont = Template.getFont().deriveFont(0);
        this.setBackground(Template.getBackground());
        this.setUI(ColorArrowUI.createUI(this));
        this.setRenderer(new MyListCellRender());
    }

    public void setElementFont(Font elementFont) {
        this.elementFont = elementFont;
        this.setRenderer(new MyListCellRender());
    }

    class MyListCellRender implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            final JLabel renderer = new JLabel(value.toString());
            renderer.setOpaque(true);
            renderer.setFont(KulComboBox.this.elementFont);
            renderer.setBackground(Template.getBackground());
            if (isSelected) {
                renderer.setForeground(Color.BLACK);
                renderer.setBackground(Template.getBorderDefault());
            }
            return renderer;
        }
    }
}

// <editor-fold desc="inner class to improve JComboBox's beauty">
class ColorArrowUI extends BasicComboBoxUI {

    public static ComboBoxUI createUI(JComponent c) {
        return new ColorArrowUI();
    }

    @Override
    protected JButton createArrowButton() {
        return new BasicArrowButton(
                BasicArrowButton.SOUTH, Template.getBackground(),
                Template.getTitleBar(), Template.getTitleBar(),
                Template.getTitleBar());
    }
}
//</editor-fold>

