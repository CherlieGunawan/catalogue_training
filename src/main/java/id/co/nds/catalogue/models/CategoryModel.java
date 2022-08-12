package id.co.nds.catalogue.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import id.co.nds.catalogue.controllers.ControllerGroup.DeletingById;
import id.co.nds.catalogue.controllers.ControllerGroup.GettingById;
import id.co.nds.catalogue.controllers.ControllerGroup.PostingNew;
import id.co.nds.catalogue.controllers.ControllerGroup.UpdatingById;

public class CategoryModel extends RecordModel {
    @NotBlank(message = "Category ID is required",
            groups = {GettingById.class, UpdatingById.class, DeletingById.class})
    @Pattern(regexp = "^PC[0-9]{4}$", message = "Category ID must start with PC followed by four digits of number",
            groups = {GettingById.class, UpdatingById.class, DeletingById.class})
    @Null(message = "Category ID is auto generated, do not input ID",
            groups = {PostingNew.class})
    private String id;

    @NotBlank(message = "Category name is required",
            groups = {PostingNew.class})
    @Pattern(regexp = "^[a-zA-Z0-9]{1,}$", message = "Category name is required",
            groups = {PostingNew.class, UpdatingById.class})
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
