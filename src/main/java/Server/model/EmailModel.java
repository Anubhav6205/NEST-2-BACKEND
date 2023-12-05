package Server.model;

//EmailModel.java

public class EmailModel {

 private String to;
 private String subject;
 private String body;
 private String imageData;

 // Constructors, getters, and setters

 public EmailModel() {
 }

 public EmailModel(String to, String subject, String body, String imageData) {
     this.to = to;
     this.subject = subject;
     this.body = body;
     this.imageData = imageData;
 }

 public String getTo() {
     return to;
 }

 public void setTo(String to) {
     this.to = to;
 }

 public String getSubject() {
     return subject;
 }

 public void setSubject(String subject) {
     this.subject = subject;
 }

 public String getBody() {
     return body;
 }

 public void setBody(String body) {
     this.body = body;
 }

 public String getImageData() {
     return imageData;
 }

 public void setImageData(String imageData) {
     this.imageData = imageData;
 }
}
