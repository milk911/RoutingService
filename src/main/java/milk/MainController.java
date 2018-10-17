package milk;


import milk.dao.PointsDAO;
import milk.dao.UserDAO;
import milk.models.Point;
import milk.models.RoutingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class MainController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PointsDAO pointsDAO;

    /*
    @GetMapping("/view/{name}")
    public String view(@PathVariable(name = "name") String name,
                       Model model) {
        model.addAttribute("msg", "Hello, " + name);
        return "index";
    }
    */

    @GetMapping("/main")
    public String main(@CookieValue(value = "UserName", required = false) Cookie UserName, @CookieValue(value = "UserID", required = false) Cookie UserID, Model model) throws SQLException{

        if (UserName == null || UserID == null)
            return "redirect:/login";

        List<Point> points =  pointsDAO.getAll(UserID.getValue());

        model.addAttribute("msg", "Hello, " + UserName.getValue() + " (" + UserID.getValue() + ")");
        model.addAttribute("points", points);
        model.addAttribute("newpoint", new Point());

        String pointsString = "";
        for(Point point:points) {
            if (pointsString.length() > 0)
                pointsString+= ",";
            pointsString = pointsString + point.getLat() +"," + point.getLon()+"," + point.getId();
        }

        model.addAttribute("pointsString", pointsString);
        return "main";
    }

    @PostMapping("/main")
    public String newpoint(@CookieValue(value = "UserID", required = false) Cookie UserID, @ModelAttribute Point point, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws SQLException{
        if ( UserID == null)
            return "redirect:/login";
        point.setUserID(UserID.getValue());
        pointsDAO.add(point);
        return "redirect:/main";
    }

    @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> ajaxTest() {
        Set<String> records = new HashSet<String>();
        records.add("Record #1");
        records.add("Record #2");

        return records;
    }

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    @ResponseBody
    public String route(@CookieValue(value = "UserID", required = false) Cookie UserID) {
        if ( UserID == null)
            return "redirect:/login";
        String answer = RoutingMachine.Routing(UserID.getValue(), pointsDAO);

        return answer;
    }


    /*
    @PostMapping("users/new")
    public String signUp(@RequestParam("name") String name,
                         @RequestParam("surname") String surname,
                         @RequestParam("email") String email) {
        users.add(new User(name, surname, email));

        return "redirect:/users";
    }
    */

    /*
    @PostMapping("users/new")
    public String signUp(@ModelAttribute @Valid User user, BindingResult result) throws SQLException{
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "/sign_up";
        }
        userDAO.add(user);
        //users.add(user);

        return "redirect:/users";
    }
    */

}
