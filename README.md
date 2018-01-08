# microservice-discussions
## API discussions
## Group List :
*Emmanuel Schmid* </br>
*Anastasia Zharkova* </br>
*Ibrahim Ounon* </br>
*Thuy-My Tran* </br>

# Purpose
We have to produce a kit of reusable micro-services containing different parts ( users, emails, discussion, survey, status ). Our groupe is responsible for discussion micro-service wich should be coordinate later with other groupes.  

# Tools 
*Swagger
*Cucumber
*InteliJ (Java)

# Structure
We have a notion of discussion and comment.  A discussion has un id (authomatically increase integer)ad his name (string)
Each comment has an unique id of discussion to which this comment is allows (integer), an id of the comment, and its name (string). As we can see the only difference between a comment and a discussion is a belonging to a discussion. If we compare it to a thread, a discussion is a father and a comment is it's child, if there is a child without a father its going to become a father (discussion in our case).
It's possible to : 
- create a discussion
- show a discussion (all comments of this discussion)
- show all discussions
- delete a discussion (with all comments if existed)
- delete all discussion
- create a comment
- show a particular comment
- delete a particular comment
- modify a comment
- show a list of all comment of this discussions
- delete all comments in a discussion
- vote for a comment
- report a comment

# Implementation of User microservice



