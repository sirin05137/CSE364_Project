# CSE364 Group11

- [x] 각자 로컬에 Docker, InteliJ IDEA, JDK 11 설치 @all
- [x] github repo 초기 세팅 @sirin05137
- [x] dockerfile, run.sh 초안 작성 ('/scripts'에) @yuujinleee
- [x] java file 초안 작성 @sirin05137
- [x] pom.xml 초안 작성
- [ ] dockerfile, run.sh 검토..? @sirin05137  @sanghun17
- [ ] 각자 branch 생성 후 **java file** 디버깅 @all
- [ ] README.md 작성 (Run Guide 등) @yuujinleee
- [ ] GroupID_StudentID.txt 각자 작성 @all



## Table of Contents
* [About the Project](#about-the-project)
   * [Built with](#built-with)
* [Repository Structure](repository-structure)
* [Setup & Run Guide](#setup-&-run-guide)
* [Contribution by Area](#contribution-by-area)
   * [Milestone 1](#milestone-1)
   * Milestone 2
   * Milestone 3
   * Milestone 4
* [Team members](#team-members)
* [License](#license)


## About the Project
This project is for the capstone project in SW Engineering (CSE364) at UNIST, developed by Youngjun Kwak, Sanghun Lee and Yujin Lee.

This is a movie recommendation system. 
> This part needs to be updated

### Built with
* Ubuntu 20.04
* JDK 11
> This part needs to be updated


## Repository Structure
```
.
├── data                    # Compiled files
├── src                     # Source files
├── test                    # Automated tests
├── scripts                 # Tools and utilities
└── README.md
```
> This part needs to be updated


## Setup & Run Guide
> This part needs to be updated by yujin

## Test Guide
1. Download _dockerfile_ and _run.sh_ and run the following commands : 
```
docker build -t *image_name* */path/to/dockerfile*
```
```
docker run -it *image_name*
```
2. Inside the docker container, run : 
```
root@containerID$ sh run.sh
```

#genre input regulation
1. you must enter the correct spelling. possible occupation are below,
"Action", "Adventure", "Animation", "Children's", "Comedy", "Crime", "Documentary", "Drama", "Fantasy", "Film-Noir", "Horror", "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western"
2. You don't have to worry about uppercase and lowercase letters.
3. You don't have to worry about spacing.
4. If you want to know the rating of a movie with multiple genres, put "|" between genres. (ex. comdey|horror)

## Contribution by Area
### Milestone 1
> Environment setup, data loading, data preprocessing
### Milestone 2
> Develop functionality #1
### Milestone 3
> Develop functionality #2
### Milestone 4
> Define and develop your own functionality and publish the system as a web application

#### UI Design
Kaiying Shan, Hanzhi Zhou, Zichao Hu, Elena Long, Mint Lin

#### Models and Data Structures
Hanzhi Zhou, Kaiying Shan

#### Documentation
Hanzhi Zhou, Kaiying Shan, Zichao Hu
> This part needs to be updated


## Team Members
* 20171012 Youngjun Kwak - [kyj05137@unist.ac.kr](kyj05137@unist.ac.kr)
* 20171183 Sanghun Lee - [sanghun17@unist.ac.kr](sanghun17@unist.ac.kr)
* 20171194 Yujin Lee - [yujinlee@unist.ac.kr](yujinlee@unist.ac.kr)


## License & Acknowledgements
* F. Maxwell Harper and Joseph A. Konstan. 2015. The MovieLens Datasets: History and Context. ACM Transactions on Interactive Intelligent Systems (TiiS) 5, 4, Article 19 (December 2015), 19 pages. DOI=http://dx.doi.org/10.1145/282787
