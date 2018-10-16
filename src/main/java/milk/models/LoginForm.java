package milk.models;

import milk.dao.UserDAO;

import javax.validation.constraints.Email;


public class LoginForm {
    @Email
    private String email;

    private String password;

    private UserDAO userDAO = new UserDAO();

    public LoginForm() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return userDAO.getOneLogin(getEmail(), getPassword());
    }
}
