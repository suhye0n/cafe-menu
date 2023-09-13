package kr.co.edu.menu.service.impl;

import kr.co.edu.menu.dao.OrderDAO;
import kr.co.edu.menu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Primary
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public int orderInsert (HashMap<String, Object> param) {
        int insertCount = orderDAO.orderInsert(param);
        return insertCount;
    }

    @Override
    public List<HashMap<String, Object>> orderSelectList() {
        return orderDAO.orderSelectList();
    }

    @Override
    public int orderUpdate(HashMap<String, Object> param) {
        int result = orderDAO.orderUpdate(param);
        return result;
    }

    @Override
    public int orderDelete(HashMap<String, Object> param) {
        int result = orderDAO.orderDelete(param);
        return result;
    }

}
