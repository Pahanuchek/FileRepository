package com.pahanuchek.github.fileRepositories.service;

import com.pahanuchek.github.fileRepositories.model.FileEntity;
import com.pahanuchek.github.fileRepositories.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public Long saveFile(FileEntity fileEntity) {
        FileEntity savedFile = fileRepository.save(fileEntity);
        return savedFile.getId();
    }

    public Optional<FileEntity> getFile(Long id) {
        return fileRepository.findById(id);
    }

    public Page<FileEntity> listFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }
}

