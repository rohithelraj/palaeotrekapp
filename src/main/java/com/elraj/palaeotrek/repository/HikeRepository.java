package com.elraj.palaeotrek.repository;

import com.elraj.palaeotrek.domain.Hike;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Hike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HikeRepository extends JpaRepository<Hike, Long> {}
