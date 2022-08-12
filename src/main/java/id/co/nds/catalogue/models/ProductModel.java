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

public class ProductModel extends RecordModel {
    @NotNull(message = "Product ID is required",
            groups = {GettingById.class, UpdatingById.class, DeletingById.class})
    @Range(min = 1, message = "Product ID starts from 1",
            groups = {GettingById.class, UpdatingById.class, DeletingById.class, GettingAllByCriteria.class})
    @Null(message = "Product id is auto generated, do not input id",
            groups = {PostingNew.class})
    private Integer id;

    @NotBlank(message = "Product name is required",
            groups = {PostingNew.class})
    @Pattern(regexp = "^[a-zA-Z0-9]{1,}$", message = "Product name is required",
            groups = {PostingNew.class, UpdatingById.class, GettingAllByCriteria.class})
    private String name;

    @NotNull(message = "Product quantity is required",
            groups = {PostingNew.class})
    @Range(min = 0, message = "Product quantity is 0 or more",
            groups = {PostingNew.class, UpdatingById.class})
    private Integer quantity;

    @NotBlank(message = "Product category ID is required",
            groups = {PostingNew.class})
    @Pattern(regexp = "^PC[0-9]{4}$", message = "Product category ID must start with PC followed by four digits of number",
            groups = {PostingNew.class, UpdatingById.class})
    private String categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
