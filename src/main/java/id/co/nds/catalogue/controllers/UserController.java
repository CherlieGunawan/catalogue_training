package id.co.nds.catalogue.controllers;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.controllers.ControllerGroup.DeletingById;
import id.co.nds.catalogue.controllers.ControllerGroup.GettingAllByCriteria;
import id.co.nds.catalogue.controllers.ControllerGroup.PostingNew;
import id.co.nds.catalogue.controllers.ControllerGroup.UpdatingById;
import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.entities.UserInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.services.RoleService;
import id.co.nds.catalogue.services.UserService;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> postUserController(@Validated(PostingNew.class) @RequestBody UserModel userModel) {
        try {
            UserEntity user = userService.add(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("New user is successfully added");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getAllUsersController() {
        try {
            List<UserEntity> user = userService.findAll();

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successful");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get/search")
    public ResponseEntity<ResponseModel> searchUsersController(@Validated(GettingAllByCriteria.class) @RequestBody UserModel userModel) {
        try {
            List<UserEntity> user = userService.findAllByCriteria(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successful");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    //NEW JOIN
    @GetMapping("/get/nonactive")
    public ResponseEntity<ResponseModel> findUsersByRoleNameWhereNoActive(
            @NotBlank(message = "Role name is required")
            @Pattern(regexp = "^[a-zA-Z0-9]{1,}$", message = "Role name is required")
            @RequestParam String roleName) {
        try {
            List<UserInfoEntity> user = userService.findUsersByRoleNameWhereNoActive(roleName);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successful");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get/info")
    public ResponseEntity<ResponseModel> findUsersByRoleName(
            @NotBlank(message = "Role name is required")
            @Pattern(regexp = "^[a-zA-Z0-9]{1,}$", message = "Role name is required")
            @RequestParam String roleName) {
        try {
            List<UserInfoEntity> user = userService.findUsersByRoleName(roleName);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successful");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }
    //NEW JOIN

    //NEW JOIN 2
    @GetMapping("/get/all/nonactive")
    public ResponseEntity<ResponseModel> findAllUserByRoleNameWhereNoActive(
            @NotBlank(message = "Role name is required")
            @Pattern(regexp = "^[a-zA-Z0-9]{1,}$", message = "Role name is required")
            @RequestParam String roleName) {
        try {
            List<UserEntity> user = userService.findAllUserByRoleNameWhereNoActive(roleName);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successful");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get/all/info")
    public ResponseEntity<ResponseModel> findAllUserByRoleName(
            @NotBlank(message = "Role name is required")
            @Pattern(regexp = "^[a-zA-Z0-9]{1,}$", message = "Role name is required")
            @RequestParam String roleName) {
        try {
            List<UserEntity> user = userService.findAllUserByRoleName(roleName);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successful");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }
    //NEW JOIN 2

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseModel> getUserByIdController(
            @NotNull(message = "User ID is required")
            @Range(min = 1, message = "User ID starts from 1")
            @PathVariable Integer id) {
        try {
            UserEntity user = userService.findById(id);

            ResponseModel response = new ResponseModel();
            response.setMsg("Request successful");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseModel> putUserController(@Validated(UpdatingById.class) @RequestBody UserModel userModel) {
        try {
            UserEntity user = userService.edit(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully updated");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseModel> deleteUserController(@Validated(DeletingById.class) @RequestBody UserModel userModel) {
        try {
            UserEntity user = userService.delete(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully deleted");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
        catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
