package com.kyanite.dingtalk.lab.repository;

import com.kyanite.dingtalk.lab.domain.PublicData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PublicData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicDataRepository extends JpaRepository<PublicData, Long> {}
