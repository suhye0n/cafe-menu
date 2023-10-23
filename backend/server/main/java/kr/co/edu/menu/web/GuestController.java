package kr.co.edu.menu.web;

import kr.co.edu.menu.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class GuestController {

    @Autowired
    private GuestService guestService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView main () {
        ModelAndView mav = new ModelAndView("jsonView");
        HashMap<String, Object> result = new HashMap<>();
        result.put("testNumber", 1);
        result.put("testString", "문자열");
        mav.addObject(result);
        return mav;
    }

    @RequestMapping(value = "/guestInsert.request", method = RequestMethod.POST)
    public ModelAndView menuInsert (@RequestBody HashMap<String, Object> param) {
        ModelAndView mav = new ModelAndView("jsonView");
        int insertCount = guestService.guestInsert(param);
        mav.addObject("result", insertCount);
        return mav;
    }

    @RequestMapping(value = "/guestUpdate.request", method = RequestMethod.POST)
    public ModelAndView menuUpdate(@RequestBody HashMap<String, Object> param) {
        System.out.println(param);
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", guestService.guestUpdate(param));
        return mav;
    }

    @RequestMapping(value = "/guestDelete.request", method = RequestMethod.POST)
    public ModelAndView menuDelete(@RequestBody HashMap<String, Object> param) {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", guestService.guestDelete(param));
        return mav;
    }

    @RequestMapping(value = "/guestSelectList.request", method = RequestMethod.GET)
    public ModelAndView menuSelectList () {
        ModelAndView mav = new ModelAndView("jsonView");
        List<HashMap<String, Object>> guestList = guestService.guestSelectList();
        mav.addObject("result", guestList);
        return mav;
    }

}
