package com.pahanuchek.github.fileRepositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.pahanuchek.github.fileRepositories.model.FileEntity;
import com.pahanuchek.github.fileRepositories.repository.FileRepository;
import com.pahanuchek.github.fileRepositories.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class FileServiceTest {

	@Mock
	private FileRepository fileRepository;

	@InjectMocks
	private FileService fileService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveFile() {
		FileEntity fileEntity = new FileEntity();
		fileEntity.setId(1L);

		when(fileRepository.save(fileEntity)).thenReturn(fileEntity);

		Long id = fileService.saveFile(fileEntity);
		assertEquals(1L, id);
	}

	@Test
	public void testGetFile() {
		FileEntity fileEntity = new FileEntity();
		fileEntity.setId(1L);
		Optional<FileEntity> optionalFile = Optional.of(fileEntity);

		when(fileRepository.findById(1L)).thenReturn(optionalFile);

		Optional<FileEntity> result = fileService.getFile(1L);
		assertTrue(result.isPresent());
		assertEquals(1L, result.get().getId());
	}


}
