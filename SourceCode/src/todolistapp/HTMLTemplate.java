package todolistapp;

import datastructure.LinkedPriorityQueue;
import java.awt.Color;
import java.util.Iterator;

/**
 *
 * @author Dam Linh
 */
public class HTMLTemplate {

    private String header;
    private String title;
    private StringBuilder body;

    public HTMLTemplate(LinkedPriorityQueue<Task> tasks, String header, String title) {
        this.header = header;
        this.title = title;

        body = new StringBuilder("\n");

        body.append("<table style='border:1px;' align='center'>\n"
                + "\t<tr style='").append(backgroundColor(Template.getTitleBar())).append(" font-size:20'>\n"
                + "\t\t<th>Priority</th>"
                + "<th ").append(HTMLWidth(150)).append(" style='").append(paddingTopBottom(7)).append("'>Day</th>"
                + "<th ").append(HTMLWidth(70)).append(">Time</th> "
                + "<th>Title</th><th>Description</th>\n"
                + "\t</tr>\n");

        int count = 0;
        Iterator<Task> iter = tasks.getIterator();
        while (iter.hasNext()) {
            Task task = iter.next();
            Color bgColor;
            if (count % 2 == 0) {
                bgColor = Template.getEvenLine();
            } else {
                bgColor = Template.getOddLine();
            }

            body.append("\t<tr style='").append(backgroundColor(bgColor)).append(" font-size:18'>\n");
            body.append("\t\t<td ")
                    .append(alignCenter("center")).append("style='").append(paddingTopBottom(7))
                    .append("'>").append(task.getPriority()).append("</td>\n");
            body.append("\t\t<td ").append(alignCenter("center")).append(">").append(task.getDateString()).append("</td>\n");
            body.append("\t\t<td ").append(alignCenter("center")).append(">").append(task.getTimeString()).append("</td>\n");
            body.append("\t\t<td>").append(task.getTitle()).append("</td>\n");
            body.append("\t\t<td>").append(task.getDescription()).append("</td>\n");
            body.append("\t</tr>\n");
            count++;
        }
        body.append("</table>\n");
    }

    public HTMLTemplate(LinkedPriorityQueue<Task> tasks) {
        this(tasks, "TODOs", "TODO List");
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "<html>\n<head>\n<title>" + title + "</title>\n</head>\n"
                + "<body style='" + backgroundColor(Template.getBackground()) + font() + "margin:0'>\n"
                + "<hr><center><h2>" + header + "</h2></center><hr>\n"
                + body + "</body>\n"
                + "</html>";
    }

    private String backgroundColor(Color color) {
        String s = "background-color:rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ");";
        return s;
    }

    private String font() {
        String s = "font-family:" + Template.getFont().getFontName() + ";";
        return s;
    }

    private String HTMLWidth(int w) {
        String s = "width='" + w + "px;'";
        return s;
    }

    private String alignCenter(String s) {
        String align = "align='" + s + "'";
        return align;
    }

    private String paddingTopBottom(int padding) {
        String s = "padding-top:" + padding + "px; padding-bottom:" + padding + "px;";
        return s;
    }
}
