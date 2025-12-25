package com.example.pcconfigurator.messaging;

import com.example.pcconfigurator.common.dto.CompatibilityRpcRequestDto;
import com.example.pcconfigurator.common.dto.CompatibilityIssueDto;
import com.example.pcconfigurator.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompatibilityRpcClient {

    private final RabbitTemplate rabbitTemplate;

    public CompatibilityRpcClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<CompatibilityIssueDto> check(CompatibilityRpcRequestDto request) {
        Object resp = rabbitTemplate.convertSendAndReceive(RabbitConfig.COMPATIBILITY_QUEUE, request);
        return (List<CompatibilityIssueDto>) resp;
    }
}
