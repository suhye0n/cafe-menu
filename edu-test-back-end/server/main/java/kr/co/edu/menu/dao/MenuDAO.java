package kr.co.edu.menu.dao;

import org.apache.ibatis.annotations.Mapper;
import spring.config.RootContextConfig;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MenuDAO {

    public int menuInsert(HashMap<String, Object> param);

    public int menuUpdate(HashMap<String, Object> param);

    public int menuDelete(HashMap<String, Object> param);

//    public List<HashMap<String, Object>> menuSelectList(HashMap<String, Object> param);
public List<HashMap<String, Object>> menuSelectList();
}
