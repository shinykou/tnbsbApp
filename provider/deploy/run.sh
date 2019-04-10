#!/usr/bin/env bash
# ------------------------------------
# default jvm args if you do not config in /jetty/boot.ini
# ------------------------------------
JVM_ARGS="-server -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Djava.io.tmpdir=/tmp -Djava.net.preferIPv6Addresses=false -Dorg.eclipse.jetty.server.Request.maxFormContentSize=-1"
JVM_GC="-XX:+DisableExplicitGC -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+PrintTenuringDistribution -XX:+UseConcMarkSweepGC -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps"
JVM_GC=$JVM_GC" -XX:CMSFullGCsBeforeCompaction=0 -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=80"
# jdk1.8中, 持久代被MetaSpace取代。PermSize参数废弃
JVM_HEAP="-XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError -XX:ReservedCodeCacheSize=128m
-XX:InitialCodeCacheSize=128m"
JVM_SIZE="-Xmx4g -Xms4g"


# ------------------------------------
# do not edit
# ------------------------------------


function init() {
    if [ -z "$LOG_PATH" ]; then
        LOG_PATH="/opt/logs/mobile/$MODULE"
    fi

    if [ -z "$WORK_PATH" ]; then
        WORK_PATH="/opt/work/gxn/$MODULE"
    fi

    unzip *.zip -d tmp && mv tmp/study.provider* output && rm -rf tmp
    mkdir -p $LOG_PATH

    #定时清理日志
    cleanpath="$WORK_PATH/clean.sh"
    echo "#!/bin/bash" > $cleanpath
    echo "find $LOG_PATH -mtime +1 -exec /bin/gzip {} \;" >> $cleanpath
    echo "find $LOG_PATH -mtime +3 -exec rm -fr {} \;" >> $cleanpath
    chmod +x $cleanpath
    (crontab -l|grep -v $cleanpath ; echo "58 05 * * * /bin/bash $cleanpath > /dev/null 2>&1" ) | crontab
}

function run() {
    EXEC="exec"
	cd output
	CLASSPATH=.:lib/*
	export CLASSPATH

    #----判断服务器java版本号---begin----
	exec  8>&2
    exec  2>jdk_verson_info.log
    java -version
    exec 2>&8 8>&-

    JDK_V="java"
    JDK7=$(cat jdk_verson_info.log | grep "1.7")
    if [ -z "$JDK7" ];then
      JDK_V="java"
    else
      JDK_V="/usr/local/jdk1.8.0_45/bin/java"
    fi
    rm -f jdk_verson_info.log
    #----判断服务器java版本号--end-----

	EXEC_JAVA="$EXEC $JDK_V $JVM_ARGS $JVM_SIZE $JVM_HEAP $JVM_JIT $JVM_GC"
	EXEC_JAVA=$EXEC_JAVA" -Xloggc:$LOG_PATH/$MODULE.gc.log -XX:ErrorFile=$LOG_PATH/$MODULE.vmerr.log -XX:HeapDumpPath=$LOG_PATH/$MODULE.heaperr.log"
	EXEC_JAVA=$EXEC_JAVA" -Ddruid.logType=log4j2"
	if [ "$JVM_JMX" != "" ]; then
		JVM_JMX_PORT=`expr $PORT '+' 10000`
		EXEC_JAVA=$EXEC_JAVA" -Dcom.sun.management.jmxremote.port=$JVM_JMX_PORT $JVM_JMX"
	fi

	if [ "$JVM_DEBUG" != "" ]; then
		JVM_DEBUG_PORT=`expr $PORT '+' 20000`
		EXEC_JAVA=$EXEC_JAVA" -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8412"
	fi

	if [ "$UID" = "0" ]; then
		ulimit -n 1024000
		umask 000
	else
		echo $EXEC_JAVA
	fi

    $EXEC_JAVA com.gxn.diamond.AppBootstrap >> $LOG_PATH/$MODULE.out.log 2>&1
}

# ------------------------------------
# actually work
# ------------------------------------
init
run