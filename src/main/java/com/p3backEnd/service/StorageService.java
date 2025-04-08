package com.p3backEnd.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	private final Path rootLocation;
	
	@Autowired
    public StorageService(StorageProperties properties) throws Exception{
        if(properties.getLocation().trim().length() == 0){
            throw new Exception("File upload location cannot be Empty");
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

	/**
	 * Store file
	 * 
	 * @param file
	 * @throws Exception 
	 */
    public void store(MultipartFile file) throws Exception{
        try {
            if(file.isEmpty()){
                throw new Exception("Empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())){
                throw new Exception("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e){
            throw new Exception("Failed to store file", e);
        }
    }
    
	/**
	 * Get file
	 * 
	 * @param file
	 * @throws Exception 
	 */
	public Resource loadAsResource(String filename) throws Exception {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new Exception(
					"Could not read file");
			}
		}
		catch (MalformedURLException e) {
			throw new Exception("Could not read file: " + filename, e);
		}
	}
	
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}
	
	public void init() throws Exception {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new Exception("Could not initialize storage", e);
		}
	}
	
	public Stream<Path> loadAll() throws Exception {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new Exception("Failed to read stored files", e);
		}
	}
	
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
}
