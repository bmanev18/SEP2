package client.model;

import java.io.Serializable;
import java.util.Objects;

public class Chat implements Serializable {
    private int id;
    private String name;

    public Chat(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id == chat.id && name.equals(chat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}