package kr.co.edu.menu.service;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

    public int orderInsert (HashMap<String, Object> param);

    public List<HashMap<String, Object>> orderSelectList ();

    public int orderUpdate (HashMap<String, Object> param);

    public int orderDelete (HashMap<String, Object> param);

    public int getLastOrderNumber();

}
