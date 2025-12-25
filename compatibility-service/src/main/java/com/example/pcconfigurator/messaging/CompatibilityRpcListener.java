package com.example.pcconfigurator.messaging;

import com.example.pcconfigurator.common.dto.CompatibilityRpcRequestDto;
import com.example.pcconfigurator.common.dto.CompatibilityIssueDto;
import com.example.pcconfigurator.config.RabbitConfig;
import com.example.pcconfigurator.service.CompatibilityService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompatibilityRpcListener { // RPC-обработчик

    private final CompatibilityService compatibilityService;

    public CompatibilityRpcListener(CompatibilityService compatibilityService) {
        this.compatibilityService = compatibilityService;
    }

    @RabbitListener(queues = RabbitConfig.COMPATIBILITY_QUEUE)
    public List<CompatibilityIssueDto> handle(CompatibilityRpcRequestDto request) {
        return compatibilityService.checkComponentsCompatibility(request.getComponents());
    }
}
