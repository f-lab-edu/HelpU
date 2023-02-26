package com.flab.helpu.domain.hello;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 스프링에서 컨트롤러임을 나타내고 빈에 등록
@Controller
public class HelloController {

  // 요청을 처리하는 어노테이션
  @RequestMapping("/")
  public void printHelloWorld(HttpServletResponse response) throws IOException {
    response.getWriter().print("Hello World");
  }

}
