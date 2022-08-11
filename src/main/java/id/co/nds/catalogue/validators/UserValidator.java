package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class UserValidator {
    public void nullCheckUserId(Integer id) throws ClientException {
        if(id == null) {
            throw new ClientException("User id is required");
        }
    }

    public void notNullCheckUserId(Integer id) throws ClientException {
        if(id != null) {
            throw new ClientException("User id is auto generated, do not input id");
        }
    }

    public void nullCheckFullname(String fullname) throws ClientException {
        if(fullname == null) {
            throw new ClientException("User fullname is required");
        }
    }

    public void nullCheckRoleId(String roleId) throws ClientException {
        if(roleId == null) {
            throw new ClientException("User role id is required");
        }
    }

    public void nullCheckCallNumber(String callNumber) throws ClientException {
        if(callNumber == null) {
            throw new ClientException("User call number is required");
        }
    }

    public void nullCheckObject(Object e) throws NotFoundException {
        if(e == null) {
            throw new NotFoundException("User is not found");
        }
    }

    public void validateUserId(Integer id) throws ClientException {
        if(id <= 0) {
            throw new ClientException("User id input is invalid");
        }
    }

    public void validateFullname(String fullname) throws ClientException {
        if(fullname.trim().equalsIgnoreCase("")) {
            throw new ClientException("User fullname is required");
        }
    }

    public void validateRoleId(String roleId) throws ClientException {
        if(roleId.length() != 5 || !roleId.startsWith("R")) {
            throw new ClientException("User role id contains five digits, starts with 'R', then four digits numbers");
        }

        //Check if everything after "R" are numbers
        try {
            Integer.parseInt(roleId.substring(1));
        }
        catch(Exception e) {
            throw new ClientException("User role id contains five digits, starts with 'R', then four digits numbers");
        }
    }

    public void validateCallNumber(String callNumber) throws ClientException {
        if(((callNumber.length() < 10 || callNumber.length() > 13) && callNumber.startsWith("0")) //Check if there are 9 - 12 digits after using prefix "0"
                || ((callNumber.length() < 12 || callNumber.length() > 15) && callNumber.startsWith("+62")) //Check if there are 9 - 12 digits after using prefix "+62"
                || (!callNumber.startsWith("+62") && !callNumber.startsWith("0"))) { //Check if the prefixes are either "0" or "+62"
            throw new ClientException("User call number starts with '0' or '+62', followed by nine to twelve digits numbers");
        }

        //Check if everything after the first digit ("+" possible) are numbers
        try {
            Long.parseLong(callNumber.substring(1));
        }
        catch(Exception e) {
            throw new ClientException("User call number starts with '0' or '+62', followed by nine to twelve digits numbers");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("User with id = " + id + " has already been deleted.");
        }
    }
}
