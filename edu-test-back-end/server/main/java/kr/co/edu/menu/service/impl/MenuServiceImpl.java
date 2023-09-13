package kr.co.edu.menu.service.impl;

import kr.co.edu.menu.dao.MenuDAO;
import kr.co.edu.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Primary
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDAO menuDAO;

    @Override
    public int menuInsert (HashMap<String, Object> param) {
        int insertCount = menuDAO.menuInsert(param);
        return insertCount;
    }

    @Override
    public List<HashMap<String, Object>> menuSelectList() {
        return menuDAO.menuSelectList();
    }

    @Override
    public int menuUpdate(HashMap<String, Object> param) {
        int result = menuDAO.menuUpdate(param);
        return result;
    }

    @Override
    public int menuDelete(HashMap<String, Object> param) {
        int result = menuDAO.menuDelete(param);
        return result;
    }

//    @Override
//    public List<HashMap<String, Object>> menuSelectList(HashMap<String, Object> param) {
//        return menuDAO.menuSelectList(param);
//    }

    @Override
    public List<HashMap<String, Object>> menuSearch (HashMap<String, Object> param) {
        return menuDAO.menuSearch(param);
    }

}
