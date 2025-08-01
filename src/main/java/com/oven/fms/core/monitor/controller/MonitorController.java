package com.oven.fms.core.monitor.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.HostInfo;
import cn.hutool.system.JavaRuntimeInfo;
import cn.hutool.system.JvmInfo;
import cn.hutool.system.OsInfo;
import cn.hutool.system.RuntimeInfo;
import cn.hutool.system.SystemUtil;
import com.oven.basic.common.util.Result;
import com.oven.fms.common.constant.PermissionCode;
import com.oven.fms.common.enumerate.ResultCode;
import com.oven.fms.core.monitor.entity.SysMachineResult;
import com.oven.fms.framework.exception.MyException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 服务监控控制器
 *
 * @author Oven
 */
@ApiIgnore
@Controller
@RequestMapping("/monitor")
public class MonitorController {

    /**
     * 去到监控页面
     */
    @RequestMapping("/index")
    @RequiresPermissions(PermissionCode.MONITOR_MANAGER)
    public String index() {
        return "monitor/index";
    }

    /**
     * 监控接口
     */
    @ResponseBody
    @RequestMapping("/data")
    @RequiresPermissions(PermissionCode.MONITOR_MANAGER)
    public Result<Object> data() throws MyException {
        try {
            JvmInfo jvmInfo = SystemUtil.getJvmInfo();
            JavaRuntimeInfo javaRuntimeInfo = SystemUtil.getJavaRuntimeInfo();
            OsInfo osInfo = SystemUtil.getOsInfo();
            HostInfo hostInfo = SystemUtil.getHostInfo();
            RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();
            SysMachineResult sysMachineResult = new SysMachineResult();
            SysMachineResult.SysOsInfo sysOsInfo = new SysMachineResult.SysOsInfo();
            sysOsInfo.setOsName(osInfo.getName());
            sysOsInfo.setOsArch(osInfo.getArch());
            sysOsInfo.setOsVersion(osInfo.getVersion());
            sysOsInfo.setOsHostName(hostInfo.getName());
            sysOsInfo.setOsHostAddress(hostInfo.getAddress());
            sysMachineResult.setSysOsInfo(sysOsInfo);
            SysMachineResult.SysJavaInfo sysJavaInfo = new SysMachineResult.SysJavaInfo();
            sysJavaInfo.setJvmName(jvmInfo.getName());
            sysJavaInfo.setJvmVersion(jvmInfo.getVersion());
            sysJavaInfo.setJvmVendor(jvmInfo.getVendor());
            sysJavaInfo.setJavaName(javaRuntimeInfo.getName());
            sysJavaInfo.setJavaVersion(javaRuntimeInfo.getVersion());
            sysMachineResult.setSysJavaInfo(sysJavaInfo);
            SysMachineResult.SysJvmMemInfo sysJvmMemInfo = new SysMachineResult.SysJvmMemInfo();
            sysJvmMemInfo.setJvmMaxMemory(FileUtil.readableFileSize(runtimeInfo.getMaxMemory()));
            sysJvmMemInfo.setJvmUsableMemory(FileUtil.readableFileSize(runtimeInfo.getUsableMemory()));
            sysJvmMemInfo.setJvmTotalMemory(FileUtil.readableFileSize(runtimeInfo.getTotalMemory()));
            sysJvmMemInfo.setJvmFreeMemory(FileUtil.readableFileSize(runtimeInfo.getFreeMemory()));
            BigDecimal usedMemory = NumberUtil.sub(new BigDecimal(runtimeInfo.getTotalMemory()), new BigDecimal(runtimeInfo.getFreeMemory()));
            sysJvmMemInfo.setJvmUsedMemory(FileUtil.readableFileSize(usedMemory.longValue()));
            BigDecimal rate = NumberUtil.div(usedMemory, runtimeInfo.getTotalMemory());
            String usedRate = (new DecimalFormat("#.00")).format(NumberUtil.mul(rate, 100)) + "%";
            sysJvmMemInfo.setJvmMemoryUsedRate(usedRate);
            sysMachineResult.setSysJvmMemInfo(sysJvmMemInfo);
            return Result.success(sysMachineResult);
        } catch (Exception e) {
            throw MyException.build(ResultCode.SEARCH_PAGE_ERROR, "监控接口异常", e);
        }
    }

}
