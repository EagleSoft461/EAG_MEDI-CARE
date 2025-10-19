package repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    // CREATE
    boolean kaydet(T entity);

    // READ
    List<T> hepsiniGetir();
    Optional<T> idIleGetir(int id);

    // UPDATE
    boolean guncelle(T entity);

    // DELETE
    boolean sil(int id);

    // UTILITY
    boolean varMi(int id);
    int toplamSayi();
}