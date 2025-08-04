package com.example.Drive_BE.services;

import com.example.Drive_BE.entity.FileEntity;
import com.example.Drive_BE.repo.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceStorage {

    @Value("${file.upload-dir}")
    private String uploadDir;  //It injet the value of the property file.upoad-dir from the application.properties file into the uploaddir variable

    private final FileRepository fileRepository;


    public FileServiceStorage(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }



    public String saveFile(MultipartFile file, Long parentFolderId) throws IOException
    {
        //adding the feature to check weather the file exist in the system or not
        boolean exists = fileRepository.existsByNameAndParentFolderId(file.getOriginalFilename(), parentFolderId);

        if (exists) {
            throw new RuntimeException("A file with the same name already exists in this folder. Please rename the file.");
        }


        //old code
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath))
        {
            Files.createDirectories(uploadPath);
        }
        String fileName = file.getOriginalFilename();
        Path filePath=uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);



        //storing meta data in db
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);

        fileEntity.setPath(filePath.toString());
        fileEntity.setSize(file.getSize());
        fileEntity.setType("file");
        fileEntity.setParentFolderId(parentFolderId);
        fileEntity.setCreatedAt(LocalDateTime.now());

        fileRepository.save(fileEntity);

        return "file uploaded Successfully";

        /*
        place upload logic in try catch and handle exception if occur
         */
    }


    //to return list of all the file in the given folder
    public List<FileEntity>  getFilesInFolder(Long parentFolderId){

        if(parentFolderId == null) {
            return fileRepository.findAll()
                    .stream()
                    .filter(f -> f.getParentFolderId() == null)
                    .collect(Collectors.toList());
        }
        else
        {
            return fileRepository.findAll()
                    .stream()
                    .filter(f->parentFolderId.equals(f.getParentFolderId()))
                    .collect(Collectors.toList());
        }
    }


    //retrueve a file using file id
    public FileEntity getFileById(Long id)
    {
        return fileRepository.findById(id).orElseThrow(()->new RuntimeException("File not found"));
    }


    //to delete file by givem id
    public void deleteById(Long id){
        fileRepository.deleteById(id);
    }


}
