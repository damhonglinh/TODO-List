package todolistapp;

import java.io.Serializable;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Dam Linh
 */
public class Task implements Serializable {

    private String title;
    private int priority;
    private String description;
    private LocalDate date;
    private int hour;
    private int min;
    private String timeString;
    private boolean selected;
    private boolean finished;

    public Task(String title, int priority, String description, LocalDate date, int hour, int min) {
        this.title = title;
        this.priority = priority;
        this.description = description;
        this.date = date;
        this.hour = hour;
        this.min = min;
        setTimeString();
    }

    public Task(String title, int priority, String description, LocalDate date) {
        this(title, priority, description, date, -1, -1);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMMM yyyy");
        String s = date.toString(fmt);
        return s;
    }

    public void setDate(LocalDate dt) {
        this.date = dt;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
        setTimeString();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        setTimeString();
    }

    private void setTimeString() {
        if (hour < 0 || min < 0) {
            timeString = "All day";
        } else {
            timeString = String.format("%02d:%02d", hour, min);
        }
    }

    public String getTimeString() {
        return timeString;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return priority + "---" + getDateString() + "---" + getTimeString()
                + "---" + getTitle() + "---" + getDescription();
    }
}
