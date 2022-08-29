#!/usr/bin/env bash
cd ..
mvn clean package


echo 'Copy files...'

scp -i ~/.ssh/misa.pem \
    target/cherryparser-1.0-SNAPSHOT.jar \
    ubuntu@54.93.229.8:~/cherryparser/

echo 'Restart server...'

ssh  -tt -i ~/.ssh/misa.pem ubuntu@54.93.229.8 << EOF
pgrep java | xargs kill -9
nohup java -jar ~/cherryparser/cherryparser-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye Bye'


