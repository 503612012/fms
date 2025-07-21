#!/bin/bash
. ./path.sh
mysql_mount_dir="${APP_HOME}/data/mysql"
is_first_start=false
if [ ! -d $mysql_mount_dir ]; then
  is_first_start=true
else
  if [ "$(ls -A $mysql_mount_dir)" ]; then
    echo "$mysql_mount_dir is not Empty"
  else
    is_first_start=true
  fi
fi

while true; do
  read -s -p "请输入root用户密码(退出请输Y)：" passwd
  if [[ "$passwd" == "y" || "$passwd" == "Y" ]]; then
    exit
  fi

  if [[ ${#passwd} -lt 6 ]]; then
    echo $'\n密码长度至少6位！'
    continue
  fi

  if [[ "$passwd" != *[a-z]* ]]; then
    echo $'\n密码至少包含一个字母！'
    continue
  fi

  if [[ "$passwd" != *[0-9]* ]]; then
    echo $'\n密码至少包含一个数字！'
    continue
  fi
  break
done

echo -n "$passwd" | openssl rsautl -encrypt -pubin -inkey <(echo -e "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6h42Pq2dHuMOU8eZT2CjvMgY2eizvW61WApQqWYuZwZ3BGChFiUehy4vh2JpW8lEFyX8eigawuVVRn55zDtbs/74ctfs2tUnyEhLX+em3ug1wCTlV2Sm8bYiBgejkXlzvy6RKvVaYspczIi3+146Y5ltcQVQ15Z9Us1eg10OWSwIDAQAB\n-----END PUBLIC KEY-----") -out ./pwd

echo "mysql container starting..."
docker run -d -p 3309:3306 --name mysql-fms -u root -v ${APP_HOME}/data/backup:/home/backup -v ${mysql_mount_dir}:/var/lib/mysql -v /etc/localtime:/etc/localtime --restart=always -e MYSQL_ROOT_PASSWORD=${passwd} mysql:8 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --lower_case_table_names=1
echo "mysql container started"

if [ "$is_first_start" = false ]; then
  exit
fi

echo "database initialing..."
docker cp fms.sql mysql-fms:/home

step_length=1
total_wait_time=20

nc="\033[0m"
green="\e[32m"
pink='\E[1;35m'
yellow="\e[33m"

for ((i = 1; i <= total_wait_time; i++)); do
  percent=$(((i * 100) / total_wait_time))
  filled=$(((i * 20) / total_wait_time))
  remainder=$((20 - filled))

  bar=$(printf "%${filled}s" | sed 's/ /█/g')
  remain=$(printf "%${remainder}s" | sed 's/ / /g')

  printf "\r${green}init database:${pink}[%s%s]${yellow}%d%%${nc}" "$bar" "$remain" "$percent"

  sleep $step_length
done
printf "\n\033[0m"
echo "init database finished"

echo "begin to config database..."
docker exec -i mysql-fms bash <<EOP
mysql -uroot -p${passwd} <<EOF
set names utf8;
create database db_fms default character set utf8 collate utf8_general_ci;
use mysql;
create user fms identified by 'fms9876';
grant all on db_fms.* to 'fms'@'%';
flush privileges;
use db_fms;
source /home/fms.sql;
exit
EOF
exit
EOP
echo "config database finished"
rm -rf ./mysql.sh
rm -rf ./fms.sql