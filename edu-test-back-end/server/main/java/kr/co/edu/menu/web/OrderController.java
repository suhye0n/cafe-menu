package kr.co.edu.menu.web;

import kr.co.edu.menu.service.OrderService;
import kr.co.edu.menu.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping(value = "/orderInsert.request", method = RequestMethod.POST)
    public ModelAndView orderInsert (@RequestBody HashMap<String, Object> param) {
        ModelAndView mav = new ModelAndView("jsonView");
        int insertCount = orderService.orderInsert(param);
        mav.addObject("result", insertCount);
        return mav;
    }

    @RequestMapping(value = "/orderSelectList.request", method = RequestMethod.GET)
    public ModelAndView orderSelectList () {
        ModelAndView mav = new ModelAndView("jsonView");
        List<HashMap<String, Object>> orderList = orderService.orderSelectList();
        mav.addObject("result", orderList);
        return mav;
    }

    @RequestMapping(value = "/orderUpdate.request", method = RequestMethod.POST)
    public ModelAndView orderUpdate(@RequestBody HashMap<String, Object> param) {
        System.out.println(param);
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", orderService.orderUpdate(param));
        return mav;
    }

    @RequestMapping(value = "/orderDelete.request", method = RequestMethod.POST)
    public ModelAndView orderDelete(@RequestBody HashMap<String, Object> param) {
        System.out.println(param);
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", orderService.orderDelete(param));
        return mav;
    }

}
