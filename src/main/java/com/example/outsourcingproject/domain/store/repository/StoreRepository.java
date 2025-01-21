package com.example.outsourcingproject.domain.store.repository;

import com.example.outsourcingproject.common.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 사장님이 폐업하지 않은 가게 수를 조회하는 기능
    Long countByOwnerIdAndIsDeleted(Long ownerId, Integer IsDeleted);

    // 가게 이름으로 가게를 조회할 때 폐업하지 않은 가게만 조회하는 기능
    List<Store> findByStoreNameContainingAndIsDeleted(String storeName, Integer isDeleted);

//    // 가게 카테고리로 가게를 조회할 때 폐업하지 않은 가게만 조회하는 기능
//    List<Store> findByStoreCategoryOne_NameOrStoreCategoryTwo_NameAndIsDeleted(
//        String storeCategoryOneName,
//        String storeCategoryTwoName,
//        Integer isDeleted
//    );
}