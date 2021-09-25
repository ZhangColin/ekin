package com.ekin.system.uploadfile.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author colin
 */
@Data
@AllArgsConstructor
public class UploadFile {
    private String fileName;
    private String url;
}
