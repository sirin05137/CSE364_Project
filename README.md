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
  * [Running the Test](#running-the-test)
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
  * [Running the Test](#running-the-test-1)
    * [Examples](#examples-1)
  * [Supported Inputs](#supported-inputs-1)
  * [Error Codes](#error-codes-1)
    * [Examples for the Error Codes](#examples-for-the-error-codes-1)
  * [About JUnit Test](#about-junit-test-1)
  * [Contribution by Area](#contribution-by-area-1)
* [Milestone 3](#milestone-3)
  * [Explanation of the Algorithm](#explanation-of-the-algorithm-1)
     * [1) Item-based Collaborative Filtering](#1-item-based-collaborative-filtering)
     * [2) Genre-based Recommendation](#2-genre-based-recommendation)
     * [Interpretation of the Algorithm Output](#interpretation-of-the-algorithm-output)
  * [Running the Test](#running-the-test-2)
    * [Examples](#examples-2)
  * [Supported Inputs](#supported-inputs-2)  
  * [Error Codes](#error-codes-2)
     * [Examples for the Error Codes](#examples-for-the-error-codes-2)
  * [About JUnit Test](#about-junit-test-2)
  * [Contribution by Area](#contribution-by-area-2)
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
├── target/                           # Generated when mvn install
│   └── site/
│       └── jacoco/                           
│           └── index.html            # JaCoCo Code Coverage Report                
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

To briefly explain how the algorithm works : 
1. First, it makes the ArrayList(`valid_user_list`) of users that matches the inputs from `users.dat`.
2. And then, it extracts the ratings information and movie data from `ratings.dat` and `movies.dat` using `valid_user_list`.
3. Check if there aren't sufficient amount of movie candidates to be ranked (On here, it is set to `100` movies) for specified user data **OR** when `m` is too small for the movie candidates (On here, it is set to `10` votes),
   If the conditions are not met -> 4.
   If all the conditions are met -> 5.
4. 'Similar' users will be added to `valid_user_list` in order of precedence (priority) by function `make_intersection_list_macro`. 
   After adding the similar users, it will repeat the steps from 2 with added `valid_user_list` again (Until the conditions on Step 3 is met).
5. Print out Rank Top 10 movies based on calculated Weighted Rating(R).


#### 1) Bayesian Estimate
**Bayesian Estimate** is an estimator that can help minimizing the risk of including  that minimizes the posterior expected value of a loss function.
By making use of Bayesian Estimate, the algorithm calculates Weighted Rating (`W`) and arranges movies with `W`.
In this way, the movies with very few ratings or below-average ratings will have comparably light weight.

In Detail, the calculation of Weighted Rating(`W`) is implemented by
* Making an ArrayList (`classified_table`) by selecting the object with more than `m` votes from `movie_data_table`.
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
  * The value of `m` is obtained by the `percentile` function. The function returns the number of votes of the movie that corresponds to (1-`p`)*100 % .
    ```ruby
    static int percentile(ArrayList<Movie_data_node> movie_rating_matrix, double p)
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
| `InputInvalidError` | Entered genre input is invalid. | Thrown when the entered genre is invalid. (e.g. Location of pipeline `|`)
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
% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 
InputNumError: The input must be in this format : "gender" "age" "occupation" "genre" (genre is optional).

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "Female" "25" "Gradstudent"
InputInvalidError : Entered gender input is invalid.

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "F" "12.5" "Gradstudent"
InputInvalidError : Entered age input is invalid. Age must be a natural number.

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "F" "25" "Lizard"       
InputInvalidError : Entered occupation (Lizard) doesn't exist.

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "F" "25" "Gradstudent" "Action|Adventure|"
InputInvalidError : Entered genre input is invalid.

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "F" "25" "Gradstudent" "ACCCCCTION"
InputInvalidError : Entered genre (accccction) doesn't exist.

% java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar group11.milestone2 "F" "25" "Gradstudent" ""          
InputEmptyError : Genre input hasn't passed. Genre must not be empty
```

<br>

### About JUnit Test
The [JaCoCO plugin](https://www.eclemma.org/jacoco/) (ver : `0.8.2`) is used for this JUnit Test to generate the code coverage report. 
The Code coverage goal for this project has been set to `0.9` (90%).
The code coverage report `index.html` can be found in `target/site/jacoco/` directory and viewed with chrome or other extensions.
* The test includes total 16 tests to check the code functionality across the implemented functions and variables.

##### Example of Report
![image](https://user-images.githubusercontent.com/38070937/116877570-163b8b80-ac59-11eb-9111-91ec3a950e0b.png)

### Contribution by Area
| Area | Contribution |
| :--- | :--- |
Java Implementation | **Models and Data Structures** <br> 👑 Yeongjun Kwak <br> **Exception Handling** <br> 👑 Sanghun Lee, Yeongjun Kwak <br>**Unit Test Building** <br> 👑 Yujin Lee
Documentation | **README.md** <br> 👑 Yujin Lee

<br>

---


## Milestone 3
> Goal : 
> * Implement RESTful web service
> * Movies Recommendation Feature based on the **Movie title** (and limit).

### Explanation of the Algorithm
On Milestone 3, the new feature has added to provide movies recommendation from the **Movie title** input.
The code returns the input-specified numbers(`limit`, default = 10) of movies for the specified movie title.

The algorithm is built based on the two concepts below :
   1) Item-based Collaborative Filtering
   2) Genre-based Recommendation


#### 1) Item-based Collaborative Filtering
Collaborative Filtering recommends movies by calculating **the similarity between the specified movie (input) and the others**.
Collaborative Filtering are subdivided to two methods ; User-based and Item-based. ([reference](Recommender Systems — User-Based and Item-Based Collaborative Filtering))  

This program uses **Item-based Collaborative Filtering**.
i.e. if a user group who highly rated Movie A have rated Movie B highly, the Movie A and Movie B are considered to be **similar**.

#### Step-by-step Explanation of Algorithm
1. First, to measure the **similarity** between movies, **Pearson correlation coefficient** has used.
The details for **Pearson correlation coefficient** can be found [here](https://en.wikipedia.org/wiki/Pearson_correlation_coefficient).
The formula to get Pearson correlation coefficient is as below :
   ![image](https://user-images.githubusercontent.com/38070937/119506343-cb6ce980-bda8-11eb-9b59-c064741d7c58.png)

   The method in program (`milestone3.java`) that implements the Pearson correlation coefficient is as below :
   ```java
   static double get_pearson_similarity(HashMap<String, Integer> rating, HashMap<String, Integer> rating_of_input, double mean_rating_of_input, double distance_of_input)
   ```
<br>

2. After getting the **similarity** between movies by Pearson correlation coefficient, the movies are sorted by decreasing similarity order.

   ##### Example - Movies sorted by decreasing similarity order
   ![image](https://user-images.githubusercontent.com/38070937/119506629-1129b200-bda9-11eb-9704-33f6d06a091d.png)

   When the sorting has finished, the program adds (1.5 * limit) numbers of items from this list to `ArrayList<Movie_data_node> movie_data_table`.
   (If the input was "Superman (1978)", it excludes the same movie when adding as the same movie as input should be excluded in the recommendation result.)
   
<br>
   
3. After then, the program calculates the **Weighted Rating** by adding **Pearson correlation coefficient** and **IMDB Weighted Rating**.
   (The **IMDB weighted rating** here is same from milestone2.)

   * ##### Recap for IMDB Weighted Rating `W = (vR+mC)/(v+m)`
   | Variables | Explanation |
   | :---: | :--- |
   W | Weighted rating
   v | Total number of ratings **for the movie** = (votes)
   m | Minimum number of ratings required to be listed in the Top 10
   R | Average rating **for the movie** as a number from 0 to 5 (mean)
   C | Average rating across **all the movies**
   * The reason of adding **Pearson correlation coefficient** and **IMDB Weighted Rating** is that it can give more weight on the movies with higher **similarity**.
   
      For example, when the input is `"Superman (1978)`, the movies "Superman II (1980)" and "Superman III (1983)" get to have high similarity with the input from the method above. 
     
      However, these movies have comparably low average rating (from users).
      To give more weight on these movies (high similarity but low rating), **Pearson correlation coefficient** and **IMDB Weighted Rating** has added.

   * The value of `v` and `R` above can be easily calculated from `Movie_data_node`.
   And the value of  `m` and `C` can be calculated from the methods each below :
   ```java
   static int percentile(ArrayList<Movie_data_node> movie_rating_matrix, double p)
   ```
   ```java
   static double total_average_rating(ArrayList<Movie_data_node> movie_rating_matrix)
   ```
   The two methods above are imported from the milestone2.
   And the value of `p` in `Percentile` method is set to 0 (zero) here. (If p > 0, the movies with high similarity can be ignored)
<br>

4. Lastly, the `classified_table`(ArrayList) are made by selecting the items with more than `m` vote counts from `movie_data_table`.
   And then, the program sorts the list with decreasing Weighted Rating value and prints out the upper `limit` number of movies.

<br>
   
#### 2) Genre-based Recommendation

##### Intro - The Weakness of Item-based Collaborative Filtering
The Genre-based Recommendation has implemented to complement the weakness of Item-based Collaborative Filtering.
The weaknesses of Item-based Collaborative Filtering are : 
* When there is no rating data available for the movie (input), the **similarity** cannot be calculated.
* When there are too few rating data for the movie (input), the recommendation output is not reliable.
* When the rating for specific movie from different users are the same, the **Pearson correlation coefficient** cannot be calculated.
  e.g movie A : (3,3,0,3) * 0 means didnt rate.
  
In these cases, the recommendation output is not reliable or cannot be calculated.
To complement this weaknesses, **when the number of ratings for the movie input is less than 100**, Genre-based recommendation is used.

Genre-based recommendation generates movie recommendation by calculating **the similarity between the genre(s) of input movie's and others'**.

#### Step-by-step Explanation of Algorithm

1. First, To measure the **similarity**, the algorithm uses **Jaccard similarity**.
The formula to get **Jaccard similarity** is as below :
   ![image](https://user-images.githubusercontent.com/38070937/119506368-d0ca3400-bda8-11eb-8500-85f27995f622.png)

   The method in program (milestone3.java) that implements the Pearson correlation coefficient is as below :
   ```java
   static double get_jaccard_similarity(ArrayList<String> movie_data ,ArrayList<String> genre_of_input_list)
   ```

2. After getting the similarity between movies by Jaccard similarity, the movies are sorted by decreasing similarity order.
   On here, there's 3 cases regarding the similarity and number of movies to recommend (limit).
      * Case 1, The similarity of (limit*1.5)th movie is not 0
        (**A** : the similarity of (limit*1.5)th movie)
        Add all the movies with similarity 1~A to `ArrayList<Movie_data_node> movie_data_table`.
        In this case, the length of List is bigger than (limit*1.5) as the movies with similarity A are also added.
      * Case 2, The similarity of (limit*1.5)th movie is 0 but (limit)th movie's is not 0
        (**B** : the similarity of (limit)th movie)
        Add all the movies with similarity 1~B to `ArrayList<Movie_data_node> movie_data_table`.
        In this case, the length of List is bigger than (limit) and smaller than (limit*1.5) as the movies with similarity B are also added.
      * Case 3, The similarity of (limit)th movie is 0
        Add all the movies with **non-zero** similarity to `ArrayList<Movie_data_node> movie_data_table`.
        Calculate the IMDB weighted rating for the movies with zero similarity and Add (limit - listlength) number of movies to `movie_data_table`.
   The same movie with the movie input is excluded for the recommendation when adding movies to `movie_data_table`.

3. After then, the program calculates the **Weighted Rating** (in IMDB weighted rating way)
   The value of `p` here in `Percentile` method is calculated from the method below :
  ```java
    static double get_ratio(int a, int b)
  ```

4. Lastly, the `classified_table`(ArrayList) are made by selecting the items with more than `m` vote counts from `movie_data_table`.
    And then,  the program sorts the list with decreasing Weighted Rating value and prints out the upper `limit` number of movies.

#### Interpretation of the Algorithm Output

When the Item-based collaborative filtering is used,
the program sorts the movies in decreasing similarity order and rank the 1 ~ (limit*1.5)th movies with altered IMDB weighted rating.
However, this rank gets differ greatly depends on the size of `limit`.

* When the number of `limit` is small enough (e.g. `limit` is 10 or 20)
The dataset that is used for ranking has high similarity, making the output (recommended movies) having high similarity.
* When the number of `limit` is too big (e.g. `limit` is 1000)
The similarity of the output gets low but the movies with high ratings get priority for the recommendation.
i.e. it's hard to find the movies with high similarity among the highly placed movies in output.

If there's movie A and movie B, as `limit` gets bigger and bigger, the highly placed movies in output gets similar.

### Running the Test
Continued from [Installation](#installation) steps.
1. (In the Docker Container) Run `./mvnw spring-boot:run`
```ruby
root@containerID:~/project# ./mvnw spring-boot:run
```
2. Open another terminal and type the curl command there.
```ruby
// sample command
curl -X GET 'http://localhost:8080/movies/recommendations' \
-H 'Content-type: application/json' \
-d '{ "title": "Toy Story (1995)", "limit": 3 }'
```
To access the docker that is running on #1,
On another terminal, use `docker ps` to get the running container's ID and
type `docker exec -it (containerID) (your_own_bash_command)`.
i.e. you can put the curl command on the `your_own_bash_command` field above.

#### Examples

When valid inputs are passed, the output message will look like this :
```ruby
[ {
  "title" : "Toy Story 2 (1999)",
  "genre" : "Animation|Children's|Comedy",
  "imdb" : "http://www.imdb.com/title/tt0120363"
}, {
  "title" : "Bug's Life, A (1998)",
  "genre" : "Animation|Children's|Comedy",
  "imdb" : "http://www.imdb.com/title/tt0120623"
}, {
  "title" : "Beauty and the Beast (1991)",
  "genre" : "Animation|Children's|Musical",
  "imdb" : "http://www.imdb.com/title/tt0101414"
} ]
```

* Please note that **genre**(singular) is used, not ***genres*** (plurar).
  
##### Testing for the Milestone 2 feature
Movie recommendation based on **gender / age / occupation / genre**

Below is curl command example for GET requests passing the user input in JSON type
Test with different json body parts as your need.
```ruby
// Input
curl -X GET 'http://localhost:8080/users/recommendations' \
-H 'Content-type: application/json' \
-d '{"gender":"F", "age":"25", "occupation":"Grad student", "genre":"Action"}'

// Output - Top 10 movies are returned.
[ {
  "title" : "Princess Bride, The (1987)",
  "genre" : "action|adventure|comedy|romance",
  "imdb" : "http://www.imdb.com/title/tt0093779"
}, {
  "title" : "Matrix, The (1999)",
  "genre" : "action|sci-fi|thriller",
  "imdb" : "http://www.imdb.com/title/tt0133093"
}, {
  "title" : "Raiders of the Lost Ark (1981)",
  "genre" : "action|adventure",
  "imdb" : "http://www.imdb.com/title/tt0082971"
},
...
```
##### Testing for the Milestone 3 feature 
Movie recommendation based on the **movie title / limit**

Below is curl command example for GET requests passing the movie input in JSON type
Test with different json body parts as your need.
```ruby
// Input
curl -X GET 'http://localhost:8080/movies/recommendations' \
-H 'Content-type: application/json' \
-d '{ "title": "Toy Story (1995)", "limit": 3 }' 

// Output - (limit) number of movies are returned.
// (when limit is not specified, 10 movies are returned.)                          
[ {
  "title" : "Toy Story 2 (1999)",
  "genre" : "Animation|Children's|Comedy",
  "imdb" : "http://www.imdb.com/title/tt0120363"
}, {
  "title" : "Bug's Life, A (1998)",
  "genre" : "Animation|Children's|Comedy",
  "imdb" : "http://www.imdb.com/title/tt0120623"
}, {
  "title" : "Beauty and the Beast (1991)",
  "genre" : "Animation|Children's|Musical",
  "imdb" : "http://www.imdb.com/title/tt0101414"
} ]%
```

### Supported Inputs
##### Testing for the Milestone 2 feature
* The field name must match exactly to "gender", "age", "occupation", "genre" and should be in the lower-case.
* For the gender, age, occupation, and genre inputs, [the same rules from Milestone2](#supported-inputs-1) are applied here as well.
* However, each of the fields **can be left empty**.
* When the field(s) are missing **OR** the wrong field name has passed, for that field, the web service makes a get request with default value `""`.
  
* Please note that **genre**(singular) is used, not ***genres*** (plurar).
* Examples
    ```ruby
    // Example 1 - Field name wrong.
    // "" "25" "Grad" "Action" is passed to program instead.
    {
        "Gender": "F",
        "age": "25",
        "occupation": "Grad",
        "genre": "Action"
    }
    
    // Example 2 - Missing field.
    // "" "" "" "" is passed to program instead.
    {
    
    }
    
    // Example 3 - It should be genre, not genres.
    // "F" "25" "Grad" "" is passed to program instead.
    {
        "gender": "F",
        "age": "25",
        "occupation": "Grad",
        "genres": "Action"
    }
    ```

##### Testing for the Milestone 3 feature
* The field name must match exactly to "title", "limit" and should be in the lower-case.
* For the **title**, the title should be the existing name in the `movies.dat`.
   * The program accepts white space error and is not case-sensitive.
     Toy Story (1995) // O
     ToyStory(1995)   // O
     toy stoRy (1995) // O
     Toy Story        // X (The name in movies.dat is with year)
* For the **limit**, it should be int value with 0 < limit <= 2000
* However, each of the fields **can be left empty**.
* When the field(s) are missing **OR** the wrong field name has passed, for that field, the web service makes a get request with default value `""`.

* Examples
    ```ruby
    // Example 1 - Field name wrong.
    // This is invalid input.
    {
    "Title": "Toy Story (1995)",
    "limit": -5
    }
    
    // Example 2 - Missing field.
    // "Toy Story (1995)" "" is passed to program instead.
    {
    "title": "Toy Story (1995)"
    }
    ```

<br>

### Error Codes
> Possible errors thrown by invalid json body input.

When invalid input is put, the web service returns the error message (json body) with Http Status code `400` (Bad Request)


#### Examples for the Error Codes
##### For the Milestone 2 feature
Input (json body)
```json
{
    "gender": "ABCD",
    "age": "EFG",
    "occupation": "HIJK",
    "genre": "LMN|OP"
}
```
Output (json body) - Status code **400 Bad Request**
```json
{
	"error": "InputInvalidError",
	"message": [
		"Entered gender input (ABCD) is invalid.",
		" Entered age input (EFG) is invalid.",
		" Entered occupation input (HIJK) is invalid.",
		" Entered genre input (LMN|OP) is invalid."
	]
}
```

##### For the Milestone 3 feature
Input (json body)
```json
{
    "title": "Toy Story",
    "limit": -5
}
```
Output (json body) - Status code **400 Bad Request**
```json
{
	"error": "InputInvalidError",
	"message": [
		"Entered title input (Toy Story) is invalid.",
		" Entered limit input (-5) is invalid."
	]
}
```

<br>

### About JUnit Test
The [JaCoCO plugin](https://www.eclemma.org/jacoco/) (ver : `0.8.2`) is used for this JUnit Test to generate the code coverage report.
The Code coverage goal for this project has been set to `0.9` (90%).
The code coverage report `index.html` can be found in `target/site/jacoco/` directory and viewed with chrome or other extensions.
* The test includes total 16 tests to check the code functionality across the implemented functions and variables.

##### Example of Report
(Below example is from Milestone 2)
![image](https://user-images.githubusercontent.com/38070937/116877570-163b8b80-ac59-11eb-9111-91ec3a950e0b.png)

### Contribution by Area
| Area | Contribution |
| :--- | :--- |
Java Implementation | **Models and Data Structures** <br> 👑 Yeongjun Kwak <br> **Exception Handling** <br> 👑 Sanghun Lee, Yeongjun Kwak <br>**Unit Test Building** <br> 👑 Yujin Lee
REST service Implementation & Dependancy management | 👑 Yujin Lee
Documentation | **README.md** <br> 👑 Yujin Lee

<br>

---

<br>

## Milestone 4 (Upcoming)

## Team Members
* 20171012 Yeongjun Kwak (@sirin05137 ) - [kyj05137@unist.ac.kr](kyj05137@unist.ac.kr)
* 20171183 Sanghun Lee (@sanghun17) - [sanghun17@unist.ac.kr](sanghun17@unist.ac.kr)
* 20171194 Yujin Lee (@yuujinleee) - [yujinlee@unist.ac.kr](yujinlee@unist.ac.kr)


## License & Acknowledgements
* F. Maxwell Harper and Joseph A. Konstan. 2015. The MovieLens Datasets: History and Context. ACM Transactions on Interactive Intelligent Systems (TiiS) 5, 4, Article 19 (December 2015), 19 pages. DOI=http://dx.doi.org/10.1145/282787
