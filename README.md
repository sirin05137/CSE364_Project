# CSE364 Group11

## Table of Contents
* [About the Project](#about-the-project)
  * [Built with](#built-with)
  * [Repository Structure](#repository-structure)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Milestone 1](#milestone-1)
  * [Explanation of the Algorithm](#explanation-of-the-algorithm)
  * [Running the Tests](#running-the-tests)
    * [Examples](#examples)
  * [Supported Inputs](#supported-inputs)
    * [Rules for the Inputs](#rules-for-the-inputs)
    * [Combination of Multiple Genres as an Input](#combination-of-multiple-genres-as-an-input)
  * [Error Codes](#error-codes)
    * [Examples for the Error Codes](#examples-for-the-error-codes)
  * [About JUnit Test](#about-junit-test)
  * [Contribution by Area](#contribution-by-area)
* [Milestone 2](#milestone-2)
  * [Explanation of the Algorithm](#explanation-of-the-algorithm-1)
    * [1) Bayesian Estimate](#1-bayesian-estimate)
    * [2) Priority rule for including similar users](#2-priority-rule-for-including-similar-users)
  * [Running the Tests](#running-the-tests-1)
    * [Examples](#examples-1)
  * [Supported Inputs](#supported-inputs-1)
  * [Error Codes](#error-codes-1)
    * [Examples for the Error Codes](#examples-for-the-error-codes-1)
  * [About JUnit Test](#about-junit-test-1)
  * [Contribution by Area](#contribution-by-area-1)
* Milestone 3 (Upcoming)
* Milestone 4 (Upcoming)
* [Team members](#team-members)
* [License & Acknowledgements](#license--acknowledgements)


## About the Project

This project is for the capstone project in SW Engineering (CSE364) at UNIST, developed by Yeongjun Kwak, Sanghun Lee and Yujin Lee.
This will be further inplemented as a movie recommendation system. **_Currently under the development···_**

### Built with
* Ubuntu 20.04
* [Oracle JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Apache Maven 3.6.3](https://maven.apache.org/download.cgi)
  * [Maven dependency tree](scripts/mvn_dependency_tree.txt)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) _(for the developers)_


### Repository Structure
```bash
CSE364_Project/
├── data/                             # Source data
├── scripts/                          # Documentation files
├── src/                              # Source files
│   ├── main/
│   │   └── java/
│   │       └── group11/
│   │           └── project.java
│   └── test/
│       ├── java/
│       │   └── group11/
│       │       └── projectTest.java
│       └── resources/   
├── target                            # Generated when mvn install
│   └── jacoco-report/
│   │       └── index.html            # JaCoCo Code Coverage Report                
├── pom.xml                           # Maven configuration
└── README.md
```

## Getting Started
> Instructions for setting up the project locally.

### Prerequisites
* [Docker](https://www.docker.com/) should be installed and ready to use.
* For the `git clone`, the user should be registered as a **Contributo** or **Collaborator** of this git project. _(Unless you have the OAuth access token.)_

### Installation
1. Download `dockerfile` and `run.sh` in the same directory.
2. (In the terminal) Build Docker Image and Container by running the following commands.
```ruby
$ docker build -t new_image_name /path/to/dockerfile
```
```ruby
$ docker run -it new_image_name
```
3. (In the Docker Container) Run ` . run.sh`
```ruby
root@containerID:~/project# . run.sh
```

---

## Milestone 1 
> Goal : Environment setup, data loading, data preprocessing

### Explanation of the Algorithm
On Milestone 1, the code calculates and returns **the average rating** from ratings data for specified **_occupation_** and **_genre_**. 

### Running the Test
Continued from [Installation](#installation) steps.
1. (In the Docker Container) Run Java command with **2 arguments** _(InputStr1 InputStr2)_, accordingly **Genre** and **Occupation** input.
```ruby
root@containerID:~/project# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project InputStr1 InputStr2
```

#### Examples
When valid inputs are passed, the output message will look like this :
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Adventure Educator
// Output
3.42
```
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Animation Doctor
// Output
3.68
```

### Supported Inputs
* **Genre** _(InputStr1)_ List

|Genre     |           |       | _total:18_ |
|----------|-----------|-------|--------|
|Action    |Crime      |Horror |Thriller|
|Adventure |Documentary|Musical|War     |
|Animation |Drama      |Mystery|Western |
|Children's|Fantasy    |Romance|        |
|Comedy    |Film-Noir  |Sci-Fi |        |

* **Occupation** _(InputStr2)_ List
  * The words in parentheses can be used as a **replacement** for the one outside.

|Occupation                                 |                      |                 |    _total : 21_     |
|-------------------------------------------|----------------------|-----------------|---------------------|
|Academic _(Educator)_                        |Executive _(Managerial)_|Programmer       |Technician _(Engineer)_| 
|Artist                                     |Farmer                |Retired          |Tradesman _(Craftsman)_| 
|Clerical _(Admin)_                           |Homemaker             |Sales _(Marketing)_|Unemployed           |
|Collegestudent _(College, Grad, Gradstudent)_|K-12student           |Scientist        |Writer               | 
|Customerservice                            |Lawyer                |Self-employed    |Other                |
|Doctor _(Healthcare)_                        |                      |                 |                     |   


#### Rules for the Inputs
* The spelling of the inputs **must be** correct.
  ```ruby
  Action    // Supported
  Accion    // X
  ```
* Both upper and lowercase letters are accepted.
  ```ruby
  Action    // Supported
  action    // Supported
  ACTION    // Supported
  aCtIOn    // Supported
  ```
* The spacings **must be** omitted.
  ```ruby
  Gradstudent   // Supported
  Grad student  // X
  ```
* The hyphens('-') must **not** be omitted.
  ```ruby
  Sci-Fi    // Supported
  SciFi     // X
  ```

#### Combination of Multiple Genres as an Input
* In order to get the average rating of the movies that fall into a combination of **multiple genres**, connect the words with **the pipeline('\\|')** and **enclosed by double quotation marks(" ")**.
  ```ruby
  Action|Adventure               // X
  "Action|Adventure|"              // X (Pipeline should be between words)
  "Action|Adventure"             // Supported
  "Action|Adventure|Animation"   // Supported
  ```
  * In case of `"Action|Adventure"`, the movies that fall into **both** Action **and** Adventure category are used for the calculation.
  * Combination of multiple occupations are not supported.

### Error Codes
Possible errors thrown by invalid user input.

##### **Table 1** Invalid input errors

| Error | Code | Message | Description | 
| :---: | :---: | --- | --- |
| `InputEmptyError` | 1 | No argument has passed. 2 arguments are required. (InputStr1 InputStr2) | Thrown when no input has entered.
| `InputNumError` | 2 | Only 1 input has passed. 2 arguments are required. | Thrown when only 1 input has entered.
| `InputNumError` | 3 | More than 2 arguments have passed. 2 arguments are required. | Thrown when more than 2 inputs have entered.
| `InputInvalidError` | 4 | Entered genre input is invalid | Thrown when the entered genre (combination) is invalid.
| `InputInvalidError` | 5 | Entered genre (_*inputString*_) doesn't exist. ( Invalid word : *input_string* ) | Thrown when the word in the entered genre (**OR** the word in genre combination) is invalid.

##### **Table 2** Invalid input warning

| Warning | Code | Message | Description | 
| :---: | :---: | --- | --- |
| `InputInvalidWarning` | 6 | Entered occupation doesn't exist. Rating by 'other' is shown instead. |  Thrown when the 2nd input is invalid.

##### **Table 3** No data exist error

| Error  | Code | Message | Description | 
| :---: | :---: | --- | --- |
| `NoDBError` | 7 | Rating data matching the input pair doesn't exist. | Thrown when there's no available Rating data for the genre-occupation pair **OR** When there's no Movie data matching the entered genre (combination).

* If the system is terminated with the error code listed above, the system exit status is `1`.

#### Examples for the Error Codes
##### Error code : 1~3

```ruby
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project 

InputEmptyError : No argument has passed. 2 arguments are required. (InputStr1 InputStr2)
Error code: 1

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Drama

InputNumError : Only 1 input has passed. 2 arguments are required.
Error code: 2

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Drama Scientist Scientist

InputNumError : More than 2 arguments have passed. 2 arguments are required.
Error code: 3
```

##### Error code: 4
```ruby
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project "|Adventure|Action" Scientist          

InputInvalidError : Entered genre input is invalid.
Error code: 4

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project "Adventure|Action|" Scientist 

InputInvalidError : Entered genre input is invalid.
Error code: 4
```

##### Error code: 5
```ruby
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project "Adventure|Action|Horrrror" Scientist

InputInvalidError : Entered genre (horrrror) doesn't exist.
Error code: 5
```
##### Error code: 6
```ruby
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Adventure Librarian                  

InputInvalidWarning : Entered occupation doesn't exist. Rating by 'other' is shown instead.

The rating of adventure rated by other : 3.43
```

##### Error code: 7
```ruby
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project "War|Crime" Academic  

NoDBError : Rating data matching the input pair doesn't exist.
Error code: 7

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project "Action|Animation|Children's|Sci-Fi|Thriller|War" retired

NoDBError : Rating data matching the input pair doesn't exist.
Error code: 7
```
* `"War|Crime" Academic` : Example for No Movie data matching the entered genre (combination).
* `"Action|Animation|Children's|Sci-Fi|Thriller|War" retired` : Example for No available Rating data for the genre-occupation input pair.

### About Junit Test
The Junit test (and regarding csv test resources) for **Milestone 1** has been moved to `scripts/`.
For more information, please refer to [this issue #20](https://github.com/sirin05137/CSE364_Project/issues/20)

### Contribution by Area
| Area | Contribution |
| :--- | :--- |
Setting up a Git Repository | Yeongjun Kwak
Environment Setup | **dockerfile, pom.xml** <br> Yujin Lee
Java Implementation | **Models and Data Structures** <br> 👑 Yeongjun Kwak <br> **Exception Handling** <br> 👑 Sanghun Lee, Yeongjun Kwak <br>**Unit Test Building** <br> Yujin Lee <br> **Final Reviewer** <br> Sanghun Lee, Yeongjun Kwak
Documentation | **README.md** <br> 👑 Yujin Lee, Sanghun Lee

<br>

---

## Milestone 2
> Goal : Implement Top 10 Movies Recommendation Features

### Explanation of the Algorithm
On Milestone 2, the code returns **the recommendation of Top 10 movies** for specified **_gender, age, occupation_** or **_genre(s)_**.
First, to recommend **'relevant'** movies, the code makes use of **_1) Bayesian Estimate_**, which is used to calculate Top 250 movies by IMDB as well, when calculating and comparing the ratings of movies.
Also, to set the **'similar'** users (in case there aren't enough ratings that match gender, age and occupation), we have set **_2) Priority rule for including similar users_**.

#### 1) Bayesian Estimate
Bayesian Estimate is an estimator that can help minimizing the risk of including  that minimizes the posterior expected value of a loss function.

By making use of Bayesian Estimate, the algorithm calculates Weighted Rating (`W`) and arranges movies with `W`.
그 후 Weighted rating에 따라 내림차순으로 List를 sort 하고 상위 10개의 영화만 출력한다.

In this way, the movies with very few ratings or below-average ratings will have comparably light weight.
The original reference for Baysian Estimate can be found [here](https://www.fxsolver.com/browse/formulas/Bayes+estimator+-+Internet+Movie+Database+%28IMDB%29).

`W = (vR+mC)/(v+m)`
> 공식 사진으로 대체하기 

| Variables | Explanation |
| :---: | :--- |
W | Weighted rating
v | Total number of ratings **for the movie** = (votes)
m | Minimum number of ratings required to be listed in the Top 10
R | Average rating **for the movie** as a number from 0 to 5 (mean)
C | Average rating across **all the movies**

* `m` : Minimum Number of Ratings 
  * The value of `m` is obtained by the `Percentile` function. The function returns the number of votes of the movie that corresponds to (1-`p`)*100 % .
  * Currenly the `p` is set to `0.8`.
    i.e. If there are 100 movies with average ratings, the function returns **the number of ratings** of 20th-highest movie.

* `R` : Average rating across **all the movies**
  * The value of `R` is obtained by the `total_average_rating` function.
  

#### 2) Priority rule for including similar users

The **'similar'** users refer to the users that may be used to secure enough number of movies (when there aren't enough ratings for specified user data).

> 벤다이어그램 사진 옮겨 넣기

When there are less than `100` movies, until the number of movies reaches `100`, the algorithm gradually includes 'similar' users with 
1. Same **Occupation** and **Gender** with inputs
   * This refers to the part `A` in the diagram.
2. Same **Occupation** and **Age range** with inputs
    * This refers to the part `B` in the diagram.
3. Same **Gender** and **Age range** with inputs
    * This refers to the part `C` in the diagram.

<br>

### Running the Test
Continued from [Installation](#installation) steps.
1. (In the Docker Container) Run Java command with **3 or 4 arguments**.
   The Arguments(_InputStr1 InputStr2 InputStr3 InputStr4_) are accordingly **Gender, Age, Occupation, _(and Genre)_**.
```ruby
root@containerID:~/project# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 InputStr1 InputStr2 InputStr3 InputStr4
```

#### Examples

When valid inputs are passed, the output message will look like this :

##### Testing with 3 inputs
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 “” “” “”
// Output
Bamboozled (2000) (http://www.imdb.com/title/tt0215545)
Bootmen (2000) (http://www.imdb.com/title/ttXXXXXXX)
Digimon: The Movie (2000) (http://www.imdb.com/title/ttXXXXXXX)
Get Carter (2000) (http://www.imdb.com/title/ttXXXXXXX)
Movie 5 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 6 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 7 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 8 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 9 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 10 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
```
##### Testing with 4 inputs
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 InputStr1 InputStr2 InputStr3 InputStr4
// Output
Bamboozled (2000) (http://www.imdb.com/title/tt0215545)
Bootmen (2000) (http://www.imdb.com/title/ttXXXXXXX)
Digimon: The Movie (2000) (http://www.imdb.com/title/ttXXXXXXX)
Get Carter (2000) (http://www.imdb.com/title/ttXXXXXXX)
Movie 5 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 6 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 7 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 8 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 9 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
Movie 10 (XXXX) (http://www.imdb.com/title/ttXXXXXXX)
```
> 예시 맞게 수정하

#### Supported Inputs
InputStr1 | InputStr2 | InputStr3 | _(InputStr4)_
| :---: | :---: | :---: | :---: |
Gender | Age | Occupation | _(Genre)_

* **Gender**
  * Must be **one letter** and is not case-sensitive.
  i.e. one of those ; **"`F`", "`f`", "`M`", "`m`"**.
  * **Can be left empty** when replaced by **paired double quotation marks**.
  i.e. `""`
    
* **Age**
  * Must be an **integer value** bigger than 0. 
    * e.g. 35 (Supported)
      -23 (X)
      Thirty-Five (X)
  * **Can be left empty** when replaced by **paired double quotation marks**.
      i.e. `""`
* **Occupation**
  * For Occupation, [the same rules from Milestone1](#supported-inputs) are applied here as well.
  * **Can be left empty** when replaced by **paired double quotation marks**.
      i.e. `""`
* **Genre** (When testing with 4 inputs)
  * For Genre, [the same rules from Milestone1](#supported-inputs) are applied here as well.
  * For the combination of genre inputs, the formatting rules from Milestone 1 [(Combination of multiple genres as an input)](#combination-of-multiple-genres-as-an-input) applies here as well. <br> However, the pipeline(`|`) here is uesd to link **OR** conditions, not **AND**.
    * e.g. `Adventure|Animation` includes all the movies that are categorized as Adventure **OR** Animation to candidates for Top 10 movies.
  * **Cannot be left empty**.
    i.e. `""`

##### Testing with 3 inputs
```ruby
// Examples of Supported Inputs
“F” “25” “Gradstudent”
“M” “30” “Athletes”
“F” “” “retired”
“” “15” “”
“” “” “”
// Examples of Wrong Inputs
"Female" "25" "Gradstudent" // Either "F" or "f" should be used.
"25" "Gradstudent" // Empty part must be explicitly specified by ""
"F" "-23" "Gradstudent" // Age must be an integer value.
"F" "Twenty-three" "Gradstudent" // Age must be an integer value.
"F" "25" "K12student" // The hyphens must not be omitted (K-12student). Please refer to the rule on Milestone 1.
```

##### Testing with 4 inputs
```ruby
“F” “25” “Grad student” “Action|Comedy”
“M” “30” “Athletes” “Children’s”
“F” “” “retired” “Animation|Drama|Fantasy”
“” “15” “” “Sci-Fi”
“” “” “” “Romance|Comedy”
```
<br>

### Error Codes
Possible errors thrown by invalid input.
> 자바 최종 파일에 맞게 수정해야

##### **Table 1** Invalid input errors

| Error | Code | Message | Description | 
| :---: | :---: | --- | --- |
| `InputEmptyError` | 1 | No argument has passed. 2 arguments are required. (InputStr1 InputStr2) | Thrown when no input has entered.
| `InputNumError` | 2 | Only 1 input has passed. 2 arguments are required. | Thrown when only 1 input has entered.
| `InputNumError` | 3 | More than 2 arguments have passed. 2 arguments are required. | Thrown when more than 2 inputs have entered.
| `InputInvalidError` | 4 | Entered genre input is invalid | Thrown when the entered genre (combination) is invalid.
| `InputInvalidError` | 5 | Entered genre (_*inputString*_) doesn't exist. ( Invalid word : *input_string* ) | Thrown when the word in the entered genre (**OR** the word in genre combination) is invalid.

##### **Table 2** Invalid input warning

| Warning | Code | Message | Description | 
| :---: | :---: | --- | --- |
| `InputInvalidWarning` | 6 | Entered occupation doesn't exist. Rating by 'other' is shown instead. |  Thrown when the 2nd input is invalid.

##### **Table 3** No data exist error

| Error  | Code | Message | Description | 
| :---: | :---: | --- | --- |
| `NoDBError` | 7 | Rating data matching the input pair doesn't exist. | Thrown when there's no available Rating data for the genre-occupation pair **OR** When there's no Movie data matching the entered genre (combination).

* If the system is terminated with the error code listed above, the system exit status is `1`.

#### Examples for the Error Codes
##### Error code : 1~3

```ruby
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project 

InputEmptyError : No argument has passed. 2 arguments are required. (InputStr1 InputStr2)
Error code: 1

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Drama

InputNumError : Only 1 input has passed. 2 arguments are required.
Error code: 2

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Drama Scientist Scientist

InputNumError : More than 2 arguments have passed. 2 arguments are required.
Error code: 3
```

<br>

### Contribution by Area
| Area | Contribution |
| :--- | :--- |
Java Implementation | **Models and Data Structures** <br> 👑 Yeongjun Kwak <br> **Exception Handling** <br> 👑 Sanghun Lee, Yeongjun Kwak <br>**Unit Test Building** <br> Yujin Lee
Environment Setup | **Maven Dependancy** <br> Yujin Lee
Documentation | **README.md** <br> 👑 Yujin Lee

<br>

---

<br>

## Milestone 3 (Upcoming)
## Milestone 4 (Upcoming)

## Team Members
* 20171012 Yeongjun Kwak (@sirin05137 ) - [kyj05137@unist.ac.kr](kyj05137@unist.ac.kr)
* 20171183 Sanghun Lee (@sanghun17) - [sanghun17@unist.ac.kr](sanghun17@unist.ac.kr)
* 20171194 Yujin Lee (@yuujinleee) - [yujinlee@unist.ac.kr](yujinlee@unist.ac.kr)


## License & Acknowledgements
* F. Maxwell Harper and Joseph A. Konstan. 2015. The MovieLens Datasets: History and Context. ACM Transactions on Interactive Intelligent Systems (TiiS) 5, 4, Article 19 (December 2015), 19 pages. DOI=http://dx.doi.org/10.1145/282787
