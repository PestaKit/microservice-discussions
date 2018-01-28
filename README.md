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
*Swagger* </br>
*Cucumber* </br>
*InteliJ* (Java) </br>

# Install 



# Structure
We have a notion of discussion and comment.  A discussion has un id (authomatically increase integer)ad his name (string)
Each comment has an unique id of discussion to which this comment is allows (integer), an id of the comment, and its name (string). As we can see the only difference between a comment and a discussion is a belonging to a discussion. If we compare it to a thread, a discussion is a father and a comment is it's child, if there is a child without a father its going to become a father (discussion in our case).
It's possible to : 
- create a discussion (one per article)
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



### *Create comment or discussion* </br>
Only authetificated users can create a comment or/and discussion, if the user is not authentificated he will receive an error message saying that he cannot create a discussion/comment ( no redirection to authentification page). 
Only one discussion is allow by article, if a user tries to create a nes discussion for an article which has already a discusion, user will receive an error message. 

### *Delete* </br>
Only authetificated users can delete a comment or/and discussion, if the user is not authentificated he will receive an error message saying that he cannot delete a discussion/comment ( no redirection to authentification page). 
When user deletes a discussion (only the autour of the discussion) , it means what every single child is deleted as well (cascade). We applied the same logic when user wants to delete a comment, all of its child will be deleted as well. This operation is irreversible, once the user says he wants to delete the item (discussion or comment) it's deleted from a database, it's impossible to undo this operation. There is no message asking if the user is sure to delete the item, so if it was a missclick there is no way to correct this error. 

### *Report* </br>
Only authetificated users can report a comment, if the user is not authentificated he will receive an error message saying that he cannot report a comment ( no redirection to authentification page). 
It's impossible to report a discussion, this operation is valid only for comments. User must be authentificated as well in order to proceed. The user can report as much comments he wants to, but he can report only once the same comment, he cannot undo this operation. One comment can be repoted by many users.   

### *Vote* </br>
Only authetificated users can vote for a comment, if the user is not authentificated he will receive an error message saying that he cannot vote for this comment ( no redirection to authentification page). 
Only comment can be voted, this property is a boolean ( true = +1, false = -1). It's not possible to do *getVote* in order to see who voted +1 et who -1, but it's possible to do "getComment* and see this information. It's impossible to undo the vote, neither change the vote, once user said +1 it stays like this.

Unauthentificated users can only read discussions and comments. 


# Implementation of User microservice

Once our system is operational, we added a notion of *user*.  We distinguishe standard user and use author (discussion and comment).

|    |      User    |  User Autor |
|----------|:-------------:|------:|
| vote comment |  yes | yes |
| delete comment |   no  |   yes |
| modify comment |no |    yes |
    
 


