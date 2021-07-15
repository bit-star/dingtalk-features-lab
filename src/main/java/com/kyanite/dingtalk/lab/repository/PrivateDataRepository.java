package com.kyanite.dingtalk.lab.repository;

import com.kyanite.dingtalk.lab.domain.PrivateData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PrivateData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrivateDataRepository extends JpaRepository<PrivateData, Long> {}
