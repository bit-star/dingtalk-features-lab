package com.kyanite.dingtalk.lab.repository;

import com.kyanite.dingtalk.lab.domain.DdUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DdUser entity.
 */
@Repository
public interface DdUserRepository extends JpaRepository<DdUser, Long> {
    @Query(
        value = "select distinct ddUser from DdUser ddUser left join fetch ddUser.privateData",
        countQuery = "select count(distinct ddUser) from DdUser ddUser"
    )
    Page<DdUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct ddUser from DdUser ddUser left join fetch ddUser.privateData")
    List<DdUser> findAllWithEagerRelationships();

    @Query("select ddUser from DdUser ddUser left join fetch ddUser.privateData where ddUser.id =:id")
    Optional<DdUser> findOneWithEagerRelationships(@Param("id") Long id);
}
