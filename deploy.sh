kill $(cat /home/leekyusung/discovery-service/pid.file) &&
  rm /home/leekyusung/discovery-service/pid.file | true

nohup ./start.sh &
