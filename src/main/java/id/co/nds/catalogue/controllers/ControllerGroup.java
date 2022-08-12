package id.co.nds.catalogue.controllers;

public class ControllerGroup {
    public interface PostingNew {}

    public interface GettingAllByCriteria {}

    public interface GettingById {}

    public interface UpdatingById extends RequestMethodById {}

    public interface DeletingById extends RequestMethodById {}

    public interface RequestMethodById {}
}
