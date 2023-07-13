package com.ekin.system.common;

import com.cartisan.util.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
//import org.flywaydb.core.Flyway;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import static com.cartisan.response.ResponseUtil.success;
import static java.util.stream.Collectors.toList;


/**
 * @author colin
 */
@Api(tags = "系统管理：通用工具接口")
@RestController
@RequestMapping("/system")
@Validated
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

private final SnowflakeIdWorker snowflakeIdWorker;

    public CommonController(SnowflakeIdWorker snowflakeIdWorker) {
        this.snowflakeIdWorker = snowflakeIdWorker;
    }

    @ApiOperation(value = "Id生成")
    @GetMapping("/idGenerate")
    public ResponseEntity<?> idGenerate(@RequestParam(value = "count", defaultValue = "10") Integer count) {
        return success(Stream.generate(snowflakeIdWorker::nextId).limit(count).collect(toList()));
    }

//    @Autowired
//    private Flyway flyway;
//
//    @ApiOperation(value = "数据库重置")
//    @GetMapping("/dbReset")
//    public ResponseEntity<?> dbReset() {
//        flyway.clean();
//        flyway.migrate();
//        return success();
//    }
}
