package kr.co.edu.menu.web;

import kr.co.edu.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView main () {
        ModelAndView mav = new ModelAndView("jsonView");
        HashMap<String, Object> result = new HashMap<>();
        result.put("testNumber", 1);
        result.put("testString", "문자열");
        mav.addObject(result);
        return mav;
    }

    @RequestMapping(value = "/menuInsert.request", method = RequestMethod.POST)
    public ModelAndView menuInsert (@RequestBody HashMap<String, Object> param) {
        ModelAndView mav = new ModelAndView("jsonView");
        int insertCount = menuService.menuInsert(param);
        mav.addObject("result", insertCount);
        return mav;
    }

//    @RequestMapping(value = "/menuSelectList.request", method = RequestMethod.GET)
//    public ModelAndView menuSelectList (@RequestBody HashMap<String, Object> param) {
//        ModelAndView mav = new ModelAndView("jsonView");
//        List<HashMap<String, Object>> menuList = menuService.menuSelectList(param);
//        mav.addObject("result", menuList);
//        return mav;
//    }

    @RequestMapping(value = "/menuSelectList.request", method = RequestMethod.GET)
    public ModelAndView menuSelectList () {
        ModelAndView mav = new ModelAndView("jsonView");
        List<HashMap<String, Object>> menuList = menuService.menuSelectList();
        mav.addObject("result", menuList);
        return mav;
    }

    @RequestMapping(value = "/menuUpdate.request", method = RequestMethod.POST)
    public ModelAndView menuUpdate(@RequestBody HashMap<String, Object> param) {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", menuService.menuUpdate(param));
        return mav;
    }

    @RequestMapping(value = "/menuDelete.request", method = RequestMethod.POST)
    public ModelAndView menuDelete(@RequestBody HashMap<String, Object> param) {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", menuService.menuDelete(param));
        return mav;
    }

}
