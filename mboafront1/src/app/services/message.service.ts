import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MessageDTO {
  idMessage?: number;
  contenu: string;
  image?: string;
  lu?: boolean;
  dateEnvoi?: Date;
  idConversation?: number;
  idExpediteur: number;
  idDestinataire: number;
}

export interface ConversationDTO {
  idConversation: number;
  dateCreation: Date;
  idUser1: number;
  idUser2: number;
  otherUserName: string;
  otherUserPhoto: string;
  lastMessage?: string;
}

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = 'http://localhost:8080/messages';

  constructor(private http: HttpClient) {}

  getConversations(userId: number): Observable<ConversationDTO[]> {
    return this.http.get<ConversationDTO[]>(`${this.apiUrl}/${userId}`);
  }

  getMessagesBetween(user1Id: number, user2Id: number): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>(`${this.apiUrl}/conversation/${user1Id}/${user2Id}`);
  }

  sendMessage(message: MessageDTO): Observable<MessageDTO> {
    return this.http.post<MessageDTO>(this.apiUrl, message);
  }

  markAsRead(conversationId: number, recipientId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/read/${conversationId}/${recipientId}`, {});
  }
}
