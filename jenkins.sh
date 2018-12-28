#!/bin/bash

PROJECTNAME=sdc

pid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}' `

if [ $pid ]; then

    echo "$PROJECTNAME is  running  and pid=$pid"

    kill -9 $pid

    if [[ $? -eq 0 ]];then

       echo "sucess to stop $PROJECTNAME "

    else

       echo "fail to stop $PROJECTNAME "

     fi

fi

oldpid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}' `

if [ $oldpid ]; then

    echo "$PROJECTNAME  is  running  and pid=$oldpid"

else

   echo "Ready to start $PROJECTNAME ...."

   cd /var/lib/jenkins/workspace/学生发展平台

   echo "切换到jar包路径---> 成功..."

   BUILD_ID=dontKillMe

   nohup java -jar target/sdc-0.0.1-SNAPSHOT.jar  >> catalina.out  2>&1 &

fi

newpid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}' `

if [ $newpid ]; then

    echo "$PROJECTNAME start  success  and pid=$newpid"

else

	echo "$PROJECTNAME start  failed "

fi