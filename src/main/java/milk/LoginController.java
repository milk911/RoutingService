package milk;

import milk.Util.Cookies;
import milk.Util.UserValidator;
import milk.dao.UserDAO;
import milk.models.LoginForm;
import milk.models.User;
import milk.models.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/index")
    public String view(@CookieValue(value = "UserName", required = false) Cookie UserName, Model model) {
        String Username = "-";
        if (UserName == null)
            return "redirect:/login";
        else
            return "redirect:/main";

    }


    @GetMapping("/raw")
    @ResponseBody
    public String raw() {
        return "raw data";
    }

    @GetMapping("/users")
    public String getUsers(Model model) throws SQLException {
        model.addAttribute("users", userDAO.getAll());
        return "/users";
    }

    @GetMapping("login")
    public String getLogin(@RequestParam(value = "errors", required = false, defaultValue = "") String errors, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("errors", errors);
        return "/login";
    }

    @PostMapping("login")
    public String Login(@ModelAttribute LoginForm loginForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws SQLException{

        if (result.hasErrors()) {
            return "redirect:/login?errors=true";
        }
        User user = loginForm.getUser();
        if (user == null)
            return "redirect:/login?errors=true";

        Cookies.setCookies(user, response);
        return "redirect:/main";
    }

    @GetMapping("sign_up")
    public String getSignUp(Model model) {
        model.addAttribute("userForm", new UserForm());

        return "/sign_up";
    }

    @PostMapping("sign_up")
    public String signUp(@ModelAttribute UserForm userForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws SQLException{

        userValidator.validate(userForm, result);

        if (result.hasErrors()) {
            return "/sign_up";
        }

        User user = userForm.getUser();
        userDAO.add(user);

        Cookies.setCookies(user, response);

        return "redirect:/main";
    }
}
