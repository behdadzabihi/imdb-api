This application processes the IMDB dataset to provide various functionalities through RESTful APIs using :
Java 21
Spring boot 3.3.10
lombok

Key Features:

Dataset Import: Loads the IMDB dataset from a local folder to avoid performance issues. The actual implementation reads compressed data files.

Titles with Same Director & Writer (Alive): Returns titles where the director and writer are the same person and still alive.

Titles Featuring Two Actors: Retrieves all titles where two given actors have worked together.

Top Titles by Genre & Year: Returns the best titles per year for a given genre based on votes and ratings.

HTTP Request Counter: Tracks and returns the total number of requests since the last startup.


For this project, since the data import must be done locally according to the instructions, 
I have done that as well and provided the path to a local folder. I could have placed the data directly into the project, but that would have made the project heavy, 
so I didn't do that. Additionally, although I could have uploaded the data to Git or Google Drive, this was not done due to the project's needs.

For testing, I have included a reduced dataset within the project. However,
it is obvious that reading the files in the actual implementation is done differently, as our project is designed to handle the original compressed dataset.

For counting the number of requests, a better approach would be to use a filter,
which would automatically display the count. However, to be able to call it as an endpoint, I implemented it in a controller, which results in some repetition of code that couldn't be avoided.








