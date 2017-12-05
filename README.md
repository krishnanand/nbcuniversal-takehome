# NBC Digital Take Home Assignment

## Table of Contents

- [API Endpoints](#api-endpoints)

## API Endpoints ##
Following are the list of endpoints supported the restful application

### POST /nbcuniversal/quiz ###

Creates a quiz game

**Response**

A success response will include:

● a status code of 201 Created

● Response Body Properties

| Name | Type | Description | Default |
| :---         |     :---:      |          :--- |         :---: |
| numberOfQuestions  | int | Maximum number of questions that can be asked | 3 |
| quizId | string | 10 digit alphanumeric string represening a unique quiz identifier | |
| active | boolean | the quiz is still active if the flag is true, and false otherwise | true |

The sample response is given below

````
{  
   "numberOfQuestions":3,
   "quizId":"Gf7mrKT7Ym",
   "active":true
}
````

### GET /nbcuniversal/quiz/{quizId}/questions ###

Generates unique questions for quiz id

**Response**

A successful response will include

● a status code of 200 OK

● Response body properties

| Name | Type | Description|
| :---         |     :---     |          :--- |
| questionId  | integer   | Question Id associated with the question  |
| question   | string    | Question to be answered as a part of the quiz    |

The example of a successful response is given below

````
{  
   "questionId":6,
   "question":"Does UK have a monarchy?"
}
````

In case of an error, a different kind of payload is returned which describes any error conditions. 

The error response is defined as follows.

| Name | Type | Description |
| :---         |     :---      | :--- |
| errors  | array   | array of errors |
| code   | integer    | Http status code  |
| message | string | user friendly error message|

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

The sample response is given below.

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

### POST /nbcuniversal/quiz/{quizId}/questions ###

Sends a response to a quiz question to the backend. The request body payload is defined below.

| Name | Type | Description |
| :---         |     :---      | :--- |
| questionId  | integer   | unique id of question to be verified against |
| response   | boolean    | boolean response; the value can either be `true` or `false` |

The sample request is given below

````
{  
   "questionId":15,
   "response":false
}
````

The sample response format is given below

| Name | Type | Description |
| :---         |     :---      | :--- |
| question  | string   | question corresponding to question id sent in the request payload |
| correctAnswer   | boolean    | represents the correct value; the value can either be `true` or `false` |
| playerAnswer    | boolean    | represents the player's answer; the value can either be `true` or `false`|


The sample response is given below

````
{  
   "question":"Does November have 30 days in a month?",
   "correctAnswer":true,
   "playerAnswer":false
}
````

In case of an error, a different kind of payload is returned which describes any error conditions. 

The error response is defined as follows.

| Name | Type | Description |
| :---         |     :---      | :--- |
| errors  | array   | array of errors |
| code   | integer    | Http status code  |
| message | string | user friendly error message|


1) #### An invalid quiz id ####

In this case, 400 error is returned. The sample response is given below

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

The sample response is given below.

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

3) #### The quiz id does not exist in the system. ####

The sample response is given below

````
{  
   "errors":[  
      {  
         "code":400,
         "message":"No quiz was found for quiz id test"
      }
   ]
}
````

### GET /nbcuniversal/quiz/{quizId}/score ###

This endpoint returns the score of the contestant at any given time. The response is defined as follows:

 Name | Type | Description |
| :---         |     :---      | :--- |
| incorrectAnswers  | integer   |number of incorrect answers given|
| correctAnswers   | integer    | number of correct answers given|
| score    | integer    | player's score; it is equal to the value of correct answers.|

The sample response is given below.

````
{  
   "incorrectAnswers":1,
   "correctAnswers":2,
   "score":2
}
````

The error conditions are described below

#### An invalid quiz id ####

````
{  
   "errors":[  
      {  
         "code":400,
         "message":"No quiz was found for quiz id test"
      }
   ]
}
````
