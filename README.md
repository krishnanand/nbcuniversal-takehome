# nbcuniversal-takehome

Following are the list of endpoints supported the restful application

### POST /nbcuniversal/quiz ###

Creates a quiz game

**Response**

A success response will include:

● a status code of 201 Created

● Response Body Properties

| Name | Type | Description | Default |
| :---         |     :---:      |          :---: |         :---: |
| numberOfQuestions  | int | Maximum number of questions that can be asked | 3 |
| quizId | string | 10 digit alphanumeric string represening a unique quiz identifier | |
| active | boolean | the quiz is still active if the flag is true, and false otherwise | true |



### GET /nbcuniversal/quiz/{quizId}/questions ###

Generates unique questions per quiz id

**Response**

A successful response will include

● a status code of 200 OK

● Response body properties

| Name | Type | Description|
| :---         |     :---:      |          ---: |
| questionId  | integer   | Question Id associated with the question  |
| question   | string    | Question to be answered as a part of the quiz    |

If more requests are made than the permissible number of questions for a given quiz id, then the following message is returned

An successful response can be of two types

1) #### An invalid quiz id ####

In this case, 404 error is returned. The sample response 

````
{  
   "errors":[  
      {  
         "code":404,
         "message":"No quiz was found for quiz id <Invalid Quiz ID>"
      }
   ]
}
````

2) #### The number of requested questions exceeds maximum permissible assigned questions. ####

````
{  
   "errors":[  
      {  
         "code":429,
         "message":"There are no questions to be asked for quiz id <Generated Quiz ID>"
      }
   ]
}
````

The error response is defined below

| Name | Type | Description|
| :---         |     :---:      |          ---: |
| errors  | array   | array of errors |
| code   | integer    | Http Status code representing the error message |
| message | string | user friendly error message|
