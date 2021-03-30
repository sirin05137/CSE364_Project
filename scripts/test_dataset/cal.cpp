#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std;

const int MOVIE_ROW = 3884;
const int MOVIE_COL = 19;
const int RATING_ROW = 1000211;
const int RATING_COL = 3; 


int find_genrenum(string s){
   if (s == "Action") return 1;
   if (s == "Adventure") return 2;
   if (s == "Animation") return 3;
   if (s == "Children's") return 4;
   if (s == "Comedy") return 5;
   if (s == "Crime") return 6;
   if (s == "Documentary") return 7;
   if (s == "Drama") return 8;
   if (s == "Fantasy") return 9;
   if (s == "Film-Noir") return 10;
   if (s == "Horror") return 11;
   if (s == "Musical") return 12;
   if (s == "Mystery") return 13;
   if (s == "Romance") return 14;
   if (s == "Sci-Fi") return 15;
   if (s == "Thriller") return 16;
   if (s == "War") return 17;
   if (s == "Western") return 18;

   return -1;
}

int find_occunum(string s){
   if (s == "Other") return 0;
   if (s == "Academic") return 1;
   if (s == "Artist") return 2;
   if (s == "Clerical") return 3;
   if (s == "Collegestudent") return 4;
   if (s == "Customerservice") return 5;
   if (s == "Doctor") return 6;
   if (s == "Executive") return 7;
   if (s == "Farmer") return 8;
   if (s == "Homemaker") return 9;
   if (s == "k-12student") return 10;
   if (s == "lawyer") return 11;
   if (s == "programmer") return 12;
   if (s == "retired") return 13;
   if (s == "sales") return 14;
   if (s == "scientist") return 15;
   if (s == "self-employed") return 16;
   if (s == "technician") return 17;
   if (s == "tradesman") return 18;
   if (s == "unemployed") return 19;
   if (s == "writer") return 20;

   return -1;
}


int main(){
   
   //------------------ LOAD MOVIE INFO ------------------
   ifstream fin_movie("movie.csv");
   string movieinfo[MOVIE_ROW][MOVIE_COL];
   // movieinfo[i][0]   : movieID
   // movieinfo[i][1~18]: bool for 18 genres,
   //                     Action,Adventure,Animation,Children's,
   //                     Comedy,Crime,Documentary,Drama,
   //                     Fantasy,Film-Noir,Horror,Musical,
   //                     Mystery,Romance,Sci-Fi,Thriller,
   //                     War,Western (in order)

   if (fin_movie.is_open()){
      int i = 0;
      string line;

      while( getline(fin_movie,line) ){
         stringstream ss(line);
         string movieID, boolforgenre;
         
         int j = 0;
         getline(ss,movieID,',');      movieinfo[i][0] = movieID;
                     
         for ( j = 1 ; j < MOVIE_COL ; j++){
            getline(ss, boolforgenre, ',');
            movieinfo[i][j] = boolforgenre;
         }   
         i++;
      }
   }
   fin_movie.close();
   
   //------------------------------------------------------
   //------------------ LOAD RATING INFO ------------------  
   ifstream fin_rating("ratings.csv");
   int** ratinginfo = new int*[RATING_ROW];
   for (int i = 0 ; i < RATING_ROW ; i++){
      ratinginfo[i] = new int[RATING_COL];
   }
   // ratinginfo[i][0] : Occupation number
   // ratinginfo[i][1] : MovieID
   // ratinginfo[i][2] : Rating (1~5 score)

   if (fin_rating.is_open()){
      int i = 0;
      string line;

      while( getline(fin_rating,line) ){
         stringstream ss(line);
         string userID, occunum, movieID, Rating;
         
         getline(ss,userID,',');       //ignore userID string
         getline(ss,occunum,',');      ratinginfo[i][0] = stoi(occunum);
         getline(ss,movieID,',');      ratinginfo[i][1] = stoi(movieID);
         getline(ss,Rating,',');       ratinginfo[i][2] = stoi(Rating);
           
         i++;
      }
   }

   fin_rating.close();

   //------------------------------------------------------
   //------------------ GET INPUT FROM CSV ------------------
   string genreinput, occuinput; //target
   int genrenum, occunum; //target
   
   ifstream fin_input("test.csv");
   
   ofstream fout;
   fout.open ("result.csv",ios::app);
  

   if (fin_input.is_open()){
      string line;

      while( getline(fin_input,line) ){
         stringstream ss(line);
         string expected;
         
         getline(ss,genreinput,',');
         getline(ss,occuinput,',');
         getline(ss,expected,',');       //ignore expected string
         
         genrenum = find_genrenum(genreinput);
         occunum = find_occunum(occuinput);

         //CALCULATION
         //fout
         int fullrating = 0;
         int fullcount = 0;

         string movieID;

         for (int i = 0 ; i < MOVIE_ROW ; i++){
            if ( movieinfo[i][genrenum] == "1"){
               movieID = movieinfo[i][0];

               for (int j = 0 ; j < RATING_ROW ; j++ ){
                  stringstream sss;
                  sss << ratinginfo[j][1];
                  string rating_movieID = sss.str();
                 
                  //if ( (ratinginfo[j][0] == occunum) && ( ratinginfo[j][1] == stoi(movieID) )){
                  if ( (ratinginfo[j][0] == occunum) && (rating_movieID == movieID)){
                     //FOUND!
                     fullrating += ratinginfo[j][2];
                     fullcount++;
                  }
                  else {
                     continue;
                  }
               }
            }
            else {
               //movie doesnt fall to that category
               continue;
            }
            movieID = "";
         }

      
         double calresult = (double)fullrating / (double)fullcount;
         fout << genreinput << "," << occuinput << "," ;
         fout << setprecision(2) << fixed << calresult << endl;
      
         genreinput = "";
         occuinput = "";
      }
      
   }
   

   fin_input.close();

   //cin >> genreinput >> occuinput;



   //------------------ DELETE 2D ARRAY ------------------
   for(int i = 0; i < RATING_ROW; ++i) {
      delete [] ratinginfo[i];
   }
   delete [] ratinginfo;
   
   return 0;
}