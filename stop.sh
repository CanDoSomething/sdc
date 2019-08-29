#!/bin/bash

PROJECTNAME=sdc

pid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}' `
for pd in $pid
   do
        echo " kill pid $pd"
        kill -9 $pd
   done
if [ $pid ]; then

    echo "$PROJECTNAME is  running  and pid=$pid"

    kill -9 $pid

    if [[ $? -eq 0 ]];then

        echo "sucess to stop $PROJECTNAME "

    else

        echo "fail to stop $PROJECTNAME "

    fi

fi
