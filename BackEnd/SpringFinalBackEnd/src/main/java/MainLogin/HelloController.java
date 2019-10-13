package MainLogin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @RequestMapping("/user")
    public String showUserMsg()
    {
        return "User has logged in!!!";
        //returns a string message if successful

    }

    @RequestMapping("/admin")
    public String showAdminMsg()
    {
        return "Admin has logged in!!!";
      //returns a string message if successful
    }
}