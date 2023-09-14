package kr.co.edu.menu.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

public interface MenuService {

    public int menuInsert (HashMap<String, Object> param);

    public List<HashMap<String, Object>> menuSelectList ();

    public int menuUpdate (HashMap<String, Object> param);

    public int menuDelete (HashMap<String, Object> param);

    public List<HashMap<String, Object>> menuSearch (HashMap<String, Object> param);

}
