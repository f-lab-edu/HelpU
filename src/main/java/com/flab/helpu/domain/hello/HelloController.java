package com.flab.helpu.domain.hello;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** <strong> Annotation Note : </strong>
 * @Controller : @Component 의 특수한 역할을 하여 클래스 경로 스캔을 통해 클래스를 자동으로 감지할 수 있다
 * @RequestMapping 어노테이션을 감지하기도 한다
 */
@Controller
public class HelloController {

  /** <strong> Annotation Note : </strong>
   *  @RequestMapping : URL을 컨트롤러의 메소드와 매핑할 때 사용하는 어노테이션으로
   *  클래스나 메소드에서 사용할 수 있다
   */
  @RequestMapping(value = "/", method={RequestMethod.GET})
  public void printHelloWorld(HttpServletResponse response) throws IOException {
    response.getWriter().print("Hello World");
  }

}
