package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.SuperDAO;
import lk.ijse.gdse.dto.IssueDTO;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {

    public IssueDTO findAllIssuesByMemberId(String memberId) ;

    public int findIssuedBooksCount(String memberId);

    public int availableBooksCount(String isbn) throws SQLException, ClassNotFoundException;


}
