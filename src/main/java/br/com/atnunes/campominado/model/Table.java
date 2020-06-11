package br.com.atnunes.campominado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author atnunes
 * @version 0.1
 * @since 10/06/2020
 * @email andretnr@gmail.com
 **/

public class Table implements ObserverField {

    private int lines;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();
    private final List<Consumer<EventResult>> observers = new ArrayList<>();

    public Table(int line, int column, int mines) {
        this.lines = line;
        this.columns = column;
        this.mines = mines;

        crateFields();
        associateNeighborHood();
        sortMines();
    }

    public void forEachField(Consumer<Field> function){
            fields.forEach(function);
    }

    public void registerObserver(Consumer<EventResult> observer){
        observers.add(observer);
    }

    public void observersNotification(Boolean result){
        observers.stream()
                .forEach(o -> o.accept(new EventResult(result)));
    }

    public void open(int line, int column) {

            fields.parallelStream()
                    .filter(c -> c.getLine() == line && c.getColumn() == column)
                    .findFirst()
                    .ifPresent(c -> c.open());

    }



    public void switchChecked(int line, int column) {
        fields.parallelStream()
                .filter(c -> c.getLine() == line && c.getColumn() == column)
                .findFirst()
                .ifPresent(c -> c.switchChecked());

    }

    private void crateFields() {
        for (int l = 0; l < lines; l++) {
            for (int c = 0; c < columns; c++) {
                Field field = new Field(l,c);
                field.observerRegister(this);
                fields.add(field);
            }
        }
    }

    private void associateNeighborHood() {
        for (Field f1 : fields) {
            for (Field f2 : fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void sortMines() {
        long pumpedMines = 0;
        Predicate<Field> pumped = f -> f.isPumped();

        do {
            int random = (int) (Math.random() * fields.size());
            fields.get(random).undermine();
            pumpedMines = fields.stream().filter(pumped).count();
        } while (pumpedMines < mines);

    }

    public boolean targetArchieved() {
        return fields.stream().allMatch(f -> f.targetArchieved());
    }

    public void restart() {
        fields.stream().forEach(f -> f.restart());
        sortMines();
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    @Override
    public void eventOcurred(Field field, EventField event) {
        if (event == EventField.EXPLODE){
            showMines();
            observersNotification(false);
        }else if(targetArchieved()) {
            observersNotification(true);
        }
    }

    private void showMines(){
        fields.stream()
                .filter(f -> f.isPumped())
                .filter(f -> !f.isChecked())
                .forEach(f -> f.setOpen(true));
    }
}
