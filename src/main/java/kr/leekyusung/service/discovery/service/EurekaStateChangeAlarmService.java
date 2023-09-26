package kr.leekyusung.service.discovery.service;

import kr.leekyusung.service.discovery.client.AlarmServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EurekaStateChangeAlarmService {
    private final AlarmServiceClient alarmServiceClient;

    private final Map<String, String> serverStatusMap = new HashMap<>();

    public void register(String appName, String instanceId, String hostName, int port) {
        if (!serverStatusMap.containsKey(instanceId)) {
            serverStatusMap.put(instanceId, hostName);
            try {
                alarmServiceClient.sendAlarm(
                        "DISCOVERY-SERVICE",
                        appName + "\n"
                                + "REGISTERED\n"
                                + instanceId + "\n"
                                + hostName + ":" + port);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void down(String appName, String serverId) {
        if (serverStatusMap.containsKey(serverId)) {
            serverStatusMap.remove(serverId);
            try {
                alarmServiceClient.sendAlarm(
                        "DISCOVERY-SERVICE",
                        appName + "\n"
                                + "DOWN\n"
                                + serverId);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void renew(String appName, String serverId, String hostName, int port) {
        if (serverStatusMap.containsKey(serverId)
                && !serverStatusMap.get(serverId).equals(hostName)) {
            serverStatusMap.put(serverId, hostName);
            try {
                alarmServiceClient.sendAlarm(
                        "DISCOVERY-SERVICE",
                        appName + "\n"
                                + "RENEW\n"
                                + serverId + "\n"
                                + hostName + ":" + port);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
