package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Member;

import java.util.List;

public interface MemberDAO extends CrudDAO<Member,String> {

    public List<Member> searchMembersByText(String text);


}
