package todolistapp;

import java.awt.CardLayout;
import java.awt.Component;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import org.joda.time.LocalDate;

/**
 *
 * @author Dam Linh - s3372757
 */
public class Tab extends JPanel implements Observer {

    private CardLayout card = new CardLayout();
    private Model model;
    private ViewList viewList;
    private ViewFormTask viewNewTask;
    private ViewFormTask viewModify;
    private ViewSingleTask viewSingleTask;
    private ViewMonth viewMonth;
    private String currentMainView;
    private String title;
    private boolean isSaved = true;

    public Tab(File file) {
        model = new Model(this, file);
        initialize();
        if (file != null) {
            title = file.getName().replaceAll("\\.linh", "");
        } else {
            title = "Default";
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    public void close() {
        model.close();
    }

    private void initialize() {
        setLayout(card);

        viewList = new ViewList(model);
        viewNewTask = new ViewFormTask(model);
        viewSingleTask = new ViewSingleTask(model);
        viewModify = new ViewFormTask(model);
        viewMonth = new ViewMonth(model);

        add(viewMonth, "viewMonth");
        add(viewList, "viewList");
        add(viewNewTask, "viewNewTask");
        add(viewSingleTask, "viewSingleTask");
        add(viewModify, "viewModify");

        currentMainView = "viewMonth";
        model.addObserver(this);
    }

    @Override
    public void update(Observable obs, Object o) {
        if (o instanceof String) {
            String s = (String) o;
            if (s.equals("New Task")) {//switch to add a new task
                card.show(this, "viewNewTask");

            } else if (s.equals("MainView")) {
                //switch to view all tasks in List or in Month
                card.show(this, currentMainView);
                //update the view that is currently showed
                if (currentMainView.equals("viewMonth")) {
                    viewMonth.refresh();
                } else {
                    viewList.refresh();
                }
            }

        } else if (o instanceof Integer) {//switch view between monthView and listView
            if (currentMainView.equals("viewMonth")) {
                currentMainView = "viewList";
                viewList.refresh();
            } else {
                currentMainView = "viewMonth";
                viewMonth.refresh();
            }
            card.show(this, currentMainView);

        } else if (o instanceof Task) {//switch to view single task
            viewSingleTask.setTask((Task) o);
            card.show(this, "viewSingleTask");

        } else if (o instanceof Task[]) {//switch to Modify 
            Task[] temp = (Task[]) o;
            viewModify.setTask(temp[0]);
            card.show(this, "viewModify");

        } else if (o instanceof LocalDate){//switch to add new task with predefined date
            LocalDate date = (LocalDate) o;
            viewNewTask.setDate(date);
            card.show(this,"viewNewTask");

        } else if (o instanceof Boolean) {//change tab title base on isSaved
            isSaved = (Boolean) o;
            Component parent = this;
            while (!(parent instanceof MainFrame)) {
                parent = parent.getParent();
            }
            MainFrame mf = (MainFrame) parent;
            mf.setTabTitleColor();
        }
    }
}
