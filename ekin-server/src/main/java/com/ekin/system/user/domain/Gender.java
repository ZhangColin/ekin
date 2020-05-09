package com.ekin.system.user.domain;

import com.ekin.system.operationlog.OperationLogType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;

/**
 * @author colin
 */
public enum Gender {
    /**
     * 未知
     */
    UNKNOWN(0, "未知"),
    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(2, "女");

    @JsonValue
    private final int value;
    private final String description;

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    Gender(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonCreator
    public static Gender getInstance(int value) {
        for (Gender gender : values()) {
            if (gender.value == value) {
                return gender;
            }
        }
        return null;
    }

    public static class Converter implements AttributeConverter<Gender, Integer> {
        @Override
        public Integer convertToDatabaseColumn(Gender gender) {
            return gender.getValue();
        }

        @Override
        public Gender convertToEntityAttribute(Integer dbData) {
            return Gender.getInstance(dbData);
        }
    }
}
