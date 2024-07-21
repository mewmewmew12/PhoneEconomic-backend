package org.example.test.service;

import org.example.test.entity.*;
import org.example.test.repository.ImageProductRepository;
import org.example.test.repository.ImageRepository;
import org.example.test.repository.ProductRepository;
import org.example.test.repository.UserRepository;
import org.example.test.respone.BadResquestException;
import org.example.test.respone.FileRespone;
import org.example.test.respone.NotFoundException;
import org.example.test.security.ImpliCurrent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageProductRepository imageProductRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImpliCurrent impliCurrent;

    public FileRespone uploadFile(Integer userId, MultipartFile file){
        validateFile(file);
        try {
            Optional<User> user = userRepository.findById(userId);
            Image image = Image.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .user(user.get())
                    .build();
            imageRepository.save(image);
            user.get().setAvatar("http://localhost:8888/api/v1/files/getImage/" + user.get().getId() + "/" + image.getId());
            userRepository.save(user.get());
            return new FileRespone("http://localhost:8888/api/v1/files/getImage/" + user.get().getId() + "/" + image.getId());
        } catch (IOException e){
            throw new BadResquestException("Quá trình upload file bị lỗi");
        }
    }
    public Image readFileUser(Integer userId, Integer imageId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    throw new BadResquestException("Không tìm thay User");
                }
        );
        return imageRepository.findByUser_IdAndId(userId,imageId);
    }
    public Image readfile(Integer id){
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw  new NotFoundException("KHông tìm ảnh");
        });
        return image;
    }

    public void deleteFile(Integer imageId){
        if(!imageRepository.findById(imageId).isPresent()){
            throw new BadResquestException("không tìm File");
        }
        imageRepository.deleteById(imageId);
    }

    public FileRespone uploadFileProduct(Integer productId, MultipartFile file){
        validateFile(file);
        try {
            Optional<Product> product = productRepository.findById(productId);
            ImageProduct imageProduct = ImageProduct.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .product(product.get())
                    .build();
            imageProductRepository.save(imageProduct);
            product.get().setThumbnail("http://localhost:8888/api/v1/files/product/" + product.get().getId() + "/" + imageProduct.getId());
            productRepository.save(product.get());
            User user = impliCurrent.getUser();
            return new FileRespone("http://localhost:8888/api/v1/files/product/" + product.get().getId() + "/" + imageProduct.getId());
        } catch (IOException e ){
            throw new BadResquestException("Có lỗi trong quá trình upload file");
        }
    }


    private void validateFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // Tên file không được trống
        if (fileName == null || fileName.isEmpty()) {
            throw new BadResquestException("Tên file không hợp lệ");
        }

        // Type file có nằm trong ds cho phép hay không
        // avatar.png, image.jpg => png, jpg
        String fileExtension = getFileExtension(fileName);
        if (!checkFileExtension(fileExtension)) {
            throw new BadResquestException("Type file không hợp lệ");
        }

        // Kích thước size có trong phạm vi cho phép không
        double fileSize = (double) (file.getSize() / 1_048_576);
        if (fileSize > 2) {
            throw new BadResquestException("Size file không được vượt quá 2MB");
        }
    }

    public String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return "";
        }
        return fileName.substring(lastIndex + 1);
    }
    public boolean checkFileExtension(String fileExtension) {
        List<String> fileExtensions = List.of("png", "jpg", "jpeg");
        return fileExtensions.contains(fileExtension);
    }
}
