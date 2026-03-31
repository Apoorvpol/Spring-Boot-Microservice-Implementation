package com.example.inventory_service.repository;

import com.example.inventory_service.model.Inventory;
import org.flywaydb.core.internal.util.ClassUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long>
{
//    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
//
//    Optional<Inventory> findBySkuCode(List<String> skuCode);

   List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
