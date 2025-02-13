import java.sql.*;
public interface Refreshable {
    void refreshTable() throws Exception;
    void updateTable(ResultSet rs) throws Exception;
    void createFields() throws Exception;
    void searchFields() throws Exception;
    void updateFields() throws Exception;
    void deleteFields() throws Exception;
}
