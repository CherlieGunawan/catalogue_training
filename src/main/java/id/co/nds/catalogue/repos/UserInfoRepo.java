package id.co.nds.catalogue.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.UserInfoEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

@Repository
@Transactional
public interface UserInfoRepo extends JpaRepository<UserInfoEntity, String> {
    @Query(value = "SELECT u.*, r.name AS role_name FROM ms_user AS u "
            + "JOIN ms_role AS r ON u.role_id = r.id "
            + "WHERE r.name = ?1 AND u.rec_status = '"
            + GlobalConstant.REC_STATUS_NON_ACTIVE
            + "'", nativeQuery = true)
    List<UserInfoEntity> findUsersByRoleNameWhereNoActive(String roleName);

    List<Object[]> findUsersByRoleName(String roleName);
}
