package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.SuperDAO;
import lk.ijse.gdse.dto.IssueDTO;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    public List<IssueDTO> findAllIssuesByMemberId(String memberId) throws SQLException, ClassNotFoundException;

    public int findIssuedBooksCount(String memberId) throws SQLException, ClassNotFoundException;

    public int availableBooksCount(String isbn) throws SQLException, ClassNotFoundException;


}
