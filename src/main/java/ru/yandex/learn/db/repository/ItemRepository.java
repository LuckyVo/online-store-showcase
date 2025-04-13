package ru.yandex.learn.db.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yandex.learn.db.entity.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE " +
            "UPPER(i.title) LIKE CONCAT('%',UPPER(:search),'%') OR " +
            "UPPER(i.description) LIKE CONCAT('%',UPPER(:search),'%')")
    List<Item> search(@Param("search") String search, Pageable pageable);


    @Query("SELECT COUNT(i) FROM Item i WHERE " +
            "UPPER(i.title) LIKE CONCAT('%',UPPER(:search),'%') OR " +
            "UPPER(i.description) LIKE CONCAT('%',UPPER(:search),'%')")
    Long countBySearch(String search);
}
