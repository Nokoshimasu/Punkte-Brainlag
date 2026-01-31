package org.example.punkte_brainlag;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Team {
    private final String name;
    private final IntegerProperty punkte = new SimpleIntegerProperty(0);

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getPunkte() {
        return punkte.get();
    }
    public IntegerProperty punkteProperty() {
        return punkte;
    }
    public void addPunkte(int value) {
        punkte.set(punkte.get() + value);
    }
}
