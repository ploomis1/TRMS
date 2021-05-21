# TRMS
The Tuition Reimbursement System, TRMS, allows users to submit reimbursements for courses and training. The submitted reimbursement must be approved by that employee's supervisor, department head, and benefits coordinator. The benefits coordinator then reviews the grade received before finalizing the reimbursement.
## Technologies Used

* Javalin
* Java 8
* AWS S3
* AWS EC2
* Aws Keyspaces
* Maven
* Git
* Cassandra NoSQL
* Mockito
* JUnit

## Features

* Ability to uload documents, prsesntations, grading formats, emails, and files
* All Employees can login and out
* All Employess can submit a reimbursement request
* Funds are calculated automatically based on cost and company rules
* Request must follow business rules or be denied automatically
* Approval process follows hierarchy
* Approval steps can be skipped if approval email is provided
* Approval steps not submitted in timely manner will be auto-approved
* Requests will be marked urgent when within 1 week of course start date
* Only appropriate employees can view, accept, or reject requests
* Only appropriate employees can view files uploaded for request
* Comments and notes can be added to a request

## Usage

Clone Repository, setup Amazon Keyspaces, setup Amazon truststore, setup enviornment vairables in run configurations, use postman for api requests
