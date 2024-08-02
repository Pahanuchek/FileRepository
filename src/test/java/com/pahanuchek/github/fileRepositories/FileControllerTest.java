package com.pahanuchek.github.fileRepositories;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.util.Optional;
import com.pahanuchek.github.fileRepositories.controller.FileController;
import com.pahanuchek.github.fileRepositories.model.FileDTO;
import com.pahanuchek.github.fileRepositories.model.FileEntity;
import com.pahanuchek.github.fileRepositories.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateFile() throws Exception {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setTitle("Test File");
        fileDTO.setCreationDate(LocalDateTime.now());
        fileDTO.setDescription("Description");
        fileDTO.setFileData("SGVsbG8sIFdvcmxkIQ==");

        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);

        when(fileService.saveFile(fileEntity)).thenReturn(1L);

        mockMvc.perform(post("/api/files/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test File\", \"creationDate\": \"2024-08-01T10:00:00\", \"description\": \"Description\", \"fileData\": \"SGVsbG8sIFdvcmxkIQ==\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void testGetFile() throws Exception {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);
        fileEntity.setTitle("Test File");
        fileEntity.setCreationDate(LocalDateTime.now());
        fileEntity.setDescription("Description");
        fileEntity.setFileData("SGVsbG8sIFdvcmxkIQ==".getBytes());

        when(fileService.getFile(1L)).thenReturn(Optional.of(fileEntity));

        mockMvc.perform(get("/api/files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test File"))
                .andExpect(jsonPath("$.description").value("Description"));
    }
}

