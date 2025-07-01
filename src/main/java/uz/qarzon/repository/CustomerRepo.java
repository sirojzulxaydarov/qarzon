package uz.qarzon.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Store;
import uz.qarzon.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    List<Customer> findAllByStore(Store store);

    List<Customer> findAllByStoreOwner(User currentUser);

    List<Customer> findByStoreIdAndCurrentBalanceGreaterThan(Integer storeId, BigDecimal amount);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.currentBalance=c.currentBalance + :amount WHERE c.id = :id")
    void increaseBalance(Integer id, BigDecimal amount);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.currentBalance=c.currentBalance - :amount WHERE c.id = :id")
    void decreaseBalance(Integer id, BigDecimal amount);

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.store.id =:storeId")
    int countCustomerByStoreId(@Param("storeId") Integer storeId);

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.store.id =:storeId AND c.currentBalance > 0")
    int countDebtCustomerByStoreId(@Param("storeId") Integer storeId);

    @Query("SELECT COALESCE(SUM(c.currentBalance), 0) FROM Customer c WHERE c.store.id =:storeId AND c.currentBalance > 0")
    BigDecimal getTotalDebtCustomersSum(@Param("storeId") Integer storeId);

}
