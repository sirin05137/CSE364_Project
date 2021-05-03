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
* In order to get the average rating of the movies that fall into a combination of **multiple genres**, connect the words with **the pipeline('|')** and **enclosed by double quotation marks(" ")**.
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
**Bayesian Estimate** is an estimator that can help minimizing the risk of including  that minimizes the posterior expected value of a loss function.
By making use of Bayesian Estimate, the algorithm calculates Weighted Rating (`W`) and arranges movies with `W`.
In this way, the movies with very few ratings or below-average ratings will have comparably light weight.

In Detail, the calculation of Weighted Rating(`W`) is implemented by
* Making an ArrayList (`classified_table`) by selecting the object with more than `m` votes from `movie_rating_table`.
* Sorting the ArrayList with descending-weighted-rating-order and printing Top 10 movies from it.

The original reference for Baysian Estimate can be found [here](https://www.fxsolver.com/browse/formulas/Bayes+estimator+-+Internet+Movie+Database+%28IMDB%29). However, in this project, the estimation method and variables has been set differently to adjust the differences in requirements.

### `W = (vR+mC)/(v+m)`

| Variables | Explanation |
| :---: | :--- |
W | Weighted rating
v | Total number of ratings **for the movie** = (votes)
m | Minimum number of ratings required to be listed in the Top 10
R | Average rating **for the movie** as a number from 0 to 5 (mean)
C | Average rating across **all the movies**

* `v` and `R`
  * The value of `v` and `R` are obtained by making use of `Movie_data_node`. 
* `m` : Minimum Number of Ratings 
  * The value of `m` is obtained by the `Percentile` function. The function returns the number of votes of the movie that corresponds to (1-`p`)*100 % .
    ```ruby
    static int Percentile(ArrayList<Movie_data_node> movie_rating_matrix, double p)
    ```
  * The value of `p` is differently set for number of movies by `set_p` function ; so that the validity of the weighted rating can be enhanced.
    ```ruby
    static double set_p(int size){
        if(size<100){
            return 0.5;
        }
        else if(size<200){
            return 0.6;
        }
        else if(size<500){
            return 0.7;
        }
        else{
            return 0.8;
        }
    } 
    ```
    e.g If there are 1000 movies with average ratings, the function will return **the number of ratings** of 200th-highest movie.

* `C` : Average rating across **all the movies**
  * The value of `C` is obtained by the `total_average_rating` function.
  ```ruby
  static double total_average_rating(ArrayList<Movie_data_node> movie_rating_matrix)
  ```

#### 2) Priority rule for including similar users
The algorithm firstly makes the ArrayList(`valid_user_list`) of users that matches the inputs from `users.dat`.
And then, this list is used to extract the ratings information and movie data from `ratings.dat` and `movies.dat`.

However,
* when there aren't sufficient amount of movie candidates to be ranked (On here, it is set to `100 movies`) for specified user data,
* **OR** when `m` is small for the movie candidates. (On here, it is set to `10` votes).
  * e.g. The case when less than 10 people have rated the movie is considered to be inappropriate.
the **'similar'** users will also be added to `valid_user_list` in order of precedence (priority) by function `make_intersection_list_macro`, until the number of movie candidates gets bigger than `100` and `m` gets bigger than 10.

The similar users with priority are the users with : 
1. Same **Gender**, **Occupation** and **Gender**
2. Same **Gender** and **Occupation**
3. Same **Age range** and **Occupation**
4. Same **Occupation**
5. Same **Gender** and **Age range**
6. Same **Gender**
7. Same **Age range**
8. All the users

The priority has set as above to give a more weighting on **Occupation**, and less on **Gender** and **Age Range**.

<br>

### Running the Test
Continued from [Installation](#installation) steps.
1. (In the Docker Container) Run Java command with **3 or 4 arguments**.
   The Arguments(_InputStr1 InputStr2 InputStr3 InputStr4_) are accordingly **Gender, Age, Occupation, _(and Genre)_**.
```ruby
root@containerID:~/project# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 InputStr1 InputStr2 InputStr3 InputStr4
```

* The maven building process(`mvn install` or `mvn test`) might take up to a minute. 

#### Examples

When valid inputs are passed, the output message will look like this :

##### Testing with 3 inputs
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "F" "25" "Gradstudent"

// Output
Sixth Sense, The (1999) (http://www.imdb.com/title/tt0167404)
Matrix, The (1999) (http://www.imdb.com/title/tt0133093)
Shawshank Redemption, The (1994) (http://www.imdb.com/title/tt0111161)
Usual Suspects, The (1995) (http://www.imdb.com/title/tt0114814)
Silence of the Lambs, The (1991) (http://www.imdb.com/title/tt0102926)
Close Shave, A (1995) (http://www.imdb.com/title/tt0112691)
Wrong Trousers, The (1993) (http://www.imdb.com/title/tt0108598)
Cinema Paradiso (1988) (http://www.imdb.com/title/tt0095765)
American Beauty (1999) (http://www.imdb.com/title/tt0169547)
Raiders of the Lost Ark (1981) (http://www.imdb.com/title/tt0082971)
```
##### Testing with 4 inputs
```ruby
// Input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "F" "25" "Gradstudent" "Action|Comedy"

// Output
Matrix, The (1999) (http://www.imdb.com/title/tt0133093)
Close Shave, A (1995) (http://www.imdb.com/title/tt0112691)
Wrong Trousers, The (1993) (http://www.imdb.com/title/tt0108598)
American Beauty (1999) (http://www.imdb.com/title/tt0169547)
Shakespeare in Love (1998) (http://www.imdb.com/title/tt0138097)
Raiders of the Lost Ark (1981) (http://www.imdb.com/title/tt0082971)
Cinema Paradiso (1988) (http://www.imdb.com/title/tt0095765)
Eat Drink Man Woman (1994) (http://www.imdb.com/title/tt0111797)
Raising Arizona (1987) (http://www.imdb.com/title/tt0093822)
Breakfast Club, The (1985) (http://www.imdb.com/title/tt0088847)
```

### Supported Inputs
InputStr1 | InputStr2 | InputStr3 | _(InputStr4)_
| :---: | :---: | :---: | :---: |
Gender | Age | Occupation | _(Genre)_

* **Common Rules for the inputs**
  * All the inputs must be enclosed with double quotation marks(`""`).
  * The inputs are **not case-sensitive**, therefore both "Action", "action" and "AcTiOn" are allowed.

* **Gender**
  * Must be **one letter** and is not case-sensitive.
  i.e. one of those ; **"`F`", "`f`", "`M`", "`m`"**.
  * **Can be left empty** when replaced by **paired double quotation marks**.
  i.e. `""`
  * Examples
    ```ruby
    "F"       // Supported
    "Female"  // X
    (nothing) // X (must be replaced by "")
    ""        // Supported
    ```
    
* **Age**
  * Must be an **integer value** bigger than 0.
  * **Can be left empty** when replaced by **paired double quotation marks**.
      i.e. `""`
  * Examples
    ```ruby
    "35"          // Supported
    "35.5"        // X
    "-23"         // X
    "Thirty-five" // X
    (nothing)     // X (must be replaced by "")
    ""            // Supported
    ```
    
* **Occupation**
  * For Occupation, [the same rules from Milestone1](#supported-inputs) are applied here as well.
  * However, on milestone 2, the spacing between input is allowed here.
  * **Can be left empty** when replaced by **paired double quotation marks**.
      i.e. `""`
  * Examples
    ```ruby
    "k-12student"   // Supported
    "K12student"    // X (The hyphens must not be omitted.)
    "Gradstudent"   // Supported
    "Grad student"  // Supported (Was not supported from Milestone 1)
    (nothing)       // X (must be replaced by "")
    ""              // Supported
    ```

* **Genre** (When testing with 4 inputs)
  * For Genre, [the same rules from Milestone1](#supported-inputs) are applied here as well.
  * For the combination of genre inputs, the formatting rules from Milestone 1 [(Combination of multiple genres as an input)](#combination-of-multiple-genres-as-an-input) applies here as well. <br> However, the pipeline(`|`) here is uesd to link **OR** conditions, not **AND**.
    * e.g. `Adventure|Animation` includes all the movies that are categorized as Adventure **OR** Animation to candidates for Top 10 movies.
  * **Must NOT be left empty**.
  * Examples
    ```ruby
    "Film-noir"         // Supported
    "Filmnoir"          // X (The hyphens must not be omitted.)
    Action|Adventure    // X (Must be enclosed by pipeline)
    "Action|Adventure|" // X (Pipeline should be between words)
    ""                  // The genre must not be left empty.
    ```

##### Testing with 3 inputs
```ruby
// Examples of Supported Inputs
“F” “25” “Gradstudent”
“M” “30” “Athletes”
“F” “” “retired”
“” “15” “”
“” “” “”

// Examples of Wrong Inputs
"F" "25"           // Empty part must be explicitly specified by ""
"25" "Gradstudent" // Empty part must be explicitly specified by ""
```

##### Testing with 4 inputs
```ruby
// Examples of Supported Inputs
“F” “25” “Grad student” “Action|Comedy”
“M” “30” “Athletes” “Children’s”
“F” “” “retired” “Animation|Drama|Fantasy”
“” “15” “” “Sci-Fi”
“” “” “” “Romance|Comedy”

// Examples of Wrong Inputs
“F” “25” “Grad student” “”  // The genre must not be left empty.
“F” “25” “Grad student” “Action|Comedy” "Drama" // Extra arguments are not allowed.
```
<br>

### Error Codes
Possible errors thrown by invalid input.

##### **Table 1** Invalid input errors

| Error | Message | Description | 
| :---: | --- | --- |
| `InputNumError` | The input must be in this format : "gender" "age" "occupation" "genre" (genre is optional). | Thrown when the number of argument is not 3 or 4. 
| `InputInvalidError` | Entered gender input is invalid. | Thrown when the entered gender is invalid.
| `InputInvalidError` | Entered age input is invalid. Age must be a natural number. | Thrown when the entered age is invalid.
| `InputInvalidError` | Entered occupation (_*inputString*_) doesn't exist. | Thrown when the entered occupation is invalid.
| `InputInvalidError` | Entered genre input is invalid. | Thrown when the entered genre is invalid.
| `InputInvalidError` | Entered genre (_*inputString*_) doesn't exist. | Thrown when the entered genre is invalid.
| `InputEmptyError` | Genre input hasn't passed. Genre must not be empty | Thrown when the `""` is passed for the genre input.



##### **Table 2** No data exist error

| Error  | Message | Description | 
| :---: | --- | --- |
| `NoDBError` | There are no movie available for more than _*m*_ votes | Thrown when there's no available movie list. This error is not likely to happen but implemented to prevent any error.
| `NoDBError` | There are no movie available for specified inputs. | Thrown when there's no available movie list or there exist movie(s) with no votes only. This error is not likely to happen but implemented to prevent any error.


* If the system is terminated with the error code listed above, the system exit status is `1`.

#### Examples for the Error Codes

```ruby
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project 

InputEmptyError : No argument has passed. 2 arguments are required. (InputStr1 InputStr2)

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Drama

InputNumError : Only 1 input has passed. 2 arguments are required.

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.project Drama Scientist Scientist

InputNumError : More than 2 arguments have passed. 2 arguments are required.
```

<br>

### About JUnit Test
The [JaCoCO plugin](https://www.eclemma.org/jacoco/) (ver : `0.8.2`) is used for this JUnit Test to generate the code coverage report. 
The Code coverage goal for this project has been set to `0.9` (90%).
The code coverage report `index.html` can be found in `target/jacoco-report/` and viewed with chrome or other extensions.
* The test includes total 20 tests to check the code functionality across the implemented functions and variables.
* For the System Exit related tests, to prevent the Java VM termination issue, the external extension written by Todd Ginsberg has been used here. [Reference : JUnit5 System.exit() Extension](https://github.com/tginsberg/junit5-system-exit)

##### Example of Report
# 스 크 린 샷 추 가 !

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
* T. Ginsberg, 2021, [JUnit5 System.exit() Extension](https://github.com/tginsberg/junit5-system-exit) 