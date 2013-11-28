package todolistapp;

import datastructure.LinkedPriorityQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Observable;
import javax.swing.JOptionPane;
import org.joda.time.LocalDate;

/**
 *
 * @author Dam Linh - s3372757
 */
public class Model extends Observable {

    private LinkedPriorityQueue<Task> tasks;
    private Tab tab;
    private boolean allSelected;
    private File currentFile;
    private boolean isSaved = true;
    private boolean dateAscendingOrder = true;
    private boolean timeAscendingOrder = true;
    private boolean titleAscendingOrder = true;
    private boolean priorityAscendingOrder = true;

    public Model(Tab container, File file) {
        this.tab = container;
        if (file == null) {
            String userHome = System.getProperty("user.home");
            file = new File(userHome, "default.linh");
        }
        this.currentFile = file;
        //load data from currentFile
        loadData();

        //set Comparator
        tasks.setComparator(new TaskPriorityComparator(true));
    }

    private void loadData() {
        ObjectInputStream input = null;
        try {
            FileInputStream fileInput = new FileInputStream(currentFile);
            input = new ObjectInputStream(fileInput);
            tasks = (LinkedPriorityQueue<Task>) input.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + " File not found exception");
            createFile(currentFile, false);
            tasks = new LinkedPriorityQueue<>();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " class not found");
        } catch (IOException e) {
            System.out.println(e.getMessage() + " IOException: File content is invalid");
            tasks = new LinkedPriorityQueue<>();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * if file does not exists or overWrite then create/overWrite it
     */
    private void createFile(File file, boolean overWrite) {
        if (!file.exists() || overWrite) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (Exception e) {
                System.out.println(e.getMessage() + " cannot create file");
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    public void open(File file) {
        Main.createNewTabList(file);
    }

    public void saveAs(File file) {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(tasks);
        } catch (IOException e) {
            System.out.println(e.getMessage() + " IOException");
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
            }
        }
        setIsSave(true);
    }

    public void save() {
        saveAs(currentFile);
    }

    public void newList(File file) {
        createFile(file, true);
        Main.createNewTabList(file);
    }

    public void close() {
        if (saveAndContinue()) {
            Main.closeOneTabList(tab);
        }
    }

    public void exportHTML(File file) {
        HTMLTemplate html = new HTMLTemplate(tasks);
        PrintWriter wr = null;
        try {
            wr = new PrintWriter(file);
            wr.write(html.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (wr != null) {
                wr.close();
            }
        }
    }

    public void exportText(File file) {
        PrintWriter wr = null;
        try {
            wr = new PrintWriter(file);
            wr.write("Priority |       Date       |    Time    | Title | Description\n");
            Iterator<Task> iter = tasks.getIterator();
            while (iter.hasNext()) {
                Task task = iter.next();
                wr.write(String.format("%-8d | ", task.getPriority()));
                wr.write(String.format("%16s | ", task.getDateString()));
                wr.write(String.format("%10s | ", task.getTimeString()));
                wr.write(task.getTitle() + " | ");
                wr.write(task.getDescription());
                wr.write("\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (wr != null) {
                wr.close();
            }
        }
    }

    public void delete(Task task) {
        //ask user for confirmation
        String[] options = {"Sure", "No"};
        int choice = JOptionPane.showOptionDialog(tab,
                "Are you sure you want to delete this task", "Delete This Task",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);
        if (choice == JOptionPane.OK_OPTION) {

            tasks.remove(task);
            setIsSave(false);
            refresh();
        }
    }

    public void deleteSelected() {
        Iterator<Task> iter = tasks.getIterator();
        //check if there is no task selected to delete
        boolean atLeastOneTaskIsSelected = false;
        while (iter.hasNext()) {
            Task temp = iter.next();
            if (temp.isSelected()) {
                atLeastOneTaskIsSelected = true;
                break;
            }
        }
        if (!atLeastOneTaskIsSelected) {
            JOptionPane.showMessageDialog(tab, "Please select at least 1 task to delete");
            return;
        }

        //ask user for confirmation
        String[] options = {"Sure", "No"};
        int choice = JOptionPane.showOptionDialog(tab,
                "Are you sure you want to delete selected tasks?", "Delete Selected Tasks",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);
        if (choice == JOptionPane.OK_OPTION) {

            iter = tasks.getIterator();
            while (iter.hasNext()) {
                Task temp = iter.next();
                if (temp.isSelected()) {
                    tasks.remove(temp);
                }
            }
            setIsSave(false);
            refresh();
        }
    }

    public void deleteAll() {
        //ask user for confirmation
        String[] options = {"Sure", "No"};
        int choice = JOptionPane.showOptionDialog(tab,
                "Are you sure you want to delete all?", "Delete All",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);
        if (choice == JOptionPane.OK_OPTION) {

            Iterator<Task> iter = tasks.getIterator();
            while (iter.hasNext()) {
                iter.remove();
            }
            setIsSave(false);
            refresh();
        }
    }

    /**
     * make a task be finished
     */
    public void finishTask(Task task) {
        task.setFinished(!task.isFinished());
        sort();
        refresh();
        setIsSave(false);
    }

    public void addNewTask(Task task) {
        tasks.enqueue(task, task.getPriority());
        sort();
        setIsSave(false);
        refresh();
    }

    /**
     * this method is just notify the Tab view to display ViewFormTask to add
     * new task
     */
    public void showFormTask() {
        setChanged();
        notifyObservers("New Task");
    }

    /**
     * this method is just notify the Tab view to display ViewFormTask to add
     * new task with a pre-defined date
     */
    public void showFormTask(LocalDate date) {
        setChanged();
        notifyObservers(date);
    }

    /**
     * this method is just notify the Tab view to display ViewFormTask to update
     * task
     */
    public void showFormTask(Task task) {
        setIsSave(false);
        Task[] temp = {task};
        setChanged();
        notifyObservers(temp);
    }

    /**
     * invoked after a task is modify. The model will re-sort the list
     */
    public void modifyTask() {
        sort();
        refresh();
    }

    public void cancelAndBack() {
        refresh();
    }

    /**
     * notify view to display ViewSingleTask
     */
    public void viewTask(Task task) {
        setChanged();
        notifyObservers(task);
    }

    /**
     * ask user to save first and return true if user wants to continue
     */
    public boolean saveAndContinue() {
        if (!isSaved) {
            String[] options = {"Save", "Don't Save", "Cancel"};
            int choice = JOptionPane.showOptionDialog(tab, "File is not saved. Do you want to save?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, JOptionPane.OK_OPTION);
            if (choice == JOptionPane.OK_OPTION) {
                save();
                return true;
            } else if (choice == JOptionPane.NO_OPTION) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public LinkedPriorityQueue<Task> getTasks() {
        return tasks;
    }

    public void selectAll() {
        Iterator<Task> iter = tasks.getIterator();
        boolean isAllSelected = isAllSelected();
        while (iter.hasNext()) {
            iter.next().setSelected(!isAllSelected);
        }
        refresh();
    }

    public boolean isAllSelected() {
        Iterator<Task> iter = tasks.getIterator();
        allSelected = true;
        while (iter.hasNext()) {
            if (!iter.next().isSelected()) {
                allSelected = false;
                break;
            }
        }
        return allSelected;
    }

    public void switchView() {
        setChanged();
        notifyObservers(0);
    }

    public void refresh() {
        setChanged();
        notifyObservers("MainView");
    }

    private void setIsSave(boolean isSaved) {
        this.isSaved = isSaved;
        //notify Tab to set tab's title red if it is not saved yet
        //and set black if it is already saved
        setChanged();
        notifyObservers(isSaved);
    }

    public void sortByPriority() {
        priorityAscendingOrder = !priorityAscendingOrder;
        tasks.setComparator(new TaskPriorityComparator(priorityAscendingOrder));
        sort();
    }

    public void sortByDate() {
        dateAscendingOrder = !dateAscendingOrder;
        tasks.setComparator(new TaskDateComparator(dateAscendingOrder));
        sort();
    }

    public void sortByTime() {
        timeAscendingOrder = !timeAscendingOrder;
        tasks.setComparator(new TaskTimeComparator(timeAscendingOrder));
        sort();
    }

    public void sortByTitle() {
        titleAscendingOrder = !titleAscendingOrder;
        tasks.setComparator(new TaskTitleComparator(titleAscendingOrder));
        sort();
    }

    public void sort() {
        tasks.sort();
    }
}
