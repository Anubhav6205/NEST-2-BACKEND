package Server.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class ChatsModel {
    @Id
    private String id; 
    private String personAId;
    private String personBId;
    private List<ChatMessage> messageDetail;
	public String getPersonAId() {
		return personAId;
	}
	public void setPersonAId(String personAId) {
		this.personAId = personAId;
	}
	public String getPersonBId() {
		return personBId;
	}
	public void setPersonBId(String personBId) {
		this.personBId = personBId;
	}
	public List<ChatMessage> getMessageDetail() {
		return messageDetail;
	}
	public void setMessageDetail(List<ChatMessage> messageDetail) {
		this.messageDetail = messageDetail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

    // Other getters and setters
}
