package todolistapp;

import datastructure.LinkedPriorityQueue;
import java.io.File;
import java.util.Iterator;
import javax.swing.UIManager;

/**
 *
 * @author Dam Linh
 */
public class Main {

    private static int numberOfList = 0;
    private static MainFrame mainFrame;
    private static LinkedPriorityQueue<Tab> tabs = new LinkedPriorityQueue<>();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            System.out.println(e.toString());
        }
        mainFrame = new MainFrame();
        createNewTabList(null);
    }

    public static void createNewTabList(File file) {
        Tab tab = new Tab(file);
        tabs.enqueue(tab);
        mainFrame.addNewTab(tab);
        numberOfList++;
    }

    public static void closeOneTabList(Tab tab) {
        mainFrame.removeTab(tab);
        tabs.remove(tab);
        numberOfList--;
        if (numberOfList == 0) {
            System.exit(0);
        }
    }

    public static void exit() {
        Iterator<Tab> iter = tabs.getIterator();
        while (iter.hasNext()) {
            Tab tabTemp = iter.next();
            tabTemp.close();
        }
    }
}
