package com.ekin.system.operationlog.log;

import com.cartisan.CartisanContext;
import com.ekin.system.operationlog.OperationLogService;
import com.ekin.system.operationlog.OperationLogType;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * @author colin
 */
@Slf4j
public class LogTaskFactory {
    private LogTaskFactory() {

    }

    public static TimerTask loginLog(Long userId, String ip, String url,
                                     String method, String parameters, Boolean isSuccess) {
        return new TimerTask() {
            @Override
            public void run() {
                final OperationLogService operationLogService = CartisanContext.getBean(OperationLogService.class);
                operationLogService.createOperationLog(OperationLogType.LOGIN, userId, ip, url, method, parameters, isSuccess);
            }
        };
    }

    public static TimerTask logoutLog(Long userId, String ip, String url,
                                     String method, String parameters, Boolean isSuccess) {
        return new TimerTask() {
            @Override
            public void run() {
                final OperationLogService operationLogService = CartisanContext.getBean(OperationLogService.class);
                operationLogService.createOperationLog(OperationLogType.LOGOUT, userId, ip, url, method, parameters, isSuccess);
            }
        };
    }

    public static TimerTask registerLog(Long userId, String ip, String url,
                                     String method, String parameters, Boolean isSuccess) {
        return new TimerTask() {
            @Override
            public void run() {
                final OperationLogService operationLogService = CartisanContext.getBean(OperationLogService.class);
                operationLogService.createOperationLog(OperationLogType.REGISTER, userId, ip, url, method, parameters, isSuccess);
            }
        };
    }

    public static TimerTask businessOperateLog(Long userId, String ip, String url,
                                     String method, String parameters, Boolean isSuccess) {
        return new TimerTask() {
            @Override
            public void run() {
                final OperationLogService operationLogService = CartisanContext.getBean(OperationLogService.class);
                operationLogService.createOperationLog(OperationLogType.BUSINESS_OPERATE, userId, ip, url, method, parameters, isSuccess);
            }
        };
    }

}
