package kr.co.edu.menu.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface OrderDAO {

    public int orderInsert(HashMap<String, Object> param);

    public int orderUpdate(HashMap<String, Object> param);

    public int orderDelete(HashMap<String, Object> param);

    public List<HashMap<String, Object>> orderSelectList();
}
