package id.co.nds.catalogue.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.SaleEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.SaleModel;
import id.co.nds.catalogue.services.SaleService;

@RestController
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    SaleService saleService;

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> postSale(@RequestBody SaleModel saleModel) throws ClientException, NotFoundException {
        SaleEntity sale = saleService.doSale(saleModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("New sale is successfully added");
        response.setData(sale);
        return ResponseEntity.ok(response);
    }
}
