package site.metacoding.serverproject.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import site.metacoding.serverproject.domain.Hospital;
import site.metacoding.serverproject.domain.HospitalRepository;

@RequiredArgsConstructor
@Controller
public class DownloadController {

    private final HospitalRepository hospitalRepository;

    @GetMapping("/")
    public String main() {

        return "download";
    }

    @GetMapping("/download")
    public String download(Model model) {

        RestTemplate rt = new RestTemplate();
        Hospital[] response = rt.getForObject("http://3.38.254.72:5000/api/hospital?sidoCdNm=부산&sgguCdNm=부산사하구",
                Hospital[].class);

        List<Hospital> hosList = Arrays.asList(response);

        hospitalRepository.saveAll(hosList);

        // 1. URI로 다운로드
        // 2. DB에 saveAll() + model에 담기

        model.addAttribute("hospitals", hosList);

        // 3. 리턴

        return "list";
    }

}