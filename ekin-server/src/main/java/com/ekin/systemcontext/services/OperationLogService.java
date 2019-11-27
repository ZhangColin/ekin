package com.ekin.systemcontext.services;

import com.ekin.systemcontext.domains.OperationLog;
import com.ekin.systemcontext.domains.OperationLogType;
import com.ekin.systemcontext.repositories.OperationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author colin
 */
@Service
public class OperationLogService {
    private final OperationLogRepository repository;

    @Autowired
    public OperationLogService(OperationLogRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void createOperationLog(OperationLogType type, Long userId, String ip, String url,
                                   String method, String parameters, Boolean isSuccess) {
        repository.save(new OperationLog(type, userId, ip, url, method, parameters, isSuccess));
    }
}
