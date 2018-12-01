package DAO;

public interface HomeDAO {
    public boolean checkUserInDB(String password) throws Exception;
    public boolean checkPasswordExist() throws Exception;
    public void changePassword(String newPassword) throws Exception;
    public void checkDB(String dbName) throws Exception;

}
