git clone https://7431831df8332ebd730fab69f7dbdc329659ed45@github.com/sirin05137/CSE364_Project.git
#git clone https://github.com/sirin05137/CSE364_Project.git
cd CSE364_Project
mvn install
mongod --fork --logpath /var/log/mongodb.log
./mvnw spring-boot:run

