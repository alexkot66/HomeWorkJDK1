package Server.Repository;

public interface Repository <T> {
    void save(T text);
    T load();
}
