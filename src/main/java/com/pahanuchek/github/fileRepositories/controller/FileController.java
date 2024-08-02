package com.pahanuchek.github.fileRepositories.controller;

import com.pahanuchek.github.fileRepositories.controller.exception.FileNotFoundException;
import com.pahanuchek.github.fileRepositories.model.FileDTO;
import com.pahanuchek.github.fileRepositories.model.FileEntity;
import com.pahanuchek.github.fileRepositories.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/create")
    public Long createFile(@RequestBody FileDTO fileDTO) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setTitle(fileDTO.getTitle());
        fileEntity.setCreationDate(fileDTO.getCreationDate());
        fileEntity.setDescription(fileDTO.getDescription());
        fileEntity.setFileData(Base64.getDecoder().decode(fileDTO.getFileData()));

        return fileService.saveFile(fileEntity);
    }

    @GetMapping("/{id}")
    public FileDTO getFile(@PathVariable Long id){
        Optional<FileEntity> fileEntityOptional = fileService.getFile(id);
        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setTitle(fileEntity.getTitle());
            fileDTO.setCreationDate(fileEntity.getCreationDate());
            fileDTO.setDescription(fileEntity.getDescription());
            fileDTO.setFileData(Base64.getEncoder().encodeToString(fileEntity.getFileData()));
            return fileDTO;
        } else {
            throw new FileNotFoundException("File not found with id " + id);
        }
    }

    @GetMapping("/list")
    public Page<FileDTO> listFiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy) {
        Page<FileEntity> filePage = fileService.listFiles(PageRequest.of(page, size, Sort.by(sortBy).descending()));
        return filePage.map(fileEntity -> {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setTitle(fileEntity.getTitle());
            fileDTO.setCreationDate(fileEntity.getCreationDate());
            fileDTO.setDescription(fileEntity.getDescription());
            fileDTO.setFileData(Base64.getEncoder().encodeToString(fileEntity.getFileData()));
            return fileDTO;
        });
    }
}