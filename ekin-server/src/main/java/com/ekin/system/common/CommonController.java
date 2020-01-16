package com.ekin.system.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.cartisan.responses.ResponseUtil.success;


/**
 * @author colin
 */
@RestController
@Slf4j
public class CommonController {
    private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @PostMapping("/import")
    public ResponseEntity<String> importData(MultipartFile file, HttpServletRequest request) throws IOException {
        String format = sdf.format(new Date());
        String realPath = request.getServletContext().getRealPath("/upload") + format;

        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));

        file.transferTo(new File(folder, newName));

        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/upload" + format + newName;

        log.info("import file generate url: "+ url);

        return success(url);
    }
}
