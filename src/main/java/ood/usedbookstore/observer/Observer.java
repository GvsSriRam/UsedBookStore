package ood.usedbookstore.observer;

public interface Observer <T> {
    void update(T object, String message);
}
