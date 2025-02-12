import java.sql.*;
public interface Refreshable {
    void refreshTable() throws Exception;
    void updateTable(ResultSet rs) throws Exception;
}
