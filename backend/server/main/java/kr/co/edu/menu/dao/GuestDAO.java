package kr.co.edu.menu.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface GuestDAO {

    public int guestInsert(HashMap<String, Object> param);

    public int guestUpdate(HashMap<String, Object> param);

    public int guestDelete(HashMap<String, Object> param);

    public List<HashMap<String, Object>> guestSelectList();
}
