package com.oven.controller.sys;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oven.controller.base.BaseController;

/**
 * 系统主页
 *
 * @author Oven
 */
@Controller
public class indexController extends BaseController {

    private final static Logger L = Logger.getLogger(indexController.class);

    /**
     * 去到系统主页面
     */
    @RequestMapping("/index")
    public ModelAndView index() {
        try {
            return new ModelAndView("/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

}
