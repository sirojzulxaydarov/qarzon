package uz.qarzon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qarzon.entity.Store;
import uz.qarzon.entity.User;

import java.util.List;

public interface StoreRepo extends JpaRepository<Store,Integer> {
    List<Store> findAllByOwner(User owner);
}
