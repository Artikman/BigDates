mvn clean package
if [ -d ./'Requests data' ]; then
rm -r ./'Requests data'
fi
java -jar target/Spark-1.0-SNAPSHOT.jar
start notepad++ ./'Requests data'/'part-00000'