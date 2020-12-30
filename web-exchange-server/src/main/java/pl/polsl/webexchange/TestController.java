package pl.polsl.webexchange;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("api/test/{testId}")
    public String getTest(@PathVariable String testId) {
        return "Test Response for id: " + testId;
    }
}
