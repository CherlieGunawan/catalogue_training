package id.co.nds.catalogue.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.LoanEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.LoanModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService loanService;

    @PostMapping("/add")
    public ResponseEntity<ResponseModel> postLoan(@RequestBody LoanModel loanModel) throws ClientException, NotFoundException {
        LoanEntity loan = loanService.doLoan(loanModel);

        ResponseModel response = new ResponseModel();
        response.setMsg("New loan is successfully added");
        response.setData(loan);
        return ResponseEntity.ok(response);
    }
}
