package milk.Util;

import milk.dao.UserDAO;
import milk.models.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    UserDAO userDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.equals(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        UserForm userForm = (UserForm) o;
        if (userDAO.getOne(userForm.getEmail())!=null) {
            errors.rejectValue("email", "", "This email is already in use!");
        }

        if (!userForm.getPassword1().equals(userForm.getPassword2())) {
            errors.rejectValue("password1", "", "Passwords are different!");
        }

    }
}
