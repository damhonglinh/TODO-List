package todolistapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author Dam Linh - s3372757
 */
public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();

    public MainFrame() {
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(865, 470));
        setVisible(true);
        setTitle("TODO List");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.exit();
            }
        });

        add(tabbedPane);
    }

    public void addNewTab(Tab tab) {
        tabbedPane.add(tab, tab.getTitle());
        tabbedPane.setSelectedComponent(tab);
    }

    public void setTabTitleColor() {
        int count = tabbedPane.getTabCount();
        for (int i = 0; i < count; i++) {
            Tab tabTemp = (Tab) tabbedPane.getComponentAt(i);
            Color color;
            if (tabTemp.getIsSaved()) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }
            tabbedPane.setForegroundAt(i, color);
        }
    }

    public void removeTab(Tab tab) {
        tabbedPane.remove(tab);
    }
}
