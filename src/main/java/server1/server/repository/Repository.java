package server1.server.repository;

public interface Repository {
    // метод для сохранения текста
    void save(String text);

    // метод для чтения текста
    String read();
}
