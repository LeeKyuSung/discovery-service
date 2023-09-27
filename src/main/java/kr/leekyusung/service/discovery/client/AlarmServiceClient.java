package kr.leekyusung.service.discovery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "dummyName", url = "https://leekyusung.kr/alarm")
public interface AlarmServiceClient {
    @PostMapping("/aws/sns/publish")
    String sendAlarm(String body);
    @PostMapping("/aws/sns/publish/{subject}")
    String sendAlarm(@PathVariable("subject") String subject, String body);
}
