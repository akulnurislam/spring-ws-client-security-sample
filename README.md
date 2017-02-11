# Spring WS Client Security Sample

This repository is based on the Spring WS weather client sample. The aim is to shows how to setup a Spring Web Services client to connect to a secure web service.
 
The security requirement of the web service are:
* Mutual authentication between client and server. X.509 certificates are used to prove the identity of the server and to authenticate the client.
  * Client and server exchange certificates.
  * Client includes a XML digital signature of the SOAP message body in the request.
  * Client includes a binary security token containing client's certificate in the request.
* WS Addressing for route messages.
  * Additional SOAP header fields are required in the request messsage.
  
Other requirement:
* Specify SOAP envelope namespace URI
* SOAP version 1.2
* Schema validations for request and response