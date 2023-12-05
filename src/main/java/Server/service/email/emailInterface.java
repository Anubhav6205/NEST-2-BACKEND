package Server.service.email;

import Server.model.EmailModel;

public interface emailInterface {
	public void sendEmailWithImage(String to, String subject, String body, String imageData) ;

}
