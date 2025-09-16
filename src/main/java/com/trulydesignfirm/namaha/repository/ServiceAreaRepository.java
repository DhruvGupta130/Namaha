package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceAreaRepository extends JpaRepository<ServiceArea, Long> {

    @Query("""
            SELECT s FROM ServiceArea s
            WHERE s.active = true
              AND s.latitude BETWEEN :latMin AND :latMax
              AND s.longitude BETWEEN :lonMin AND :lonMax
            """)
    List<ServiceArea> findActiveWithinBoundingBox(
            @Param("latMin") double latMin,
            @Param("latMax") double latMax,
            @Param("lonMin") double lonMin,
            @Param("lonMax") double lonMax
    );
}