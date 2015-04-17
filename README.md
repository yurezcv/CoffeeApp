# CoffeeApp
Coffee App for the technical interview at Percolate.

Hi there,
There should be no problems with building this project. You can easily import it to Android Studio from this repo.

I made this technical/design decisions during the development process:
* For an easy image loading, I used [Picasso](http://square.github.io/picasso/) library;
* For sending the received data to the main app thread, I used [Otto](http://square.github.io/otto/) library;
* To calculate the period of time, I used [Joda Time](http://www.joda.org/joda-time/);
* I created a Request Manager class to handle all request in one place and then easily manage the errors, if there is any error;
* I decided to use Material style, because it is up to date design style for Android apps;
* I didn't use the MVP pattern because there is not so much logic in this app for defining it to separate MVP classes.
