kill $(cat /home/leekyusung/service/discovery/pid.file) &&
  rm /home/leekyusung/service/discovery/pid.file | true

nohup ./start.sh &
