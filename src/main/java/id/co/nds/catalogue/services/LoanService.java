package id.co.nds.catalogue.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import id.co.nds.catalogue.entities.LoanEntity;
import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.LoanModel;
import id.co.nds.catalogue.repos.LoanRepo;
import id.co.nds.catalogue.repos.UserRepo;
import id.co.nds.catalogue.validators.UserValidator;

@Service
public class LoanService {
    @Autowired
    private LoanRepo loanRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    private UserValidator userValidator = new UserValidator();

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public LoanEntity doLoan(LoanModel loanModel) throws ClientException, NotFoundException {

        userValidator.nullCheckUserId(loanModel.getUserId());
        if(!userRepo.existsById(loanModel.getUserId())) {
            throw new NotFoundException("Cannot find user with id: " + loanModel.getUserId());
        }

        UserEntity user = userService.findById(loanModel.getUserId());

        if(loanModel.getInterestRate() == null || loanModel.getLoanAmount() == null || loanModel.getLoanTerm() == null) {
            throw new ClientException("Interest rate, loan amount, or loan term cannot be null");
        }

        if(loanModel.getInterestRate().doubleValue() < 0 || loanModel.getInterestRate().doubleValue() >= 100) {
            throw new ClientException("Interest rate between 0 to 99.99%");
        }

        if(loanModel.getLoanAmount().doubleValue() < 0 || loanModel.getLoanAmount().doubleValue() >= 100000000) {
            throw new ClientException("Loan amount between 0 to 99,999,999.99");
        }

        if(loanModel.getCustomerName() == null || loanModel.getCustomerName().trim().equalsIgnoreCase("")) {
            throw new ClientException("Customer name is required");
        }

        LoanEntity loan = new LoanEntity();
        loan.setUserId(loanModel.getUserId());
        loan.setRoleId(user.getRoleId());
        loan.setLoanAmount(loanModel.getLoanAmount().doubleValue());
        loan.setLoanTerm(loanModel.getLoanTerm());
        loan.setInterestRate(loanModel.getInterestRate().doubleValue());
        loan.setTotalLoan(loanModel.getLoanAmount().doubleValue() + loanModel.getLoanAmount().doubleValue() * loanModel.getInterestRate().doubleValue() / 100);
        loan.setCustomerName(loanModel.getCustomerName());
        loan.setStartDate(new Timestamp(System.currentTimeMillis()));

        return loanRepo.save(loan);
    }
}
