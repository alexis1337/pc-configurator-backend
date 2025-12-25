package com.example.pcconfigurator.service;

import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PCBuildService {

    Page<PCBuild> getBuildsForUser(User user, Pageable pageable);

    PCBuild getBuildForUser(User user, Long buildId);

    PCBuild createBuild(PCBuild build);

    PCBuild updateBuild(User user, Long buildId, PCBuild updatedBuild);

    void deleteBuild(User user, Long buildId);

    void recalculateAndValidate(PCBuild build, List<String> problemsOut);
}

