# nbcuniversal-takehome

Following are the list of endpoints supported the restful application

1) POST /nbcuniversal/quiz

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



2) GET /nbcuniversal/quiz/{quizId}

Generates unique questions per quiz id

**Response**

A successful response will include

● a status code of 200 OK

● Response body properties



| Name | Type | Description|
| :---         |     :---:      |          ---: |
| questionId  | integer   | Question Id associated with the question  |
| question   | string    | Question to be answered as a part of the quiz    |


