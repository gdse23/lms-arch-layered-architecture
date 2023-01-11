package lk.ijse.gdse.dao;

import lk.ijse.gdse.entity.Return;

import java.sql.SQLException;
import java.util.List;

public interface ReturnDAO {

    Return saveReturn(Return returnEntity) throws SQLException;

    void deleteReturnById(int issueId) throws SQLException;

    List<Return> findAllReturns() throws SQLException;

    Return findByIssueId(int issueId) throws SQLException;
}
