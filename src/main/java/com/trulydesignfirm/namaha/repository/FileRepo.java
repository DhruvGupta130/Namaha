package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepo extends JpaRepository<ImageFile, Long> {
    Optional<ImageFile> findByPublicId(String publicId);
}
