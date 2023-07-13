package com.ekin.security;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author colin
 */
@Getter
@Setter
public class IgnoreUrlsProperties {
    private List<String> urls = new ArrayList<>();
}
