package kr.leekyusung.service.discovery.listener;

import kr.leekyusung.service.discovery.service.EurekaStateChangeAlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EurekaStateChangeListener {
    private final EurekaStateChangeAlarmService eurekaStateChangeAlarmService;

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        // 인스턴스가 Eureka에서 내려갔을 때의 로직
        log.info("DOWN [{}][{}]",
                event.getAppName(),
                event.getServerId()
        );
        eurekaStateChangeAlarmService.down(
                event.getAppName(),
                event.getServerId()
        );
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        // 인스턴스가 Eureka에 등록될 때의 로직
        log.info("REGISTERED [{}][{}][{}][{}]",
                event.getInstanceInfo().getAppName(),
                event.getInstanceInfo().getInstanceId(),
                event.getInstanceInfo().getHostName(),
                event.getInstanceInfo().getPort()
        );
        eurekaStateChangeAlarmService.register(
                event.getInstanceInfo().getAppName(),
                event.getInstanceInfo().getInstanceId(),
                event.getInstanceInfo().getHostName(),
                event.getInstanceInfo().getPort()
        );
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        // 인스턴스 정보가 Eureka에서 갱신될 때의 로직
//        log.info("RENEWED [{}][{}][{}][{}]",
//                event.getAppName(),
//                event.getServerId(),
//                event.getInstanceInfo().getHostName(),
//                event.getInstanceInfo().getPort()
//        );
        eurekaStateChangeAlarmService.renew(
                event.getAppName(),
                event.getServerId(),
                event.getInstanceInfo().getHostName(),
                event.getInstanceInfo().getPort()
        );
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        // Eureka 레지스트리가 사용 가능한 상태가 되었을 때의 로직
        log.info("Eureka Registry is available");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        // Eureka 서버가 시작되었을 때의 로직
        log.info("Eureka Server started");
    }
}
