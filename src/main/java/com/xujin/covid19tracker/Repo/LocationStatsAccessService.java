package com.xujin.covid19tracker.Repo;

import com.xujin.covid19tracker.models.LocationStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationStatsAccessService  extends JpaRepository<LocationStats, Long> {

}
