package id.co.nds.catalogue.controllers;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import id.co.nds.catalogue.controllers.ControllerGroup.DeletingById;
import id.co.nds.catalogue.controllers.ControllerGroup.GettingAllByCriteria;
// import id.co.nds.catalogue.controllers.ControllerGroup.GettingById;
import id.co.nds.catalogue.controllers.ControllerGroup.PostingNew;
import id.co.nds.catalogue.controllers.ControllerGroup.UpdatingById;
import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.entities.ProductInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ProductModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.ProductService;

@RestController
@Validated
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> postProductController(@Validated(PostingNew.class) @RequestBody ProductModel productModel) throws ClientException, InvalidFormatException {
        ProductEntity product = productService.add(productModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("New product is successfully added");
        response.setData(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getAllProductsController() {
        List<ProductEntity> products = productService.findAll();
        
        ResponseModel response = new ResponseModel();
        response.setMsg("Request successfully");
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/search")
    public ResponseEntity<ResponseModel> searchProductsController(@Validated(GettingAllByCriteria.class) @RequestBody ProductModel productModel) {
        List<ProductEntity> products = productService.findAllByCriteria(productModel);
        
        ResponseModel response = new ResponseModel();
        response.setMsg("Request successfully");
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseModel> getProductByIdController(
            @Range(min = 1, message = "Product ID starts from 1")
            @PathVariable Integer id) throws ClientException, NotFoundException {
        ProductEntity product = productService.findById(id);

        ResponseModel response = new ResponseModel();
        response.setMsg("Request successfully");
        response.setData(product);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseModel> putProductController(@Validated(UpdatingById.class) @RequestBody ProductModel productModel) throws ClientException, NotFoundException {
        ProductEntity product = productService.edit(productModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("Product is successfully updated");
        response.setData(product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseModel> deleteProductController(@Validated(DeletingById.class) @RequestBody ProductModel productModel) throws ClientException, NotFoundException {
        ProductEntity product = productService.delete(productModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("Product is successfully deleted");
        response.setData(product);
        return ResponseEntity.ok(response);
    }

    //NEW JOIN
    @GetMapping("/get/info")
    public ResponseEntity<ResponseModel> getAllByCategoryController(
            @NotBlank(message = "Product category ID is required")
            @Pattern(regexp = "^PC[0-9]{4}$", message = "Product category ID must start with PC followed by four digits of number")
            @RequestParam String categoryId) throws ClientException, NotFoundException {
        List<ProductInfoEntity> product = productService.findAllByCategory(categoryId);

        ResponseModel response = new ResponseModel();
        response.setMsg("Request successfully");
        response.setData(product);
        return ResponseEntity.ok(response);
    }
    //NEW JOIN

    //NEW JOIN 2
    @GetMapping("/get/category")
    public ResponseEntity<ResponseModel> getProductsByCategoryIdController(
            @NotBlank(message = "Product category ID is required")
            @Pattern(regexp = "^PC[0-9]{4}$", message = "Product category ID must start with PC followed by four digits of number")
            @RequestParam String categoryId) throws ClientException, NotFoundException {
        List<ProductEntity> product = productService.findProductsByCategoryId(categoryId);

        ResponseModel response = new ResponseModel();
        response.setMsg("Request successfully");
        response.setData(product);
        return ResponseEntity.ok(response);
    }
    //NEW JOIN 2
}
