package com.p3backEnd.controller;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p3backEnd.service.StorageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/images")
public class FileController {

	private final StorageService storageService;
	
	public FileController(StorageService storageService) {
		this.storageService = storageService;
	}

	/**
	 * Get File by Name
	 * @param filename
	 * @return File
	 * @throws Exception 
	 */
	@Operation(summary = "Get file by fileName", description = "Retourne une image par son nom")
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {
        Resource file = storageService.loadAsResource(filename);
        if (file == null)
            return ResponseEntity.notFound().build();
        String contentType = Files.probeContentType(Path.of(filename));
        if (contentType == null)
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
