## Inspiration
Track: Continental
How can we - in our daily work environment - motivate people to act climate friendly? What can employer s do?

## What it does
We were thinking about a mobile app where people can participate in "climate duels". They can join a team and compete against their teammates or another team.
After installing the app on their mobile, people can join a team and choose a username. Then they are given different categories defined by their team and the can choose what they want to do in a week. They select the challenges themselves by what is doable for them, eg. travel with the bike 3 times a week (instead of the car). In each week you can reach 100 points if you reach your goal. You can see your score in your team...
...and how your team does.

## How we built it
The application is a native Java Android App, using an Azure PostgreSQL for storing and getting the data.</br>
<img src="https://www.pixellove.com/assets/img/home/logo-android.png"/>
<img src="https://toppng.com/uploads/thumbnail/java-logo-vector-free-download-11574238315lbps3x45md.png"/>
<img src="https://hystax.com/wp-content/uploads/2019/05/Microsoft-Azure-Logo-small.png" />
<img src="https://wiki.postgresql.org/images/3/30/PostgreSQL_logo.3colors.120x120.png" />
<img src="https://4.bp.blogspot.com/-i1maQG0pYmg/VbqHs_0APSI/AAAAAAAAB6c/pbLTnzmMPkU/s175/logo_android_studio_512dp.png" />
## Challenges we ran into
The most challenging part was finding a system for fair calculation of the points. As people have different requirements and working conditions in their daily life, the same challenges would not be doable for everyone. So we had to think about a system which gives the same points to someone who lives 1 km away from work and chooses the bike and another one who is 30 km away and chooses the train. We had different solutions in mind, which we were discussing about. Then we decided make a team competition with points from personal challenges.
Also we struggled with creating a working database to get and store our data.

## Accomplishments that we're proud of
We are proud to present to you a working prototype with database connection.

## What we learned
We had a good revision in native Java programming and had new insights in database-management directly in the Android app.

## What's next for ClimateDuels
In the future we see our app being used by climate active companies to create a vivid and engaging competition, connecting likeminded people.
Furthermore we can think of more categories and features to provide a broader spectrum of challenges to choose from. We also had in mind to use the phones sensors to automatically track the users movements.

## Running
Please create a file "database.properties" in the root directory of the project with this content:
```
host="<host>"

port="<port>"

database="<database>"

user="<userY"

password="<password"

sslmode="require"
```

# GitHub branch
Please use the branch: **release-maybe**
