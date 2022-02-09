all:
	../../bin/catalina.sh stop
	javac -cp ../../lib/servlet-api.jar:../../lib/antlr-4.9.3-complete.jar:../../lib/ST-4.3.1.jar WEB-INF/src/*/*.java -d WEB-INF/classes
	../../bin/catalina.sh run
