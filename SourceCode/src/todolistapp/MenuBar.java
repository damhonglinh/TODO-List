package todolistapp;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

/**
 *
 * @author Dam Linh
 */
public class MenuBar extends JMenuBar {

    private ArrayList<JMenuItem> items = new ArrayList<>(20);// to group MenuItem together for ease of controlling
    private JMenu fileMenu = new JMenu("File");
    private JMenu editMenu = new JMenu("Edit");
    private JMenuItem newList = new JMenuItem("New List");
    private JMenuItem open = new JMenuItem("Open");
    private JMenuItem close = new JMenuItem("Close");
    private JMenuItem save = new JMenuItem("Save");
    private JMenuItem saveAs = new JMenuItem("Save As...");
    private JMenuItem toHTML = new JMenuItem("HTML File");
    private JMenuItem toTXT = new JMenuItem("Text File");
    private JMenuItem delete = new JMenuItem("Delete Selected Tasks");
    private JMenu exportMenu = new JMenu("Export to...");
    private JMenuItem deleteAll = new JMenuItem("Delete All");
    private JMenuItem newTask = new JMenuItem("New Task");

    public MenuBar() {
        setBackground(Template.getBackground());
        setBorder(new LineBorder(Color.BLACK));
        add(fileMenu);
        add(editMenu);

        newList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK, true));
        items.add(newList);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK, true));
        items.add(open);
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK, true));
        items.add(close);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK, true));
        items.add(save);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK, true));
        items.add(saveAs);
        items.add(toHTML);
        items.add(toTXT);
        exportMenu.add(toHTML);
        exportMenu.add(toTXT);
        fileMenu.add(newList);
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.add(exportMenu);
        fileMenu.add(close);

        items.add(delete);
        items.add(deleteAll);
        items.add(newTask);
        editMenu.add(newTask);
        editMenu.add(delete);
        editMenu.add(deleteAll);

        items.trimToSize();
    }

    public void addListener(MenuBarListener listener) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).addActionListener(listener);
        }
    }

    /**
     * @return the fileMenu
     */
    public JMenu getFileMenu() {
        return fileMenu;
    }

    /**
     * @return the editMenu
     */
    public JMenu getEditMenu() {
        return editMenu;
    }

    /**
     * @return the newList
     */
    public JMenuItem getNewList() {
        return newList;
    }

    /**
     * @return the open
     */
    public JMenuItem getOpen() {
        return open;
    }

    /**
     * @return the close
     */
    public JMenuItem getClose() {
        return close;
    }

    /**
     * @return the save
     */
    public JMenuItem getSave() {
        return save;
    }

    /**
     * @return the saveAs
     */
    public JMenuItem getSaveAs() {
        return saveAs;
    }

    /**
     * @return the toHTML
     */
    public JMenuItem getToHTML() {
        return toHTML;
    }

    /**
     * @return the toTXT
     */
    public JMenuItem getToTXT() {
        return toTXT;
    }

    /**
     * @return the delete
     */
    public JMenuItem getDelete() {
        return delete;
    }

    /**
     * @return the exportMenu
     */
    public JMenu getExportMenu() {
        return exportMenu;
    }

    /**
     * @return the deleteAll
     */
    public JMenuItem getDeleteAll() {
        return deleteAll;
    }

    /**
     * @return the newTask
     */
    public JMenuItem getNewTask() {
        return newTask;
    }
}
