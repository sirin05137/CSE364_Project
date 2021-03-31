#include <iostream>
#include <string>
#include <fstream>
#include <sstream>

using namespace std;

int main(){

   string genre[18] =   {"Action", "Adventure", "Animation", "Children's",
                            "Comedy", "Crime", "Documentary", "Drama",
                            "Fantasy", "Film-Noir", "Horror", "Musical",
                            "Mystery", "Romance", "Sci-Fi", "Thriller",
                            "War", "Western"
                        };

   string occupation[21] = {"Other", "Academic", "Artist", "Clerical",
                            "Collegestudent", "Customerservice", "Doctor", "Executive",
                            "Farmer", "Homemaker", "k-12student", "lawyer",
                            "programmer", "retired", "sales", "scientist",
                            "self-employed", "technician", "tradesman", "unemployed", "writer"
                           };

   ofstream fout;  // Create Object of Ofstream

   //if(fin.is_open()) fout<< "\n Writing to a file opened from program."; // Writing data to file

   
   fout.open ("dataset_other.txt",ios::app); // Append mode
   /*
   for (int i = 0 ; i < 18 ; i++){
      //cout << genre[i] << endl;
      for (int j = 0 ; j < 21 ; j++){
        //fout << genre[i] << "," << occupation[j] << ",0.00" <<endl ; 
      }
   }
   */
   for (int j = 0 ; j < 18 ; j++){
        fout << genre[j] << "," << "Other" << ",0" <<endl ; 
   }
   
   
   /*
   ifstream fin("db_multiple_genre.txt");
   fout.open ("dataset_6genres.txt",ios::app); // Append mode
   
   string line;
   int num_pipe = 0;

   while (getline(fin, line)){
         
         size_t n = std::count(line.begin(), line.end(), '|');

         if (n == (unsigned long)5){
            fout << line << endl;
         }
   }
   */

   cout << "\nData has been appended to file\n";
   fout.close(); // Closing the file

   return 0;
}