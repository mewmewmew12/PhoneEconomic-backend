package org.example.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test.entity.Image;
import org.example.test.respone.FileRespone;
import org.example.test.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/files")
@Slf4j
public class FileController {
    private final FileService fileService;
    // 1. Upload file ảnh của User

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/uploadUser/{userId}")
    public ResponseEntity<?> uploadFile(@PathVariable Integer userId,@ModelAttribute("file") MultipartFile file) {
        FileRespone fileRespone = fileService.uploadFile(userId,file);
        return new ResponseEntity<>(fileRespone, HttpStatus.CREATED);
    }
    @GetMapping("getImage/{userId}/{imageId}")
    public ResponseEntity<?> readfile(@PathVariable Integer userId, @PathVariable Integer imageId){
        Image image = fileService.readFileUser(userId , imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getData());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> readfile(@PathVariable Integer id){
        Image image = fileService.readfile(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getType())).body(image.getData());
    }
    // upload file product
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/uploadProduct/{productId}")
    public ResponseEntity<?> uploadFileProduct(@PathVariable Integer productId, @ModelAttribute("file") MultipartFile file) {
        FileRespone fileRespone = fileService.uploadFileProduct(productId, file);
        return new ResponseEntity<>(fileRespone,HttpStatus.CREATED);
    }


}
