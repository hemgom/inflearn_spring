package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    // @ResponseBody 사용 -> view를 찾지않고 바로 String이 출력되어 버림 주의!
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");

        return "response/hello";
    }

    /**
     * 반환값을 void일 경우
     * HTTP 메시지 바디를 처리하는 파라미터가 없을 경우 `요청 URL`을 참고해 논리 뷰 이름으로 사용함
     * 하지만 명시성이 너무 떨어져 권장하지 않는 방법 -> v2 정도가 적당하다고 함 (강사 피셜)
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
