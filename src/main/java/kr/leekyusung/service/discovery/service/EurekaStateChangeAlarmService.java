package kr.leekyusung.service.discovery.service;

import kr.leekyusung.service.discovery.client.AlarmServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EurekaStateChangeAlarmService {
    private final AlarmServiceClient alarmServiceClient;

    private final Map<String, String> serverStatusMap = new HashMap<>();

    public void register(String appName, String instanceId, String hostName, int port) {
        if (!serverStatusMap.containsKey(instanceId)) {
            serverStatusMap.put(instanceId, hostName);
            alarmServiceClient.sendAlarm(
                    "DISCOVERY-SERVICE",
                    appName + "\n"
                            + "REGISTERED\n"
                            + instanceId + "\n"
                            + hostName + ":" + port);
        }
    }

    public void down(String appName, String serverId) {
        if (serverStatusMap.containsKey(serverId)) {
            serverStatusMap.remove(serverId);
            alarmServiceClient.sendAlarm(
                    "DISCOVERY-SERVICE",
                    appName + "\n"
                            + "DOWN\n"
                            + serverId);
        }
    }

    public void renew(String appName, String serverId, String hostName, int port) {
        if (serverStatusMap.containsKey(serverId)
                && !serverStatusMap.get(serverId).equals(hostName)) {
            serverStatusMap.put(serverId, hostName);
            alarmServiceClient.sendAlarm(
                    "DISCOVERY-SERVICE",
                    appName + "\n"
                            + "RENEW\n"
                            + serverId + "\n"
                            + hostName + ":" + port);
        }
    }
}
