#!/bin/sh

APP_JAR="short-link-0.2.jar"

pid=0
check_pid() {
    javaps=`ps -ef | grep short-link | grep -v monitor | grep -v grep | awk '{print $2}'`
    if [ -n "$javaps" ]; then
        pid=${javaps}
    else
        pid=0
    fi
}

start() {
    check_pid
    if [ ${pid} -ne 0 ]; then
        echo "======================================================"
        echo "===warn: short-link.jar already started! (pid=$pid)==="
        echo "======================================================"
    else
        echo -n "Starting short-link.jar now..."
        nohup java -Djava.io.tmpdir=tmp -jar ${APP_JAR} >./nohup.out 2>&1 &
        check_pid
        if [ ${pid} -ne 0 ]; then
            echo "Start short-link.jar (pid=$pid) [OK]"
        else
            echo "Start short-link.jar [Failed]"
        fi
    fi
}

stop() {
    check_pid
    if [ ${pid} -ne 0 ]; then
        echo -n "Stopping short-link.jar ...(pid=$pid) "
        kill -9 ${pid}
        #su - $RUNNING_USER -c "kill -9 $pid"
        if [ $? -eq 0 ]; then
            echo "Stop short-link.jar [OK]"
        else
            echo "Stop short-link.jar [Failed]"
        fi

        check_pid
        if [ ${pid} -ne 0 ]; then
            stop;
        fi
    else
        echo "==========================================="
        echo "===warn: short-link.jar is not running====="
        echo "==========================================="
    fi

}

case $1 in
start)
echo "Starting Application Now......"
start
;;
stop)
echo "Stopping Application Now......"
stop
;;
restart)
echo "Restarting Application Now......"
stop
sleep 1
start
;;
*)
esac

exit 0
