package id.co.nds.catalogue.controllers;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.controllers.ControllerGroup.DeletingById;
import id.co.nds.catalogue.controllers.ControllerGroup.PostingNew;
import id.co.nds.catalogue.controllers.ControllerGroup.UpdatingById;
import id.co.nds.catalogue.entities.CategoryEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.CategoryModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.CategoryService;

@RestController
@Validated
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> postCategoryController(@Validated(PostingNew.class) @RequestBody CategoryModel categoryModel) {
        try{
            CategoryEntity category = categoryService.add(categoryModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("New category is successfully added");
            response.setData(category);
            return ResponseEntity.ok(response);
        }
        catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getAllCategoryController() {
        try {
            List<CategoryEntity> category = categoryService.findAll();

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(category);
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseModel> getCategoryByIdController(
            @NotBlank(message = "Category ID is required")
            @Pattern(regexp = "^PC[0-9]{4}$", message = "Category ID must start with PC followed by four digits of number")
            @PathVariable String id) {
        try {
            CategoryEntity category = categoryService.findById(id);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(category);
            return ResponseEntity.ok(response);
        }
        catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseModel> putCategoryController(@Validated(UpdatingById.class) @RequestBody CategoryModel categoryModel) {
        try {
            CategoryEntity category = categoryService.edit(categoryModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Category is successfully updated");
            response.setData(category);
            return ResponseEntity.ok(response);
        }
        catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseModel> deleteCategoryController (@Validated(DeletingById.class) @RequestBody CategoryModel categoryModel) {
        try {
            CategoryEntity category = categoryService.delete(categoryModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Category is successfully deleted");
            response.setData(category);
            return ResponseEntity.ok(response);
        }
        catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
