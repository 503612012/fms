package com.oven.fms.core.system.controller;

import com.oven.basic.common.util.Result;
import com.oven.fms.framework.annotation.Anonymous;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@ApiIgnore
@RestController
public class HealthController {

    @Anonymous
    @GetMapping("/health")
    public Result<Object> health() {
        return Result.success();
    }

}
