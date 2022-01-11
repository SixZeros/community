package tian.life.community.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tian.life.community.dto.PaginationDTO;
import tian.life.community.dto.QuestionDTO;
import tian.life.community.mapper.QuestionMapper;
import tian.life.community.mapper.UserMapper;
import tian.life.community.model.Question;
import tian.life.community.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * 业务层代码，将dto里面的数据整合在一起
 * 将user的对象赋放进question里面
 * 然后将question的数据全都放到questiondto
 * 然后将数据返回上去
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        //size*(page - 1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);  //将question里面的数据都复制到questionDTO里
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
