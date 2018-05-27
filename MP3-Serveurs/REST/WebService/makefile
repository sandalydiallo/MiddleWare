all: deploy

deploy: compress
	asadmin deploy --force AnalyseurRequete.war

compress: S2
	jar cvf AnalyseurRequete.war WEB-INF/*

S2: 
	javac serveur/pack/*.java -d WEB-INF/classes