package com.boot.board230801.validator;

import com.boot.board230801.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

//@Autowired 와 연결
@Component
public class BoardValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return Board.class.equals(clazz);
  }

  @Override
  public void validate(Object obj, Errors errors) {
    Board b = (Board) obj;
    if (StringUtils.isEmpty(b.getContent())){
      errors.rejectValue("content", "key","내용을 입력하세요.");
    }
  }
}
