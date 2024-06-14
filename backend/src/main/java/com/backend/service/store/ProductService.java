package com.backend.service.store;

import com.backend.domain.store.Product;
import com.backend.domain.store.ProductType;
import com.backend.mapper.store.ImageMapper;
import com.backend.mapper.store.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ImageMapper imageMapper;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    String bucketName;

    @Value("${image.src.prefix}")
    String srcPrefix;


    public void add(Product product, MultipartFile[] files) throws Exception {

        mapper.add(product);

        if (files != null) {
            for (MultipartFile file : files) {
                imageMapper.add(product.getId(), file.getOriginalFilename());

                String key = STR."prj3/\{product.getId()}/\{file.getOriginalFilename()}";
                PutObjectRequest objectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build();

                s3Client.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            }
        }
    }


    public Map<String, Object> productList(String menuTypeSelect, Integer page) {

        Integer offset = (page - 1) * 12;

        Integer totalCount = mapper.totalCount(menuTypeSelect);

        Integer lastPageNumber = (totalCount - 1) / 10 + 1;
        Integer leftPageNumber = (page - 1) / 10 * 10 + 1;
        Integer rightPageNumber = leftPageNumber + 9;

        Map pageInfo = new HashMap();

        rightPageNumber = Math.min(rightPageNumber, lastPageNumber);

        Integer prevPageNumber = leftPageNumber - 1;
        Integer nextPageNumber = rightPageNumber + 1;

        if (prevPageNumber > 0) {
            pageInfo.put("prevPageNumber", prevPageNumber);
        }
        if (nextPageNumber <= lastPageNumber) {
            pageInfo.put("nextPageNumber", nextPageNumber);
        }
        pageInfo.put("currentPage", page);
        pageInfo.put("leftPageNumber", leftPageNumber);
        pageInfo.put("rightPageNumber", rightPageNumber);


        return Map.of("productList", mapper.productList(menuTypeSelect, offset),
                "pageInfo", pageInfo);
    }

    public void deleteProduct(Integer id) {

        String fileName = imageMapper.selectFileName(id);

        String dir = STR."/Users/igyeyeong/Desktop/Store/ProductImage/\{id}/";

        if (fileName != null) {

            File file = new File(dir + fileName);
            file.delete();

            File dirFile = new File(dir);
            if (dirFile.exists()) {
                dirFile.delete();
            }
        }

        imageMapper.deleteImage(id);

        mapper.deleteProduct(id);

    }

    public void updateProduct(Integer productId, Product product, String originalFileName, MultipartFile[] file) throws Exception {

        if (file == null) {
            mapper.updateProduct(product, productId);
        }
        if (file != null) {

            for (MultipartFile newFile : file) {

                String originalPath = STR."/Users/igyeyeong/Desktop/Store/ProductImage/\{productId}/\{originalFileName}";
                File originalFile = new File(originalPath);

                if (originalFile.exists()) {
                    originalFile.delete();
                }
                String path = STR."/Users/igyeyeong/Desktop/Store/ProductImage/\{productId}/\{newFile.getOriginalFilename()}";
                File modifyFile = new File(path);

                newFile.transferTo(modifyFile);

                mapper.updateProduct(product, productId);
                imageMapper.update(newFile.getOriginalFilename(), path, productId);
            }
        }
    }

    public Product info(Integer id) {

        return mapper.info(id);
    }

    public List<ProductType> typeList() {

        return mapper.typeList();
    }
}
