package kr.co.edu.menu.service.impl;

import kr.co.edu.menu.dao.GuestDAO;
import kr.co.edu.menu.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Primary
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestDAO guestDAO;

    @Override
    public int guestInsert (HashMap<String, Object> param) {
        int insertCount = guestDAO.guestInsert(param);
        return insertCount;
    }

    @Override
    public int guestUpdate(HashMap<String, Object> param) {
        int result = guestDAO.guestUpdate(param);
        return result;
    }

    @Override
    public int guestDelete(HashMap<String, Object> param) {
        int result = guestDAO.guestDelete(param);
        return result;
    }

    @Override
    public List<HashMap<String, Object>> guestSelectList() {
        return guestDAO.guestSelectList();
    }

}
