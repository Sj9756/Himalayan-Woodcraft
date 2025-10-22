package com.himalayanwc.products.repo;

import com.himalayanwc.products.model.SpecificationField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecificationFieldRepo extends JpaRepository<SpecificationField, Long> {

    List<SpecificationField> findByCategoryId(Long categoryId);

    Optional<SpecificationField> findByFieldNameAndCategoryId(String fieldName, Long categoryId);

    @Query("SELECT sf FROM SpecificationField sf LEFT JOIN FETCH sf.options WHERE sf.category.id = :categoryId")
    List<SpecificationField> findByCategoryIdWithOptions(@Param("categoryId") Long categoryId);
}