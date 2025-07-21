#!/bin/bash
. ./path.sh

run_container() {
  docker run -d --name=fms \
    -v ${APP_HOME}/logs:/home/fms/logs \
    -v /data/storage/fms:/data/storage \
    -v /usr/sbin/dmidecode:/usr/sbin/dmidecode \
    -v /dev/mem:/dev/mem \
    --restart always \
    --privileged=true \
    -p 6004:45678 \
    fms:@version@
}

count=$(docker ps -a | grep fms | grep -v mysql | wc -l)
if [[ $count -gt 0 ]]; then
  image=$(docker ps -a | grep fms | grep -v mysql | awk '{print $2}')
  if [[ "$image" = "fms:@version@" ]]; then
    status=$(docker ps -a --format "table {{.Names}}\t{{.Status}}" | grep fms | grep -v mysql | awk '{print $2}')
    if [[ $status = Up* ]]; then
      echo "fms container already started."
    else
      docker start fms
    fi
  else
    docker rm -f fms
    run_container
  fi
else
  run_container
fi
