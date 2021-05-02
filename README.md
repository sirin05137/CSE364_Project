# CSE364 Group11

## Table of Contents
* [About the Project](#about-the-project)
  * [Built with](#built-with)
  * [Repository Structure](#repository-structure)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Milestone 1](#milestone-1)
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

## :triangular_flag_on_post:
## Milestone 1 
> Goal : Environment setup, data loading, data preprocessing

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


## :triangular_flag_on_post:
## Milestone 2
> Goal : Implement Top 10 Movies Recommendation Features

### Running the Test
Continued from [Installation](#installation) steps.
1. (In the Docker Container) Run Java command with **3 or 4 arguments**.
   4 Arguments(_InputStr1 InputStr2 InputStr3 InputStr4_) are accordingly **Gender, Age, Occupation, _(and Genre)_**.
```ruby
root@containerID:~/project# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 InputStr1 InputStr2 InputStr3 InputStr4
```

#### Examples
##### Testing with 3 inputs
When valid inputs are passed, the output message will look like this :
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 InputStr1 InputStr2 InputStr3
// Output
3.42
```
##### Testing with 4 inputs
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 InputStr1 InputStr2 InputStr3 InputStr4
// Output
3.42
```
> 예시 맞게 수정하



#### Supported Inputs
InputStr1 | InputStr2 | InputStr3 | _(InputStr4)_
| :---: | :---: | :---: | :---: |
Gender | Age | Occupation | _(Genre)_

* **Gender**
  * Must be **one letter** and is not case-sensitive.<br>
  i.e. one of those ; **"`F`", "`f`", "`M`", "`m`"**.
  * **Can be left empty** when replaced by **paired double quotation marks**. <br>
  i.e. `""`
    
> 스페이스 포함인가? (수정예정)
  
* **Age**
  * Must be an **integer value** bigger than 0. 
    * e.g. 35 (Supported) <br>
      -23 (X) <br>
      Thirty-Five (X)
  * **Can be left empty** when replaced by **paired double quotation marks**. <br>
      i.e. `""`
* **Occupation**
  * For Occupation, [the same rules from Milestone1](#supported-inputs) are applied here as well.
  * **Can be left empty** when replaced by **paired double quotation marks**. <br>
      i.e. `""`
* **Genre**
  * For Genre, [the same rules from Milestone1](#supported-inputs) are applied here as well.
  * The combination of genre inputs are allowed as it were in Milestone1. Please refer to [Combination of multiple genres as an input (Milestone 1)](#combination-of-multiple-genres-as-an-input)
  * **Cannot be left empty**. <br>
    i.e. `""`
    > 근데 어차피 인풋 3개면 can be left empty 아닌가? 머쓱.
    
> 예시 snippet  정리해서 넣기

### Contribution by Area
| Area | Contribution |
| :--- | :--- |
Fundamental Research | Sanghun Lee
Java Implementation | **Models and Data Structures** <br> 👑 Yeongjun Kwak <br> **Exception Handling** <br> 👑 Sanghun Lee, Yeongjun Kwak <br>**Unit Test Building** <br> Yujin Lee
Environment Setup | **Maven Dependancy** <br> Yujin Lee
Documentation | **README.md** <br> 👑 Yujin Lee, Sanghun Lee




## Team Members
* 20171012 Yeongjun Kwak (@sirin05137 ) - [kyj05137@unist.ac.kr](kyj05137@unist.ac.kr)
* 20171183 Sanghun Lee (@sanghun17) - [sanghun17@unist.ac.kr](sanghun17@unist.ac.kr)
* 20171194 Yujin Lee (@yuujinleee) - [yujinlee@unist.ac.kr](yujinlee@unist.ac.kr)


## License & Acknowledgements
* F. Maxwell Harper and Joseph A. Konstan. 2015. The MovieLens Datasets: History and Context. ACM Transactions on Interactive Intelligent Systems (TiiS) 5, 4, Article 19 (December 2015), 19 pages. DOI=http://dx.doi.org/10.1145/282787
