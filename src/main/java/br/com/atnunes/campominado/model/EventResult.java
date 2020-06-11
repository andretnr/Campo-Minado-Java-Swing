package br.com.atnunes.campominado.model;

public class EventResult {

    private final boolean win;


    public EventResult(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }
}
