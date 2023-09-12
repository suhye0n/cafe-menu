package kr.co.edu.menu.service.impl;

import kr.co.edu.menu.dao.MenuDAO;
import kr.co.edu.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDAO menuDAO;

    @Override
    public void menuInsert () {
        HashMap<String, Object> data = new HashMap<>();
        menuDAO.menuInsert();
    }

    @Override
    public List<HashMap<String, Object>> menuSelectList() {
        return null;
    }

    @Override
    public void menuUpdate() {

    }

    @Override
    public void menuDelete() {

    }

}
