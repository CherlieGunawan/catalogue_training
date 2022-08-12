package id.co.nds.catalogue.models;

import org.hibernate.validator.constraints.Range;

import id.co.nds.catalogue.controllers.ControllerGroup.DeletingById;
import id.co.nds.catalogue.controllers.ControllerGroup.PostingNew;
import id.co.nds.catalogue.controllers.ControllerGroup.UpdatingById;

public class RecordModel {
    @Range(min = 0, message = "Actor id is 0 or more",
            groups = {PostingNew.class, UpdatingById.class, DeletingById.class})
    private Integer actorId;

    private String recStatus;

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }
}
