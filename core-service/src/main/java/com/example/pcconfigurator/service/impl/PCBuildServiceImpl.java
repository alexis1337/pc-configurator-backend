package com.example.pcconfigurator.service.impl;

import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.PCBuildItem;
import com.example.pcconfigurator.domain.User;
import com.example.pcconfigurator.repository.PCBuildRepository;
import com.example.pcconfigurator.service.CompatibilityService;
import com.example.pcconfigurator.service.PCBuildService;
import com.example.pcconfigurator.service.dto.CompatibilityIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PCBuildServiceImpl implements PCBuildService {

    private final PCBuildRepository buildRepository;
    private final CompatibilityService compatibilityService;

    @Override
    @Transactional(readOnly = true)
    public Page<PCBuild> getBuildsForUser(User user, Pageable pageable) {
        return buildRepository.findByUser(user, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public PCBuild getBuildForUser(User user, Long buildId) {
        return buildRepository.findByIdAndUser(buildId, user)
                .orElseThrow(() -> new NoSuchElementException("Build not found: " + buildId));
    }

    @Override
    public PCBuild createBuild(PCBuild build) {
        List<String> problems = new ArrayList<>();
        recalculateAndValidate(build, problems);

        if (!problems.isEmpty()) {
            throw new IllegalStateException("Build has compatibility issues: " + problems);
        }

        return buildRepository.save(build);
    }

    @Override
    public PCBuild updateBuild(User user, Long buildId, PCBuild updatedBuild) {
        PCBuild existing = getBuildForUser(user, buildId);

        existing.setName(updatedBuild.getName());
        existing.setTargetUsage(updatedBuild.getTargetUsage());

        existing.getItems().clear();
        for (PCBuildItem item : updatedBuild.getItems()) {
            item.setBuild(existing);
            existing.getItems().add(item);
        }

        List<String> problems = new ArrayList<>();
        recalculateAndValidate(existing, problems);
        if (!problems.isEmpty()) {
            throw new IllegalStateException("Build has compatibility issues: " + problems);
        }

        return buildRepository.save(existing);
    }

    @Override
    public void deleteBuild(User user, Long buildId) {
        PCBuild build = getBuildForUser(user, buildId);
        buildRepository.delete(build);
    }

    @Override
    public void recalculateAndValidate(PCBuild build, List<String> problemsOut) {
        BigDecimal total = BigDecimal.ZERO;
        for (PCBuildItem item : build.getItems()) {
            if (item.getComponent() != null && item.getComponent().getPrice() != null) {
                BigDecimal price = item.getComponent().getPrice();
                BigDecimal qty = BigDecimal.valueOf(Math.max(item.getQuantity(), 1));
                total = total.add(price.multiply(qty));
            }
        }
        build.setTotalPrice(total);

        List<CompatibilityIssue> issues = compatibilityService.checkBuildCompatibility(build);
        for (CompatibilityIssue issue : issues) {
            problemsOut.add(issue.getMessage());
        }
    }
}

