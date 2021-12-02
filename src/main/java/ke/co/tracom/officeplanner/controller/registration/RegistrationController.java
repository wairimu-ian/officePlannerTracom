package ke.co.tracom.officeplanner.controller.registration;

import ke.co.tracom.officeplanner.entity.user.User;
import ke.co.tracom.officeplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    private final UserValidator validator;

    @Autowired
    public RegistrationController(UserValidator validator, UserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result){
        validator.validate(user, result);
        if (result.hasErrors()) {
            return "registration";
        }else {
            userRepository.save(user);
        }
        return "redirect:/login";
    }
}
