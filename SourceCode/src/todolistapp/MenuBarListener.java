package todolistapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URLDecoder;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Dam Linh - s3372757
 */
public class MenuBarListener implements ActionListener {

    private Model model;
    private MyJFileChooser linhFileChooser;
    private MyJFileChooser htmlFileChooser;
    private MyJFileChooser txtFileChooser;

    public MenuBarListener(Model model) {
        this.model = model;
        String dir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            dir = URLDecoder.decode(dir, "UTF-8");
        } catch (Exception e) {
        }
        linhFileChooser = new MyJFileChooser(dir, "linh");
        linhFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        linhFileChooser.setAcceptAllFileFilterUsed(false);
        linhFileChooser.setFileExtension("linh");
        linhFileChooser.setFileFilter(new MyFileFilter("linh"));

        htmlFileChooser = new MyJFileChooser(dir, "html");
        htmlFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        htmlFileChooser.setAcceptAllFileFilterUsed(false);
        htmlFileChooser.setFileExtension("html");
        htmlFileChooser.setFileFilter(new MyFileFilter("html"));

        txtFileChooser = new MyJFileChooser(dir, "txt");
        txtFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        txtFileChooser.setAcceptAllFileFilterUsed(false);
        txtFileChooser.setFileExtension("txt");
        txtFileChooser.setFileFilter(new MyFileFilter("txt"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();

        int returnValue;
        switch (item.getText()) {
            case "New List":
                returnValue = linhFileChooser.showDialog(item.getParent(), "Create");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    model.newList(linhFileChooser.getSelectedFile());
                }
                break;

            case "Open":
                returnValue = linhFileChooser.showOpenDialog(item.getParent());
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    model.open(linhFileChooser.getSelectedFile());
                }
                break;

            case "Close":
                model.close();
                break;

            case "Save As...":
                returnValue = linhFileChooser.showSaveDialog(item.getParent());
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    model.saveAs(linhFileChooser.getSelectedFile());
                }
                break;

            case "Save":
                model.save();
                break;

            case "HTML File":
                returnValue = htmlFileChooser.showDialog(item.getParent(), "Select");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    model.exportHTML(htmlFileChooser.getSelectedFile());
                }
                break;

            case "Text File":
                returnValue = txtFileChooser.showDialog(item.getParent(), "Select");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    model.exportText(txtFileChooser.getSelectedFile());
                }
                break;

            case "Delete Selected Tasks":
                model.deleteSelected();
                break;

            case "Delete All":
                model.deleteAll();
                break;

            case "New Task":
                model.showFormTask();
                break;
        }
    }

    private File appendExtension(File file, String fileExtension) {
        String[] temp = file.getName().split("\\.");
        String ext = temp[temp.length - 1];
        if (!ext.equalsIgnoreCase(fileExtension)) {
            file = new File(file.getPath() + "." + fileExtension);
        }
        return file;
    }

    private class MyFileFilter extends FileFilter {

        private String fileExtension;

        public MyFileFilter(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String[] temp = f.getName().split("\\.");
            String ext = temp[temp.length - 1];
            if (ext.equalsIgnoreCase(fileExtension)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String getDescription() {
            return fileExtension;
        }
    }

    private class MyJFileChooser extends JFileChooser {

        private String fileExtension;

        public MyJFileChooser(String s, String fileExtension) {
            super(s);
            this.fileExtension = fileExtension;
        }

        public void setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        @Override
        public void approveSelection() {
            File f = getSelectedFile();
            if (f.exists() && getDialogType() != OPEN_DIALOG) {
                int result = JOptionPane.showConfirmDialog(this,
                        "File already exists. Do you want to overwrite?",
                        "File exists", JOptionPane.YES_NO_CANCEL_OPTION);
                switch (result) {
                    case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        cancelSelection();
                        return;
                    default:
                        return;
                }
            } else if (!f.exists() && getDialogType() == OPEN_DIALOG) {
                JOptionPane.showMessageDialog(this, "File Not Found!", "File Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            super.approveSelection();
        }

        @Override
        public File getSelectedFile() {
            if (super.getSelectedFile() == null) {
                return null;
            } else {
                return appendExtension(super.getSelectedFile(), fileExtension);
            }
        }
    }
}
