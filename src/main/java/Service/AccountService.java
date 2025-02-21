package Service;

import Model.Account;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import DAO.AccountDAO;

public class AccountService {
    
    //creates object
    AccountDAO accountDao;
    MessageDAO messageDao;

    //instiantiates object
    public AccountService(){
        this.accountDao = new AccountDAO();
        this.messageDao = new MessageDAO();
    }


    //adds new user
    public Account addAccount(Account account){

        //if username is not black & password is at least 4 characters
        if (!account.getUsername().isBlank() && account.getPassword().length() > 3)
            return accountDao.insertAccount(account);
        else {
            return null;
        }
    }

    public Account getAccount(Account account){
        
        //if username is not blacnk & password is not blank
        if (!account.getUsername().isBlank() && !account.getPassword().isBlank()){
            return accountDao.getAccount(account);
        } else {
            return null;
        }

            
    }

}
