package tian.life.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tian.life.community.dto.QuestionDTO;
import tian.life.community.mapper.QuestionMapper;
import tian.life.community.mapper.UserMapper;
import tian.life.community.model.Question;
import tian.life.community.model.User;
import tian.life.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     *用户访问首页时，将循环所有的cookie
     * 然后对比cookie的name，找到定义名为token的cookie
     * 找到这个特定的cookie后，再从数据库里面寻找这个数据
     * 如果它存在于数据库则表示该用户登陆过，将这个user赋值给session
     * 然后跳出循环返回已登录的首页
     */
    @GetMapping("/")
    public  String index(HttpServletRequest request,
                         Model model){
        //获取cookie
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            //增强for循环，遍历数组的每一个元素
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList = questionService.list();
        for (QuestionDTO questionDTO : questionList) {
            questionDTO.setDescription("c");
        }
        model.addAttribute("questions", questionList);
        return "index";
    }
}
