package kr.co.edu.menu.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface MenuService {

    //메뉴 등록 기능
    public void menuInsert ();

    //메뉴 목록 조회 기능
    public List<HashMap<String, Object>> menuSelectList ();

    //메뉴 수정 기능
    public void menuUpdate ();

    //메뉴 삭제 기능
    public void menuDelete ();

}
