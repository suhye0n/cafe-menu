package kr.co.edu.menu.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

public interface GuestService {

    public int guestInsert (HashMap<String, Object> param);

    public List<HashMap<String, Object>> guestSelectList ();

    public int guestUpdate (HashMap<String, Object> param);

    public int guestDelete (HashMap<String, Object> param);

}
