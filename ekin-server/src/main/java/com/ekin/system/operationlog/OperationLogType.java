package com.ekin.system.operationlog;

import javax.persistence.AttributeConverter;

/**
 * @author colin
 */
public enum OperationLogType {
    /**
     * 登录
     */
    LOGIN(1, "登录"),
    /**
     * 退出
     */
    LOGOUT(2, "退出"),
    /**
     * 注册
     */
    REGISTER(3, "注册"),
    /**
     * 业务操作
     */
    BUSINESS_OPERATE(4, "业务操作");

    private final int value;
    private final String description;

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    OperationLogType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static OperationLogType getInstance(int value) {
        for (OperationLogType operationLogType : values()) {
            if (operationLogType.value == value) {
                return operationLogType;
            }
        }
        return null;
    }

    public static class Converter implements AttributeConverter<OperationLogType, Integer> {
        @Override
        public Integer convertToDatabaseColumn(OperationLogType operationLogType) {
            return operationLogType.getValue();
        }

        @Override
        public OperationLogType convertToEntityAttribute(Integer dbData) {
            return OperationLogType.getInstance(dbData);
        }
    }
}
