# TRMS
The Tuition Reimbursement System, TRMS, allows users to submit reimbursements for courses and training. The submitted reimbursement must be approved by that employee's supervisor, department head, and benefits coordinator. The benefits coordinator then reviews the grade received before finalizing the reimbursement.
## Technologies Used

* Javalin
* Java
* AWS S3
* Maven
* Git
* Amazon Keyspaces
* Cassandra
Mockito
JUnit

## Features

List of features ready and TODOs for future development

Comments and notes can be added to a request
* Ability to uload documents and files to Amazon S3
* Ability to view presentations and files uploaded to S3
Employees of all levels can login and out
Employees can submit a request for reimbursement with funds auto-calculated from data provided
Submissions must follow certain business rules or be automatically rejected
Hierarchy for aprroval
Certain approval steps can be skipped by submitting course documents
Certain approval steps will be skipped after a long enough time without approval
Submission will be marked urgent within 1 week of course start date
Only the appropiate person at each stage can view the request and files submitted

To-do list:
* Give access for S3 to EC2 instance
* Create Fron-end

## Usage

Clone Repository, setup Amazon Keyspaces, setup Amazon truststore, setup enviornment vairables in run configurations, use postman for api requests
