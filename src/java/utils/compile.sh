#!/bin/sh

find ../ -name "*.java" > ../sources.txt

javac -cp ../../../../tomee/lib/*:. -d ../../../../tomee/webapps/utj#api/WEB-INF/classes @sources.txt
if [ $? -ne 0 ]
then
  echo "Error during compilation"  
fi

cd ../../../../tomee/bin
shutdown.sh
startup.sh