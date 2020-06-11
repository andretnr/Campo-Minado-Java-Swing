package br.com.atnunes.campominado.model;


import java.util.ArrayList;
import java.util.List;

/**
 * @author atnunes
 * @version 0.1
 * @email andretnr@gmail.com
 * @since 10/06/2020
 **/

public class Field {
    private final int line;
    private final int column;

    private boolean open = false;
    private boolean pumped = false;
    private boolean checked = false;

    private List<Field> neighborhood = new ArrayList<>();
    private List<ObserverField> observers = new ArrayList<>();

    Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void observerRegister(ObserverField observer) {
        observers.add(observer);
    }

    public void observersNotification(EventField event) {
        observers.stream()
                .forEach(o -> o.eventOcurred(this, event));
    }

    boolean addNeighbor(Field neighbor) {
        boolean differentLine = line != neighbor.line;
        boolean differentColumn = column != neighbor.column;
        boolean diagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(column - neighbor.column);
        int deltaAll = deltaColumn + deltaLine;

        if (deltaAll == 1 && !diagonal) {
            neighborhood.add(neighbor);
            return true;
        } else if (deltaAll == 2 && diagonal) {
            neighborhood.add(neighbor);
            return true;
        } else {
            return false;
        }


    }

    public void switchChecked() {
        if (!open) {
            checked = !checked;
            if (checked) {
                observersNotification(EventField.CHECK);
            } else {
                observersNotification(EventField.UNCHECK);
            }
        }
    }

    public boolean open() {
        if (!open && !checked) {
            if (pumped) {
                observersNotification(EventField.EXPLODE);
                return true;
            }
            setOpen(true);

            if (neighborHoodSecure()) {
                neighborhood.forEach(n -> n.open());
            }
            return true;
        } else {
            return false;
        }
    }

   public boolean neighborHoodSecure() {
        return neighborhood.stream().noneMatch(n -> n.pumped);
    }

    void undermine() {
        pumped = true;
    }

    public boolean isPumped() {
        return pumped;
    }

    public boolean isChecked() {
        return checked;
    }

    void setOpen(boolean open) {
        this.open = open;

        if (open) {
            observersNotification(EventField.OPEN);
        }
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    boolean targetArchieved() {
        boolean unveiled = !pumped && open;
        boolean protect = pumped && checked;
        return unveiled || protect;
    }

   public int neighborhoodPumped() {
        return (int) neighborhood.stream().filter(n -> n.pumped).count();

    }

    void restart() {
        open = false;
        pumped = false;
        checked = false;
        observersNotification(EventField.RESTART);
    }


}
