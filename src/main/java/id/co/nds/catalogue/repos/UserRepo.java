package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE rec_status = '"
            + GlobalConstant.REC_STATUS_ACTIVE
            + "' AND LOWER(call_number) = LOWER(:call_number)", nativeQuery = true)
    long countByCallNumber(@Param("call_number") String callNumber);

    @Query(value = "SELECT u.*, r.name AS role_name FROM ms_user AS u "
            + "JOIN ms_role AS r ON u.role_id = r.id "
            + "WHERE r.name = ?1", nativeQuery = true)
    List<UserEntity> findAllUserByRoleName(String roleName);

    @Query(value = "SELECT u.*, r.name AS role_name FROM ms_user AS u "
            + "JOIN ms_role AS r ON u.role_id = r.id "
            + "WHERE r.name = ?1 AND u.rec_status = '"
            + GlobalConstant.REC_STATUS_NON_ACTIVE
            + "'", nativeQuery = true)
    List<UserEntity> findAllUserByRoleNameWhereNoActive(String roleName);
}
