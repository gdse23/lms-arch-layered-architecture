package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.QueryDAO;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.dto.IssueDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public IssueDTO findAllIssuesByMemberId(String memberId) {
        return null;
    }

    @Override
    public int findIssuedBooksCount(String memberId) {
        return 0;
    }

    @Override
    public int availableBooksCount(String isbn) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBUtil.executeQuery("SELECT(b.qty-COUNT(i.issue_id)+COUNT(R.date)) as availableCount FROM Book b LEFT JOIN issue i on b.isbn = i.isbn LEFT JOIN `Return` R on i.issue_id = R.issue_id WHERE b.isbn =? GROUP BY b.isbn", isbn);
        rst.next();
        return rst.getInt(1);

    }
}
