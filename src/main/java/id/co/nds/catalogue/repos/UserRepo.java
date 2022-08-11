package id.co.nds.catalogue.repos;

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
}
