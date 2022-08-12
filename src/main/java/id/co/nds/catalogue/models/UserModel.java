package id.co.nds.catalogue.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import id.co.nds.catalogue.controllers.ControllerGroup.DeletingById;
import id.co.nds.catalogue.controllers.ControllerGroup.GettingAllByCriteria;
import id.co.nds.catalogue.controllers.ControllerGroup.GettingById;
import id.co.nds.catalogue.controllers.ControllerGroup.PostingNew;
import id.co.nds.catalogue.controllers.ControllerGroup.UpdatingById;

public class UserModel extends RecordModel {
    @NotNull(message = "User ID is required",
            groups = {GettingById.class, UpdatingById.class, DeletingById.class})
    @Range(min = 1, message = "User ID starts from 1",
            groups = {GettingById.class, UpdatingById.class, DeletingById.class, GettingAllByCriteria.class})
    @Null(message = "User id is auto generated, do not input id",
            groups = {PostingNew.class})
    private Integer id;

    @NotBlank(message = "User fullname is required",
            groups = {PostingNew.class})
    @Pattern(regexp = "^[a-zA-Z0-9]{1,}$", message = "User fullname is required",
            groups = {PostingNew.class, UpdatingById.class, GettingAllByCriteria.class})
    private String fullname;

    @NotBlank(message = "User role ID is required",
            groups = {PostingNew.class})
    @Pattern(regexp = "^R[0-9]{4}$", message = "User role ID must start with R followed by four digits of number",
            groups = {PostingNew.class, UpdatingById.class, GettingAllByCriteria.class})
    private String roleId;

    @Pattern(regexp = "^(\\+62|0)[0-9]{8,12}$", message = "User call number must start with 0 or +62 followed by 8 to 12 digits of number",
            groups = {PostingNew.class, UpdatingById.class, GettingAllByCriteria.class})
    private String callNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }
}
