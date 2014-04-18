#!/bin/sh

CLASSPATH="."

# travel all jars and add to CLASSPATH
for jarfile in `ls lib/.`
do
    CLASSPATH="${CLASSPATH}:lib/$jarfile"
done

echo $CLASSPATH

JAVA_OPTS="-Xms1024m -Xmx2048m -Xss512K -XX:PermSize=256m -XX:MaxPermSize=256m"

java ${JAVA_OPTS} -cp ${CLASSPATH} com.aug3.logservice.thrift.log.start.LogServiceStart
