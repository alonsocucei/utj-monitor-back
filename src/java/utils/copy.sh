#!/bin/sh

if [ $# -lt 1 ] 
then
   echo "usage: copy.sh Source.java, you have to be at src/java"
   echo " where Source.java is the assumed name of the Java source file"
   exit 1
fi

javac -cp ../../../tomee/lib/*:. -d ../../../tomee/webapps/utj#api $1.java
if [ $? -ne 0 ]
then
  echo "Error during compilation"  
fi

cd ../../../tomee/webapps/utj#api
ls -laR