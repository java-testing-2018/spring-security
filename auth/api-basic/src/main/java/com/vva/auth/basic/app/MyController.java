package com.vva.auth.basic.app;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping("authorized")
    public List<String> get() {
        return ImmutableList.of(
                "MyController.get()",
                "one",
                "two",
                "three"
        );
    }



    @GetMapping("admin")
    public List<String> getSecurity() {
        return myService.getSecurityList();
    }

//    @RequestMapping("/logout")
//    public void exit(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, null, null);
//        try {
//            response.sendRedirect(request.getHeader("referer"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

