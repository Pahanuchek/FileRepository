package com.pahanuchek.github.fileRepositories.repository;

import com.pahanuchek.github.fileRepositories.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}