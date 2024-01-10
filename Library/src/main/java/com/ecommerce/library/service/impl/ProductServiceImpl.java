package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageUpload imageUpload;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDto> allProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = transferData(products);
        return productDtos;
    }
    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImage());
            productDto.setCategory(product.getCategory());
            productDto.setActivited(product.isActivated());
            productDto.setDeleted(product.isDeleted());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();
        try {
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                if(imageUpload.uploadFile(imageProduct)){
                    System.out.println("Upload Success");
                };
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCostPrice(productDto.getCostPrice());
            product.setCategory(productDto.getCategory());
            product.setDeleted(false);
            product.setSalePrice(productDto.getSalePrice());
            product.setActivated(true);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct,ProductDto productDto) {
//        Product product = productRepository.findById(id);
        try {
            Product productUpdate = productRepository.getById(productDto.getId());
            if (imageProduct == null) {
                productUpdate.setImage(productUpdate.getImage());
            } else {
                if (imageUpload.checkExist(imageProduct) == false) {
//                    productUpdate.setImage(productUpdate.getImage());
                    imageUpload.uploadFile(imageProduct);
//                    System.out.println("Image Upload to folder");
                }
//                System.out.println("Image existed");
                productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
//                else {
//                    System.out.println("Image existed");
//                    imageUpload.uploadFile(imageProduct);
//                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
//                }
            }
            productUpdate.setCategory(productDto.getCategory());
//            productUpdate.seId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productUpdate.setSalePrice(productDto.getSalePrice());
            productUpdate.setCurrentQuantity(productDto.getCurrentQuantity());
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setImage(product.getImage());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setDeleted(product.isDeleted());
        productDto.setActivited(product.isActivated());
        return productDto;
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        productRepository.delete(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.setActivated(true);
        product.setDeleted(false);
        productRepository.save(product);
    }
// Customer
    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<ProductDto> products() {
        return transferData(productRepository.getAllProducts());
    }

    @Override
    public List<Product> listViewProducts() {
        return productRepository.listViewProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getRelatedProducts(Long categoryId) {
        return productRepository.getRelatedProducts(categoryId);
    }

    @Override
    public List<Product> getAllProductInCategory(Long categoryId) {
        return productRepository.getAllProductInCategory(categoryId);
    }

    @Override
    public List<Product> filterHighPrice() {
        return productRepository.filterHighPrice();
    }

    @Override
    public List<Product> filterLowPrice() {
        return productRepository.filterLowPrice();
    }

//    @Override
//    public Page<ProductDto> getAllProducts(int pageNo) {
//        Pageable pageable = PageRequest.of(pageNo, 6);
//        List<ProductDto> productDtoLists = this.allProduct();
//        Page<ProductDto> productDtoPage = toPage(productDtoLists, pageable);
//        return productDtoPage;
//    }
//    private Page toPage(List list, Pageable pageable) {
//        if (pageable.getOffset() >= list.size()) {
//            return Page.empty();
//        }
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
//                ? list.size()
//                : (int) (pageable.getOffset() + pageable.getPageSize());
//        List subList = list.subList(startIndex, endIndex);
//        return new PageImpl(subList, pageable, list.size());
//    }

}
