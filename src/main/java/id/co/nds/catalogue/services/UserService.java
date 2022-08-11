package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.entities.UserInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.repos.UserInfoRepo;
import id.co.nds.catalogue.repos.UserRepo;
import id.co.nds.catalogue.repos.specs.UserSpec;
import id.co.nds.catalogue.validators.RoleValidator;
import id.co.nds.catalogue.validators.UserValidator;

@Service
public class UserService implements Serializable {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    UserValidator userValidator = new UserValidator();
    RoleValidator roleValidator = new RoleValidator();

    public UserEntity add(UserModel userModel) throws ClientException {
        userValidator.notNullCheckUserId(userModel.getId());
        userValidator.nullCheckFullname(userModel.getFullname());
        userValidator.validateFullname(userModel.getFullname());
        userValidator.nullCheckRoleId(userModel.getRoleId());
        userValidator.validateRoleId(userModel.getRoleId());

        if(userModel.getCallNumber() != null) {
            userValidator.validateCallNumber(userModel.getCallNumber());
            
            Long count = userRepo.countByCallNumber(userModel.getCallNumber());
            if(count > 0) {
                throw new ClientException("Call number already exists");
            }
        }

        UserEntity user = new UserEntity();
        user.setFullname(userModel.getFullname());
        user.setRoleId(userModel.getRoleId());
        user.setCallNumber(userModel.getCallNumber());
        user.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        user.setCreatorId(
            userModel.getActorId() == null ? 0 : userModel.getActorId()
        );

        return userRepo.save(user);
    }

    public List<UserEntity> findAll() {
        return userRepo.findAll();
    }

    public List<UserEntity> findAllByCriteria(UserModel userModel) {
        UserSpec spec = new UserSpec(userModel);
        return userRepo.findAll(spec);
    }

    //NEW JOIN
    public List<UserInfoEntity> findAllNoActiveByRole(String roleName) throws ClientException, NotFoundException {
        roleValidator.nullCheckName(roleName);
        roleValidator.validateName(roleName);

        List<UserInfoEntity> user = userInfoRepo.findUsersByRoleNameWhereNoActive(roleName);
        userValidator.nullCheckObject(user);

        return user;
    }

    public List<UserInfoEntity> findAllByRole(String roleName) throws ClientException, NotFoundException {
        roleValidator.nullCheckName(roleName);
        roleValidator.validateName(roleName);

        List<Object[]> userObject = userInfoRepo.findUsersByRoleName(roleName);
        userValidator.nullCheckObject(userObject);

        List<UserInfoEntity> users = new ArrayList<UserInfoEntity>();
        for(int i = 0; i < userObject.size(); i++) {
            UserInfoEntity user = new UserInfoEntity();
            user.setId(Integer.parseInt(userObject.get(i)[0].toString()));
            user.setFullname(userObject.get(i)[1].toString());
            user.setRoleId(userObject.get(i)[2].toString());
            user.setRoleName(userObject.get(i)[3].toString());
            user.setCallNumber(userObject.get(i)[4] == null ? null : userObject.get(i)[4].toString());
            user.setCreatedDate(Timestamp.valueOf(userObject.get(i)[5].toString()));
            user.setUpdatedDate(userObject.get(i)[6] == null ? null : Timestamp.valueOf(userObject.get(i)[6].toString()));
            user.setDeletedDate(userObject.get(i)[7] == null ? null : Timestamp.valueOf(userObject.get(i)[7].toString()));
            user.setCreatorId(Integer.parseInt(userObject.get(i)[8].toString()));
            user.setUpdaterId(userObject.get(i)[9] == null ? null : Integer.parseInt(userObject.get(i)[9].toString()));
            user.setDeleterId(userObject.get(i)[10] == null ? null : Integer.parseInt(userObject.get(i)[10].toString()));
            user.setRecStatus(userObject.get(i)[11].toString());

            users.add(user);
        }

        return users;
    }
    //NEW JOIN

    public UserEntity findById(Integer id) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(id);
        userValidator.validateUserId(id);

        UserEntity user = userRepo.findById(id).orElse(null);
        userValidator.nullCheckObject(user);

        return user;
    }

    public UserEntity edit(UserModel userModel) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        if(!userRepo.existsById(userModel.getId())) {
            throw new NotFoundException("Cannot find user with id: " + userModel.getId());
        }

        UserEntity user = new UserEntity();
        user = findById(userModel.getId());

        if(userModel.getFullname() != null) {
            userValidator.validateFullname(userModel.getFullname());

            user.setFullname(userModel.getFullname());
        }

        if(userModel.getRoleId() != null) {
            userValidator.validateRoleId(userModel.getRoleId());

            user.setRoleId(userModel.getRoleId());
        }

        if(userModel.getCallNumber() != null) {
            userValidator.validateCallNumber(userModel.getCallNumber());

            Long count = userRepo.countByCallNumber(userModel.getCallNumber());
            if(count > 0) {
                throw new ClientException("Call namber already exists");
            }

            user.setCallNumber(userModel.getCallNumber());
        }

        user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        user.setUpdaterId(
            userModel.getActorId() == null ? 0 : userModel.getActorId()
        );

        return userRepo.save(user);
    }

    public UserEntity delete(UserModel userModel) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        if(!userRepo.existsById(userModel.getId())) {
            throw new NotFoundException("Cannot find user with id: " + userModel.getId());
        }

        UserEntity user = new UserEntity();
        user = findById(userModel.getId());

        if(user.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("User id (" + userModel.getId() + ") has already been deleted.");
        }

        user.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        user.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        user.setDeleterId(
            userModel.getActorId() == null ? 0 : userModel.getActorId()
        );

        return userRepo.save(user);
    }
}
