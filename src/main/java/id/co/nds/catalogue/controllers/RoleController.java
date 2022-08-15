package id.co.nds.catalogue.controllers;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.controllers.ControllerGroup.DeletingById;
import id.co.nds.catalogue.controllers.ControllerGroup.PostingNew;
import id.co.nds.catalogue.controllers.ControllerGroup.UpdatingById;
import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.RoleModel;
import id.co.nds.catalogue.services.RoleService;

@RestController
@Validated
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> postRoleController(@Validated(PostingNew.class) @RequestBody RoleModel roleModel) throws ClientException {
        RoleEntity role = roleService.add(roleModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("New role is successfully added");
        response.setData(role);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getAllRoleController() {
        List<RoleEntity> role = roleService.findAll();

        ResponseModel response = new ResponseModel();
        response.setMsg("Request successfully");
        response.setData(role);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseModel> getRoleByIdController(
            @NotBlank(message = "Role ID is required")
            @Pattern(regexp = "^R[0-9]{4}$", message = "Role ID must start with R followed by four digits of number")
            @PathVariable String id) throws ClientException, NotFoundException {
        RoleEntity role = roleService.findById(id);

        ResponseModel response = new ResponseModel();
        response.setMsg("Request successfully");
        response.setData(role);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseModel> putRoleController(@Validated(UpdatingById.class) @RequestBody RoleModel roleModel) throws ClientException, NotFoundException {
        RoleEntity role = roleService.edit(roleModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("Role is successfully updated");
        response.setData(role);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseModel> deleteRoleController (@Validated(DeletingById.class) @RequestBody RoleModel roleModel) throws ClientException, NotFoundException {
        RoleEntity role = roleService.delete(roleModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("Role is successfully deleted");
        response.setData(role);
        return ResponseEntity.ok(response);
    }
}
