package kr.co.edu.menu.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface OrderDAO {
    // 엄. 잠만 뭐 JPA 이런거 써?아니.. // 얘가 디비랑 어떻게 연결돼?

    public int orderInsert(HashMap<String, Object> param); // 얘가 없네헐 맞네 cart를 order로 고쳐야된다 ㄱㄱㄱㄱ다해써

    public int orderUpdate(HashMap<String, Object> param);

    public int orderDelete(HashMap<String, Object> param);

    public List<HashMap<String, Object>> orderSelectList();
}
