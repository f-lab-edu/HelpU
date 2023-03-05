package com.flab.helpu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/** <strong>Annotation Note : </strong>
 * @SpringBootTest : 스프링부트를 기반으로 한 테스트를 실행하기 위해 클래스에 지정할 수 있는 주석
 */
@SpringBootTest
class HelpuApplicationTests {
	/** <strong>Annotation Note : </strong>
	 * @Test : 테스트 메소드임을 명시해주는 어노테이션
	 * 해당 어노테이션은 private, static 이 불가능하고 값을 반환하면 안된다
	 *
	 */
  @Test
  void contextLoads() {
  }
}
